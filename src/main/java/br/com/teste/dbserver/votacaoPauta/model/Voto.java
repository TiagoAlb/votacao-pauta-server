package br.com.teste.dbserver.votacaoPauta.model;

import static br.com.teste.dbserver.votacaoPauta.util.Util.CONVERT_TO_BOOLEAN;
import static br.com.teste.dbserver.votacaoPauta.util.Util.VALIDATE_VOTE_BOOLEAN;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Associado associado;

    @Column(nullable = false)
    private boolean voto = false;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date = new Date(System.currentTimeMillis());

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
    
    public void setVoto(String voto) throws Exception {
        if(!VALIDATE_VOTE_BOOLEAN(voto))
            throw new Exception("Campo voto inválido!");
        
        this.voto = CONVERT_TO_BOOLEAN(voto);
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
