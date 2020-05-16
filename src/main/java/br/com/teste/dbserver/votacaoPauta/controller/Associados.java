package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.AssociadoDAO;
import br.com.teste.dbserver.votacaoPauta.error.ResourceNotFoundException;
import br.com.teste.dbserver.votacaoPauta.model.Associado;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
   @ResponseStatus(HttpStatus.OK)
   public Associado POST_ASSOCIADO(@RequestBody Associado associado) {
      associado.setCreate_date(new Date(System.currentTimeMillis()));
      return associadoDAO.save(associado);
   }

   @RequestMapping(method = RequestMethod.GET, path = "/associados")
   public Iterable<Associado> GET_ASSOCIADOS(@RequestParam(required = false,defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "10") int page_results) {
      PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
      return associadoDAO.findAll(pageRequest);
   }

   @RequestMapping(method = RequestMethod.GET, path = "/associados/{id}")
   public Associado GET_ASSOCIADO(@PathVariable int id) {
      Optional<Associado> associado = this.associadoDAO.findById(id);
      if (!associado.isPresent()) {
         throw new ResourceNotFoundException("Pauta " + id + " não encontrada!");
      } else {
         return associado.get();
      }
   }
}
