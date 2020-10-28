package br.com.votacaoPautaServer.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.http.HttpStatus;

public class ApiSuccess {

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date(System.currentTimeMillis());
    private HttpStatus status;
    private String message;

    public ApiSuccess(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp() {
        return this.timestamp;
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
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
