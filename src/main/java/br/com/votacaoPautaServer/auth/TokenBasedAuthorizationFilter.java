/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacaoPautaServer.auth;

import static br.com.votacaoPautaServer.controller.Associados.SECRET;
import br.com.votacaoPautaServer.dao.AssociadoDAO;
import br.com.votacaoPautaServer.model.Associado;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 *
 * @author Tiago
 */
public class TokenBasedAuthorizationFilter 
        extends OncePerRequestFilter {
    
    AssociadoDAO associadoDAO;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token;
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer "))
            token = header.substring(7);
        else
            token = request.getParameter("token");

        if (token != null && !token.isEmpty()) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            long id = decode.getClaim("id").asLong();
            
            Associado associado = associadoDAO.findById(id).get();
            
            AssociadoAuth associadoAuth = new AssociadoAuth(associado);
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(associadoAuth
                            , null, associadoAuth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        chain.doFilter(request, response);
    }

    public TokenBasedAuthorizationFilter(AssociadoDAO associadoDAO) {
        super();
        this.associadoDAO = associadoDAO;
    }
}