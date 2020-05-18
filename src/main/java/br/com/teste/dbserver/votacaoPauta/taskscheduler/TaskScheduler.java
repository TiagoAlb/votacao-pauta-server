package br.com.teste.dbserver.votacaoPauta.taskscheduler;

import br.com.teste.dbserver.votacaoPauta.dao.VotacaoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    @Autowired
    VotacaoDAO votacaoDAO;

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
            if (!votacao.isPresent())
                throw new ResourceNotFoundException("Votação ID: " + id + " não encontrada!");

            votacao.get().setOpen(false);
            votacao.get().setEnd_date(new Date(System.currentTimeMillis()));
            votacaoDAO.save(votacao.get());
            System.out.println("VOTAÇÃO ENCERRADA EM " + votacao.get().getEnd_date());

        }
    }
}
