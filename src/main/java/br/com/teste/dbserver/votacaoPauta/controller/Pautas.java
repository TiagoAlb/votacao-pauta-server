package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.PautaDAO;
import br.com.teste.dbserver.votacaoPauta.error.ApiError;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Pauta;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/votacaoPauta")
public class Pautas {

    @Autowired
    PautaDAO pautaDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/pautas")
    public ResponseEntity<Object> POST_PAUTA(@RequestBody Pauta pauta) {
        
        try {
            pauta.setId(0);
            pauta.setCreate_date(new Date(System.currentTimeMillis()));
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Erro ao incluir pauta!", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pautaDAO.save(pauta), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pautas")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Pauta> GET_PAUTAS(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return pautaDAO.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pautas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pauta GET_PAUTA(@PathVariable long id) {
        Optional<Pauta> pauta = pautaDAO.findById(id);
        if (!pauta.isPresent())
            throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");
    
        return pauta.get();
    }
}
