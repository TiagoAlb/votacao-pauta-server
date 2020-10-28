package br.com.votacaoPautaServer.controller;

import br.com.votacaoPautaServer.auth.AssociadoAuth;
import br.com.votacaoPautaServer.dao.PautaDAO;
import br.com.votacaoPautaServer.error.ApiError;
import br.com.votacaoPautaServer.error.ResourceNotFoundException;
import br.com.votacaoPautaServer.model.Pauta;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Pautas {

    @Autowired
    PautaDAO pautaDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/pautas")
    public ResponseEntity<Object> postPauta(@AuthenticationPrincipal AssociadoAuth associadoAuth, @RequestBody Pauta pauta) {
        try {
            pauta.setId(0);
            pauta.setAutor(associadoAuth.getAssociado());
            pauta.setCreation_date(new Date(System.currentTimeMillis()));
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Erro ao incluir pauta!", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pautaDAO.save(pauta), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pautas")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Pauta> getPautas(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return pautaDAO.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pautas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pauta getPauta(@PathVariable long id) {
        Optional<Pauta> pauta = pautaDAO.findById(id);
        if (!pauta.isPresent())
            throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");
    
        return pauta.get();
    }
}
