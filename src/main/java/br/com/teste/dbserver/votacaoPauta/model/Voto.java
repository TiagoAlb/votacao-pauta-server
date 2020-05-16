package br.com.teste.dbserver.votacaoPauta.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Voto implements Serializable {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private long id;
   @ManyToOne
   private Associado associado;
   @Column(
      nullable = false
   )
   private boolean voto = false;

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Associado getAssociado() {
      return this.associado;
   }

   public void setAssociado(Associado associado) {
      this.associado = associado;
   }

   public boolean isVoto() {
      return this.voto;
   }

   public void setVoto(boolean voto) {
      this.voto = voto;
   }
}
