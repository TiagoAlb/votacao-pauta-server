package br.com.teste.dbserver.votacaoPauta.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class Util {
   public Date ADD_DATE_MINUTES(Date date, int minutes) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(12, minutes);
      return calendar.getTime();
   }

   public String FORMAT_DATETIME_TO_STR(Date date) {
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      return format.format(date);
   }
}
