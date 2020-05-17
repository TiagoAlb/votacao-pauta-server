package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Votacao;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoDAO extends PagingAndSortingRepository<Votacao, Long> {
    @Query("SELECT open FROM Votacao votacao WHERE votacao.id = :idVotacao")
    public boolean findVotacaoStatus(@Param("idVotacao") long idVotacao);
    
    @Query("SELECT associado.id FROM Votacao votacao JOIN votacao.votos voto JOIN voto.associado associado "
            + "WHERE votacao.id = :idVotacao AND associado.id = :idAssociado")
    public Optional<Long> findAssociadoIdVotacao(@Param("idVotacao") long idVotacao, @Param("idAssociado") long idAssociado);
    
    @Query("SELECT votacao FROM Votacao votacao JOIN votacao.pauta pauta "
            + "WHERE pauta.id = :idPauta")
    public Optional<Votacao> findByPautaId(@Param("idPauta") long idPauta);
}
