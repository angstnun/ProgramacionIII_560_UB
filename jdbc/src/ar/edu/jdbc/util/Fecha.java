package ar.edu.jdbc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fecha {
	
	public static String fmtFecha(Calendar fecha) {
		SimpleDateFormat fmt;
		
		if (fecha == null)
			return ("");
		
		fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		return (fmt.format(fecha.getTime()));
	}
	
	private static int ldec(String f, int i, int l) {
		return ((int) Long.parseLong(f.substring(i, i + l), 10));
	}
	
	public static Calendar loadFecha(String f) throws Exception {
		Calendar c;
		int year;
		int mon;
		int day;
		int hour;
		int min;
		int sec;
		int i;
		
		if (f == null)
			return (null);
		
		if (f.length() < "YYYY-MM-DD HH:MI:SS".length())
			throw new Exception("El formato de la fecha debe ser " + "YYYY-MM-DD HH:MI:SS" + ", y es " + f);
		
		i = 0;
		year = ldec(f, i, 4);
		i += 4;
		i++;
		mon = ldec(f, i, 2);
		i += 2;
		i++;
		day = ldec(f, i, 2);
		i += 2;
		i++;
		hour = ldec(f, i, 2);
		i += 2;
		i++;
		min = ldec(f, i, 2);
		i += 2;
		i++;
		sec = ldec(f, i, 2);
		
		if (mon > 0)
			mon--;
		if (year + mon + day + hour + min + sec == 0)
			return (null);
		
		c = Calendar.getInstance();
		c.set(year, mon, day, hour, min, sec);
		return (c);
	}
	
	public static String dbGetFecha(String campo) {
		return (campo);
	}
	
	public static String dbFecha(Calendar fecha) {
		String d;
		if (fecha == null)
			return ("null");
		d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha.getTime());
		return ("'" + d + "'");
	}
	
	public static String diffFecha(Calendar fecha) {
		Calendar now = Calendar.getInstance();
		String s;
		int anios;
		int dias;
		int horas;
		int min;
		
		if (fecha == null)
			return ("0");
		
		anios = now.get(Calendar.YEAR) - fecha.get(Calendar.YEAR);
		if (anios > 0) {
			dias = now.get(Calendar.DAY_OF_YEAR) + (365 - fecha.get(Calendar.DAY_OF_YEAR));
		} else {
			dias = now.get(Calendar.DAY_OF_YEAR) - fecha.get(Calendar.DAY_OF_YEAR);
		}
		
		if (dias > 0) {
			horas = now.get(Calendar.HOUR_OF_DAY) + (24 - fecha.get(Calendar.HOUR_OF_DAY)) - 1;
			min = now.get(Calendar.MINUTE) + (60 - fecha.get(Calendar.MINUTE));
		} else {
			horas = now.get(Calendar.HOUR_OF_DAY) - fecha.get(Calendar.HOUR_OF_DAY);
			min = now.get(Calendar.MINUTE) - fecha.get(Calendar.MINUTE);
		}
		
		if (min >= 60) {
			min -= 60;
			horas++;
		}
		if (horas >= 24) {
			horas -= 24;
			dias++;
		}
		
		s = "";
		if (anios > 0)
			s = anios + " A&#64os ";
		if (dias > 0)
			s += dias + " Dias ";
		s += horas + ":";
		if (min < 10)
			s += "0";
		s += min + " Horas";
		
		return (s);
	}
	
	public static Calendar loadFecha(Date d) throws Exception {
		Calendar c;
		
		if (d == null)
			return (null);
		
		c = Calendar.getInstance();
		c.setTime(d);
		return (c);
	}
	
}
