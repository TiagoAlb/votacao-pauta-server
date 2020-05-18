package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.VotacaoStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoStatusDAO extends PagingAndSortingRepository<VotacaoStatus, Long> {
    @Query("SELECT new br.com.teste.dbserver.votacaoPauta.model.VotacaoStatus("
            + "votacao, "
            + "SUM(CASE WHEN (voto.voto = true) THEN 1 ELSE 0 END) AS qtdSim, "
            + "SUM(CASE WHEN (voto.voto = false) THEN 1 ELSE 0 END) AS qtdNao,"
            + "COUNT(voto.voto) AS qtdVotos) "
            + "FROM Votacao votacao JOIN votacao.votos voto "
            + "WHERE votacao.id = :idVotacao")
    public Optional<VotacaoStatus> findByVotacaoId(@Param("idVotacao") long idVotacao);
}
