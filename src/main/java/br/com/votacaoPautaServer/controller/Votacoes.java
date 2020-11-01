package br.com.votacaoPautaServer.controller;

import br.com.votacaoPautaServer.auth.AssociadoAuth;
import br.com.votacaoPautaServer.auth.ForbiddenException;
import br.com.votacaoPautaServer.dao.PautaDAO;
import br.com.votacaoPautaServer.dao.VotacaoDAO;
import br.com.votacaoPautaServer.dao.VotacaoStatusDAO;
import br.com.votacaoPautaServer.error.ApiError;
import br.com.votacaoPautaServer.error.ResourceNotFoundException;
import br.com.votacaoPautaServer.model.Pauta;
import br.com.votacaoPautaServer.model.Votacao;
import br.com.votacaoPautaServer.model.VotacaoStatus;
import br.com.votacaoPautaServer.success.ApiSuccess;
import br.com.votacaoPautaServer.taskscheduler.TaskScheduler;
import static br.com.votacaoPautaServer.util.Util.ADD_DATE_MINUTES;
import static br.com.votacaoPautaServer.util.Util.FORMAT_DATETIME_TO_STR;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Votacoes {

    @Autowired
    VotacaoDAO votacaoDAO;
    
    @Autowired
    VotacaoStatusDAO votacaoStatusDAO;

    @Autowired
    PautaDAO pautaDAO;

    @Autowired
    TaskScheduler taskScheduler;

    @RequestMapping(method = RequestMethod.POST, value = "/pautas/{id}/votacao")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postVotacao(@AuthenticationPrincipal AssociadoAuth associadoAuth, @PathVariable long id, @RequestParam(required = false, defaultValue = "1") long minutes) {
        Votacao votacao = new Votacao();
        
        try {
            
            if(!associadoAuth.getAssociado().getPermissions().contains("admin"))
                throw new ForbiddenException("Apenas um administrador pode iniciar a sessão de votação!");
            
            
            Optional<Pauta> pauta = pautaDAO.findById(id);
            if (!pauta.isPresent())
                throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");

            Optional<Votacao> votacaoPauta = votacaoDAO.findByPautaId(id);

            if(votacaoPauta.isPresent()) {
                if(votacaoPauta.get().isOpen())
                    throw new Exception("A pauta " + id + " está em votação neste momento!");
                else
                    throw new Exception("A pauta " + id + " já foi votada! Sessão encerrada.");
            }
            pauta.get().setEmVotacao(true);
            votacao.setMinutes(minutes);
            votacao.setPauta(pautaDAO.save(pauta.get()));
            votacao = votacaoDAO.save(votacao);

            taskScheduler.scheduleRunnable(votacao.getId(), minutes);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.OK,
                            "Erro ao iniciar sessão de votação!", e.getMessage()), HttpStatus.OK);
        }
        
        String formattedDate = FORMAT_DATETIME_TO_STR(ADD_DATE_MINUTES(votacao.getStart_date(), minutes));
        return new ResponseEntity(
                new ApiSuccess(
                        HttpStatus.CREATED,
                        "Votação ID: " + votacao.getId() + " iniciada com sucesso! Término em " + formattedDate), HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Votacao> getVotacoes(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.by("id").descending());
        return votacaoDAO.findAll(pageRequest);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Votacao getVotacao(@PathVariable long id) {
        Optional<Votacao> votacao = votacaoDAO.findById(id);
        if (!votacao.isPresent())
            throw new ResourceNotFoundException("Votacão " + id + " não encontrada!");
    
        return votacao.get();
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes/{id}/associados/voto")
    @ResponseStatus(HttpStatus.OK)
    public boolean getValidaAssociadoVoto(@AuthenticationPrincipal AssociadoAuth associadoAuth, @PathVariable long id) {
        Optional<Votacao> votacao = votacaoDAO.findById(id);
        if (!votacao.isPresent())
            throw new ResourceNotFoundException("Votacão " + id + " não encontrada!");
        
        Optional<Long> idAssociadoVoto = votacaoDAO.findAssociadoIdVotacao(id, associadoAuth.getAssociado().getId());
        
        return idAssociadoVoto.isPresent();
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VotacaoStatus> getVotacaoStatus(@PathVariable long id) {
        Optional<VotacaoStatus> votacaoStatus = votacaoStatusDAO.findByVotacaoId(id);
        Votacao votacao = votacaoStatus.get().getVotacao();
        
        if (!votacaoStatus.isPresent())
            throw new ResourceNotFoundException("A votação " + votacao.getId() + " ainda não terminou! Dados não contabilizaos.");

        return new ResponseEntity<>(votacaoStatus.get(), HttpStatus.OK);
    }
}
