package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Voto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoDAO extends PagingAndSortingRepository<Voto, Integer> {
}
