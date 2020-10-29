package br.com.votacaoPautaServer.model;

import br.com.votacaoPautaServer.util.CpfCnpjUtils;
import br.com.votacaoPautaServer.util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Transient;

@Entity
public class Associado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "O campo cnpjCpf não pode ser nulo!")
    @Size(max = 14, message = "O campo cnpjCpf deve possuir no máximo {max} caracteres!")
    @NotEmpty(message = "O campo cnpjCpf não pode ser vazio!")
    @Column(unique = true, nullable = false, length = 14)
    private String cnpjCpf;

    @NotNull(message = "O campo nome não pode ser nulo!")
    @Size(max = 100, message = "O campo nome deve possuir no máximo {max} caracteres!")
    @NotEmpty(message = "O campo nome não pode ser vazio!")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "O campo email não pode ser nulo!")
    @NotEmpty(message = "O campo email não pode ser vazio!")
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @JsonIgnore
    private String password;
    
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date = new Date(System.currentTimeMillis());
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissions;
    
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

        if (!CpfCnpjUtils.isValid(cnpjCpf)) {
            throw new Exception("Formato de CNPJ ou CPF inválido!");
        }

        this.cnpjCpf = cnpjCpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(!Util.VALIDATE_MAIL(email))
            throw new Exception("Email inválido!");
        
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Date getCreation_date() {
        return this.creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
