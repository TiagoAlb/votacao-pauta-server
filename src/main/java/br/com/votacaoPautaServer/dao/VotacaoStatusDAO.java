package br.com.votacaoPautaServer.dao;

import br.com.votacaoPautaServer.model.VotacaoStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoStatusDAO extends PagingAndSortingRepository<VotacaoStatus, Long> {
    @Query("SELECT votacaoStatus "
            + "FROM VotacaoStatus votacaoStatus JOIN votacaoStatus.votacao votacao "
            + "WHERE votacao.id = :idVotacao")
    public Optional<VotacaoStatus> findByVotacaoId(@Param("idVotacao") long idVotacao);
    
    @Query("SELECT new br.com.votacaoPautaServer.model.VotacaoStatus("
            + "votacao, "
            + "SUM(CASE WHEN (voto.voto = true) THEN 1 ELSE 0 END) AS qtdSim, "
            + "SUM(CASE WHEN (voto.voto = false) THEN 1 ELSE 0 END) AS qtdNao,"
            + "COUNT(voto.voto) AS qtdVotos) "
            + "FROM Votacao votacao LEFT JOIN votacao.votos voto "
            + "WHERE votacao.id = :idVotacao")
    public Optional<VotacaoStatus> contabilizaVotos(@Param("idVotacao") long idVotacao);
}
