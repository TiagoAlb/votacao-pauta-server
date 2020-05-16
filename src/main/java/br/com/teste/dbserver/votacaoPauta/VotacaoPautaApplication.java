package br.com.teste.dbserver.votacaoPauta;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotacaoPautaApplication {
   @PostConstruct
   public void init() {
      TimeZone.setDefault(TimeZone.getTimeZone("BET"));
   }

   public static void main(String[] args) {
      SpringApplication.run(VotacaoPautaApplication.class, args);
   }
}
