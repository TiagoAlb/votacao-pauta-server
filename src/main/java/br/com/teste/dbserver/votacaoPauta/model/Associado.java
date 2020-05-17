package br.com.teste.dbserver.votacaoPauta.model;

import br.com.teste.dbserver.votacaoPauta.util.CpfCnpjUtils;
import br.com.teste.dbserver.votacaoPauta.util.Util;
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
public class Associado implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @NotNull(message = "O campo cnpjCpf não pode ser nulo!")
   @Size(max = 14, message = "O campo cnpjCpf deve possuir no máximo {max} digitos!")
   @NotEmpty(message = "O campo cnpjCpf não pode ser vazio!")
   @Column(nullable = false, length = 14)
   private String cnpjCpf;
   
   @NotNull(message = "O campo nome não pode ser nulo!")
   @Size(max = 50, message = "O campo nome deve possuir no máximo {max} digitos!")
   @NotEmpty(message = "O campo nome não pode ser vazio!")
   @Column(unique = true, nullable = false, length = 50)
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

   public void setCnpjCpf(String cnpjCpf) throws Exception {
      cnpjCpf = CpfCnpjUtils.limpaCnpjCpf(cnpjCpf);
      
      if(!CpfCnpjUtils.isValid(cnpjCpf))
          throw new Exception("Formato de CNPJ ou CPF inválido!");
      
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
