package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Associado;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoDAO extends PagingAndSortingRepository<Associado, Integer> {
}
