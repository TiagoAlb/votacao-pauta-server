package br.com.teste.dbserver.votacaoPauta.util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class Util {
   public static Date ADD_DATE_MINUTES(Date date, long minutes) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(12, (int)minutes);
      return calendar.getTime();
   }

   public static String FORMAT_DATETIME_TO_STR(Date date) {
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      return format.format(date);
   }
   
   public static boolean CONVERT_TO_BOOLEAN(String value) {
       value = value.trim().toLowerCase();
       value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
       
       if(value.equals("sim"))
           return true;
       
       return false;
   }
   
   public static boolean VALIDATE_VOTE_BOOLEAN(String value) {
       value = value.trim().toLowerCase();
       value = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
       
       return value.equals("sim") || value.equals("nao");
   }
}
