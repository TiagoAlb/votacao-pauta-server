package br.com.votacaoPautaServer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Pauta implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @NotNull(message = "O campo titulo não pode ser nulo!")
   @Size(max = 100, message = "O campo titulo deve possuir no máximo {max} caracteres!")
   @NotEmpty(message = "O campo titulo não pode ser vazio!")
   @Column(nullable = false, length = 100)
   private String titulo;
   
   @NotNull(message = "O campo descricao não pode ser nulo!")
   @NotEmpty(message = "O campo descricao não pode ser vazio!")
   @Column(columnDefinition="TEXT")
   private String descricao;
   
   @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
   @Temporal(TemporalType.TIMESTAMP)
   private Date creation_date = new Date(System.currentTimeMillis());
   
   @ManyToOne
   @JoinColumn(nullable = false)
   private Associado autor;
   
   @Column(nullable = false)
   private boolean emVotacao = false;

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getTitulo() {
      return this.titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public String getDescricao() {
      return this.descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public Date getCreation_date() {
      return this.creation_date;
   }

   public void setCreation_date(Date creation_date) {
      this.creation_date = creation_date;
   }

    public Associado getAutor() {
        return autor;
    }

    public void setAutor(Associado autor) {
        this.autor = autor;
    }

    public boolean isEmVotacao() {
        return emVotacao;
    }

    public void setEmVotacao(boolean emVotacao) {
        this.emVotacao = emVotacao;
    }
}
