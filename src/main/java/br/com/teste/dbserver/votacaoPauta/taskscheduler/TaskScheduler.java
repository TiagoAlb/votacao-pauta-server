package br.com.teste.dbserver.votacaoPauta.taskscheduler;

import br.com.teste.dbserver.votacaoPauta.dao.AssociadoDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotacaoDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotacaoStatusDAO;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.mail.SendMail;
import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import br.com.teste.dbserver.votacaoPauta.model.VotacaoStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    @Autowired
    VotacaoDAO votacaoDAO;
    
    @Autowired
    VotacaoStatusDAO votacaoStatusDAO;
    
    @Autowired 
    JmsTemplate jmsTemplate;

    public void scheduleRunnable(long id, long minutes) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new VotacaoEndTask(id), minutes, TimeUnit.MINUTES);
    }

    class VotacaoEndTask implements Runnable {

        private final long id;

        public VotacaoEndTask(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            Optional<Votacao> votacao = votacaoDAO.findById(id);
            
            try {
                if (!votacao.isPresent())
                    throw new ResourceNotFoundException("Votação ID: " + id + " não encontrada!");

                votacao.get().setOpen(false);
                votacao.get().setEnd_date(new Date(System.currentTimeMillis()));
                votacaoDAO.save(votacao.get());

                Optional<VotacaoStatus> votacaoStatus = votacaoStatusDAO.contabilizaVotos(id);

                StringBuilder mensagemStatus = new StringBuilder();
                if(votacaoStatus.isPresent() && votacaoStatus.get().getQtdVotos() > 0) {
                    double porcentagemSim = (votacaoStatus.get().getQtdSim() * 100) / votacaoStatus.get().getQtdVotos();
                    double porcentagemNao = (votacaoStatus.get().getQtdNao() * 100) / votacaoStatus.get().getQtdVotos();

                    if(votacaoStatus.get().getQtdSim() > votacaoStatus.get().getQtdNao())
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPauta().getId())
                                .append(" foi aprovada! ").append(porcentagemSim)
                                .append("% dos votos favoráveis.");
                    else if(votacaoStatus.get().getQtdSim() < votacaoStatus.get().getQtdNao())
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPauta().getId())
                                .append(" foi reprovada! ").append(porcentagemNao)
                                .append("% dos votos contrários.");
                    else
                        mensagemStatus.append("A pauta ID: ").append(votacao.get().getPauta().getId())
                                .append(" foi votada com empate nos votos, mas sem uma conclusão de aprovação! ")
                                .append(porcentagemSim).append("% dos votos favoráveis e ")
                                .append(porcentagemNao).append("% dos votos contrários.");
                } else {
                    mensagemStatus.append("A pauta ID: ").append(votacao.get().getPauta().getId())
                                .append(" não teve votos!");
                }

                votacaoStatus.get().setResultado(mensagemStatus.toString());        
                VotacaoStatus votacaoStatusSaved = votacaoStatusDAO.save(votacaoStatus.get());

                jmsTemplate.convertAndSend("queue.sample", votacaoStatusSaved);
            } catch(ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
