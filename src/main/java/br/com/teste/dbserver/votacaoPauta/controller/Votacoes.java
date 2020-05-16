package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.PautaDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotacaoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Pauta;
import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import br.com.teste.dbserver.votacaoPauta.success.SuccessResponse;
import br.com.teste.dbserver.votacaoPauta.taskscheduler.TaskSchedulert;
import br.com.teste.dbserver.votacaoPauta.util.Util;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    TaskSchedulert taskSchedulert;

    @Autowired
    Util util;

    @RequestMapping(method = RequestMethod.POST, value = "/pautas/{id}/votacao")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> POST_VOTACAO(@PathVariable long id, @RequestParam(required = false, defaultValue = "1") long minutes) {
        Optional<Pauta> pauta = this.pautaDAO.findById(id);
        if (!pauta.isPresent())
            throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");

        Votacao votacao = new Votacao();
        votacao.setMinutes(minutes);
        votacao.setPauta(pauta.get());
        votacao = votacaoDAO.save(votacao);
        taskSchedulert.scheduleRunnable(votacao.getId(), minutes);
        String formattedDate = this.util.FORMAT_DATETIME_TO_STR(this.util.ADD_DATE_MINUTES(votacao.getStart_date(), minutes));
        return new ResponseEntity(
                new SuccessResponse(
                        HttpStatus.CREATED.value(),
                        "Votação " + votacao.getId() + " iniciada com sucesso! Termino em " + formattedDate), HttpStatus.CREATED);
    }
}
