package se.chalmers.ibid.model.util;

import java.util.Calendar;

public final class CalendarUtil {
	
	private CalendarUtil() {}
	
	public static Calendar getCalendar(int dia, int mes, int anio, int hora, int minuto){
		
		Calendar date = Calendar.getInstance();

		date.set(Calendar.DAY_OF_MONTH, dia);
		date.set(Calendar.MONTH, mes-1);
		date.set(Calendar.YEAR, anio);
		date.set(Calendar.HOUR_OF_DAY, hora);
		date.set(Calendar.MINUTE, minuto);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date;

	}
}
