package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.PautaDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotacaoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ApiError;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Pauta;
import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import br.com.teste.dbserver.votacaoPauta.success.ApiSuccess;
import br.com.teste.dbserver.votacaoPauta.taskscheduler.TaskScheduler;
import br.com.teste.dbserver.votacaoPauta.util.Util;
import static br.com.teste.dbserver.votacaoPauta.util.Util.ADD_DATE_MINUTES;
import static br.com.teste.dbserver.votacaoPauta.util.Util.FORMAT_DATETIME_TO_STR;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/votacaoPauta")
public class Votacoes {

    @Autowired
    VotacaoDAO votacaoDAO;

    @Autowired
    PautaDAO pautaDAO;

    @Autowired
    TaskScheduler taskScheduler;

    @RequestMapping(method = RequestMethod.POST, value = "/pautas/{id}/votacao")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> POST_VOTACAO(@PathVariable long id, @RequestParam(required = false, defaultValue = "1") long minutes) {
        Votacao votacao = new Votacao();
        
        try {
            Optional<Pauta> pauta = this.pautaDAO.findById(id);
            if (!pauta.isPresent())
                throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");

            Optional<Votacao> votacaoPauta = votacaoDAO.findByPautaId(id);

            if(votacaoPauta.isPresent()) {
                if(votacaoPauta.get().isOpen())
                    throw new Exception("A pauta " + id + " está em votação neste momento!");
                else
                    throw new Exception("A pauta " + id + " já foi votada! Sessão encerrada.");
            }

            votacao.setMinutes(minutes);
            votacao.setPauta(pauta.get());
            votacao = votacaoDAO.save(votacao);

            taskScheduler.scheduleRunnable(votacao.getId(), minutes);
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Não foi possível votar!", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        String formattedDate = FORMAT_DATETIME_TO_STR(ADD_DATE_MINUTES(votacao.getStart_date(), minutes));
        return new ResponseEntity(
                new ApiSuccess(
                        HttpStatus.CREATED,
                        "Votação " + votacao.getId() + " iniciada com sucesso! Término em " + formattedDate), HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Votacao> GET_VOTACOES(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return votacaoDAO.findAll(pageRequest);
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/votacoes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Votacao GET_VOTACAO(@PathVariable long id) {
        Optional<Votacao> votacao = votacaoDAO.findById(id);
        if (!votacao.isPresent())
            throw new ResourceNotFoundException("Votacao " + id + " não encontrada!");
    
        return votacao.get();
    }
}
