/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta;

import br.com.teste.dbserver.votacaoPauta.controller.Associados;
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
public class AssociadoControllerTest extends VotacaoPautaApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private Associados associadoController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(associadoController).build();
    }

    @Test
    public void testGetAssociadosController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/votacaoPauta/associados")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPostAssociadoController() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/votacaoPauta/associados")
                .content("{ 'id': 0, 'nome': 'Tiago Albuquerque', 'cnpjCpf': '03599666083', 'email': 'tiago.mauricio.albuquerque@gmail.com' }")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
