/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta;

import br.com.teste.dbserver.votacaoPauta.controller.Pautas;
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
public class PautaControllerTest extends VotacaoPautaApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private Pautas pautaController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(pautaController).build();
    }

    @Test
    public void testGetPautasController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/votacaoPauta/pautas")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPostPautaController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/votacaoPauta/pautas")
                .content("{ 'id': 0, 'titulo': 'Pauta Teste 1', 'descricao': 'Descricao teste da pauta teste 1' }")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
