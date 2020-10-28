/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacaoPautaServer.auth;

import br.com.votacaoPautaServer.model.Associado;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Tiago
 */
public class AssociadoAuth extends User {
    private final Associado associado;

    public AssociadoAuth(Associado associado) {
        super(associado.getEmail(),
                associado.getPassword(),
                AuthorityUtils.createAuthorityList(
                        associado.getPermissions().toArray(new String[]{})));
        this.associado = associado;
    }

    public Associado getAssociado() {
        return associado;
    }
}
