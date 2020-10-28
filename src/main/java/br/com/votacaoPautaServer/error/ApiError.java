/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacaoPautaServer.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Tiago
 */
public class ApiError {
    
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date(System.currentTimeMillis());

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setError(String error) {
        this.errors = Arrays.asList(error);
    }
}
