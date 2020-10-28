/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacaoPautaServer.auth;

import br.com.votacaoPautaServer.dao.AssociadoDAO;
import br.com.votacaoPautaServer.model.Associado;
import br.com.votacaoPautaServer.auth.AssociadoAuth;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tiago
 */
@Component
public class DetailsService implements UserDetailsService {
    @Autowired
    AssociadoDAO associadoDAO;
    
    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        Optional<Associado> associado = associadoDAO.findByEmail(email);
        System.out.println(associado.get());
        if (!associado.isPresent()) {
            throw new UsernameNotFoundException(email + " não existe!");
        }

        return new AssociadoAuth(associado.get());
    }
}
