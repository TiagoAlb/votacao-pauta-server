package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoDAO extends PagingAndSortingRepository<Votacao, Long> {
}
