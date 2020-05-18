package br.com.teste.dbserver.votacaoPauta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
   @Size(max = 500, message = "O descricao titulo deve possuir no máximo {max} caracteres!")
   @NotEmpty(message = "O campo descricao não pode ser vazio!")
   @Column(length = 500)
   private String descricao;
   
   @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
   @Temporal(TemporalType.TIMESTAMP)
   private Date create_date = new Date(System.currentTimeMillis());

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

   public Date getCreate_date() {
      return this.create_date;
   }

   public void setCreate_date(Date create_date) {
      this.create_date = create_date;
   }
}
