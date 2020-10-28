package br.com.votacaoPautaServer.dao;

import br.com.votacaoPautaServer.model.Pauta;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaDAO extends PagingAndSortingRepository<Pauta, Long> {
}
