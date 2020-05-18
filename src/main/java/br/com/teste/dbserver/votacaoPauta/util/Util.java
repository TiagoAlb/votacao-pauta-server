package br.com.teste.dbserver.votacaoPauta.util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
   
   public static boolean VALIDATE_MAIL(String email) {
        if(email == null || email.isEmpty())
            return false;
        
        String padraoDeEmail
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(padraoDeEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
