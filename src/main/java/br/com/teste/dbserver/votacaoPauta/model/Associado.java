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

@Entity
public class Associado implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @Column(nullable = false, length = 14)
   private String cnpjCpf;
   
   @Column(nullable = false, length = 50)
   private String nome;
   
   @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
   @Temporal(TemporalType.TIMESTAMP)
   private Date create_date = new Date(System.currentTimeMillis());

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getCnpjCpf() {
      return this.cnpjCpf;
   }

   public void setCnpjCpf(String cnpjCpf) {
      this.cnpjCpf = cnpjCpf;
   }

   public String getNome() {
      return this.nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Date getCreate_date() {
      return this.create_date;
   }

   public void setCreate_date(Date create_date) {
      this.create_date = create_date;
   }
}
