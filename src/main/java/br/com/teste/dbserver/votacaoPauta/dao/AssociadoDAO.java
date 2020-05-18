package br.com.teste.dbserver.votacaoPauta.dao;

import br.com.teste.dbserver.votacaoPauta.model.Associado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoDAO extends PagingAndSortingRepository<Associado, Long> {
    public Optional<Associado> findByCnpjCpf(String cnpjCpf);
    
    public Optional<Associado> findByEmail(String email);
    
    @Query("SELECT associado.email FROM Associado associado ")
    public String[] findMailList();
}
