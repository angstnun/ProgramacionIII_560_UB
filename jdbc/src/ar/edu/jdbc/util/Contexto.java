package ar.edu.jdbc.util;

import java.util.HashMap;

public class Contexto {
	
	private static final HashMap<String, Object> MAPA = new HashMap<String, Object>();
	
	public static Object get(String clave) {
		return MAPA.get(clave);
	}
	
	public static void set(String clave, Object valor) {
		MAPA.put(clave, valor);
	}
	
	public static String getString(String clave) {
		Object objeto = Contexto.get(clave);
		return (objeto != null) ? objeto.toString() : null;
	}
	
}