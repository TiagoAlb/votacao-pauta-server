package br.com.teste.dbserver.votacaoPauta.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SuccessResponse {
   private int status;
   @JsonFormat(
      pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ"
   )
   @Temporal(TemporalType.TIMESTAMP)
   private Date timestamp = new Date(System.currentTimeMillis());
   private String message;

   public SuccessResponse(int status, String message) {
      this.status = status;
      this.message = message;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public Date getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
