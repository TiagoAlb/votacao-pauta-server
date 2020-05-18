/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta;

import br.com.teste.dbserver.votacaoPauta.controller.Associados;
import br.com.teste.dbserver.votacaoPauta.controller.Votacoes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *
 * @author Tiago
 */
public class VotacaoControllerTest extends VotacaoPautaApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private Votacoes votacaoController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(votacaoController).build();
    }

    @Test
    public void testGetVotacoesController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/votacaoPauta/votacoes")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPostVotacaoController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/votacaoPauta/pautas/1/votacao?minutes=1")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
