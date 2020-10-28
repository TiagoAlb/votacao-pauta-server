package br.com.votacaoPautaServer.dao;

import br.com.votacaoPautaServer.model.Voto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoDAO extends PagingAndSortingRepository<Voto, Long> {
}
