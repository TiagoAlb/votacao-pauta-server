/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Tiago
 */
@Entity
public class VotacaoStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne
    private Votacao votacao;
    
    @Column(nullable = false)
    private long qtdSim;
    
    @Column(nullable = false)
    private long qtdNao;
    
    @Column(nullable = false)
    private long qtdVotos;
    
    @Column(length = 500)
    private String resultado;
    
    public VotacaoStatus(Votacao votacao, long qtdSim, long qtdNao, long qtdVotos) {
        this.votacao = votacao;
        this.qtdSim = qtdSim;
        this.qtdNao = qtdNao;
        this.qtdVotos = qtdVotos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Votacao getVotacao() {
        return votacao;
    }

    public void setVotacao(Votacao votacao) {
        this.votacao = votacao;
    }

    public long getQtdSim() {
        return qtdSim;
    }

    public void setQtdSim(long qtdSim) {
        this.qtdSim = qtdSim;
    }

    public long getQtdNao() {
        return qtdNao;
    }

    public void setQtdNao(long qtdNao) {
        this.qtdNao = qtdNao;
    }

    public long getQtdVotos() {
        return qtdVotos;
    }

    public void setQtdVotos(long qtdVotos) {
        this.qtdVotos = qtdVotos;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
