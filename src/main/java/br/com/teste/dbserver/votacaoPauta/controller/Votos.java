/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.AssociadoDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotacaoDAO;
import br.com.teste.dbserver.votacaoPauta.dao.VotoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ApiError;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Associado;
import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import br.com.teste.dbserver.votacaoPauta.model.Voto;
import br.com.teste.dbserver.votacaoPauta.util.Util;
import static br.com.teste.dbserver.votacaoPauta.util.Util.VALIDATE_VOTE_BOOLEAN;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author Tiago
 */
@RestController
@RequestMapping(path = "/api/votacaoPauta")
public class Votos {
    
    @Autowired
    AssociadoDAO associadoDAO;
    
    @Autowired
    VotacaoDAO votacaoDAO;
    
    @Autowired
    VotoDAO votoDAO;
    
    @RequestMapping(method = RequestMethod.POST, value = "/votacoes/{idVotacao}/associados/{idAssociado}/votos")
    public ResponseEntity<Object> POST_VOTO(@PathVariable long idVotacao, @PathVariable long idAssociado, 
            @RequestParam(required = true) String voto) {
        
        Voto objetoVoto = new Voto();

        try {
            Optional<Votacao> votacao = votacaoDAO.findById(idVotacao);
            if (!votacao.isPresent())
                throw new ResourceNotFoundException("Votacao " + idVotacao + " não encontrada!");

            Optional<Associado> associado = associadoDAO.findById(idAssociado);
            if (!associado.isPresent())
                throw new ResourceNotFoundException("Associado " + idAssociado + " não encontrado!");

            if(!votacaoDAO.findVotacaoStatus(idVotacao))
                throw new Exception("Sessão de votação encerrada! Não é mais possível votar.");

            Optional<Long> idAssociadoVoto = votacaoDAO.findAssociadoIdVotacao(idVotacao, idAssociado);
            if(idAssociadoVoto.isPresent())
               throw new Exception("O associado " + associado.get().getNome() + " já realizou seu voto!");
               
            objetoVoto.setId(0);
            objetoVoto.setAssociado(associado.get());
            objetoVoto.setVoto(voto);
            objetoVoto = votoDAO.save(objetoVoto);

            votacao.get().getVotos().add(objetoVoto);
            votacaoDAO.save(votacao.get());
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.FORBIDDEN,
                            "Não foi possível votar!", e.getMessage()), HttpStatus.FORBIDDEN);
        }
        
        return new ResponseEntity<>(objetoVoto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/votos")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Voto> GET_VOTOS(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return votoDAO.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/votos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Voto GET_VOTO(@PathVariable long id) {
        Optional<Voto> voto = votoDAO.findById(id);
        if (!voto.isPresent())
            throw new ResourceNotFoundException("Voto " + id + " não encontrado!");

        return voto.get();
    }
}
