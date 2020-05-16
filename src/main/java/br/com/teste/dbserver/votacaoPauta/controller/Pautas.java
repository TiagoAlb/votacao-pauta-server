package br.com.teste.dbserver.votacaoPauta.controller;

import br.com.teste.dbserver.votacaoPauta.dao.PautaDAO;
import br.com.teste.dbserver.votacaoPauta.model.Pauta;
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
public class Pautas {
   @Autowired
   PautaDAO pautaDAO;

   @RequestMapping(method = RequestMethod.POST, value = "/pautas")
   @ResponseStatus(HttpStatus.OK)
   public Pauta POST_PAUTA(@RequestBody Pauta pauta) {
      pauta.setCreate_date(new Date(System.currentTimeMillis()));
      return pautaDAO.save(pauta);
   }

   @RequestMapping(method = RequestMethod.GET, path = "/pautas")
   public Iterable<Pauta> GET_PAUTAS(@RequestParam(required = false,defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "10") int page_results) {
      PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
      return pautaDAO.findAll(pageRequest);
   }

   @RequestMapping(method = RequestMethod.GET, path = "/pautas/{id}")
   public Optional<Pauta> GET_PAUTA(@PathVariable long id) {
      return pautaDAO.findById(id);
   }
}
