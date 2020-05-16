package br.com.teste.dbserver.votacaoPauta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Votacao implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @OneToOne
   private Pauta pauta;
   
   @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
   @Temporal(TemporalType.TIMESTAMP)
   private Date start_date = new Date(System.currentTimeMillis());
   
   @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
   @Temporal(TemporalType.TIMESTAMP)
   private Date end_date;
   
   @Column(nullable = false)
   private boolean open = true;
   
   @Column(nullable = false)
   private long minutes = 1;
   
   @OneToMany(orphanRemoval = true)
   private List<Voto> votos;

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Pauta getPauta() {
      return this.pauta;
   }

   public void setPauta(Pauta pauta) {
      this.pauta = pauta;
   }

   public Date getStart_date() {
      return this.start_date;
   }

   public void setStart_date(Date start_date) {
      this.start_date = start_date;
   }

   public Date getEnd_date() {
      return this.end_date;
   }

   public void setEnd_date(Date end_date) {
      this.end_date = end_date;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public long getMinutes() {
      return this.minutes;
   }

   public void setMinutes(long minutes) {
      this.minutes = minutes;
   }

   public List<Voto> getVotos() {
      return this.votos;
   }

   public void setVotos(List<Voto> votos) {
      this.votos = votos;
   }
}
