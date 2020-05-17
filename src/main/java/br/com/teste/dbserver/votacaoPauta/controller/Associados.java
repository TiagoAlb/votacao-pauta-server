package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.AssociadoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ApiError;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Associado;
import br.com.teste.dbserver.votacaoPauta.util.CpfCnpjUtils;
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
public class Associados {

    @Autowired
    AssociadoDAO associadoDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/associados")
    public ResponseEntity<Object> POST_ASSOCIADO(@RequestBody Associado associado) {
        String cnpjCpfLimpo = CpfCnpjUtils.limpaCnpjCpf(associado.getCnpjCpf());

        try {
            Optional<Associado> associadoAtual = associadoDAO.findByCnpjCpf(cnpjCpfLimpo);
            if(associadoAtual.isPresent())
                throw new Exception("Este CNPJ ou CPF já está cadastrado!");
            
            associado.setId(0);
            associado.setCnpjCpf(cnpjCpfLimpo);
            associado.setCreate_date(new Date(System.currentTimeMillis()));
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Não foi possível votar!", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(associadoDAO.save(associado), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/associados")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Associado> GET_ASSOCIADOS(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return associadoDAO.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/associados/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Associado GET_ASSOCIADO(@PathVariable long id) {
        Optional<Associado> associado = associadoDAO.findById(id);
        if (!associado.isPresent())
            throw new ResourceNotFoundException("Associado " + id + " não encontrado!");
             
        return associado.get();
    }
}
