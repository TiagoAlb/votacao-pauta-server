package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Pauta;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaDAO extends PagingAndSortingRepository<Pauta, Long> {
}
