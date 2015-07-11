package ar.edu.ub.colchita.utilidades;

import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.modelo.javacc.ASTPrincipal;

/**
 * <b><big><u>Clase <code>Contexto</code>.</u></big></b>
 * <p>
 * Guarda datos contextuales, variables est&aacuteticas que son usadas en la ejecuci&oacuten del programa.
 * <p>
 */

public class Contexto {

	
	private static final HashMap<String, Object> MAPA = new HashMap<String, Object>();
	private static final ArrayList<RetazoModelo> LISTA_RETAZOS = new ArrayList<RetazoModelo>();
	private static ASTPrincipal EJECUTABLE = null;
	
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

	public static Object getRetazos(Integer clave) {
		return LISTA_RETAZOS.get(clave);
	}
	
	/**
	 * <b><u>M&eacutetodo <code>addRetazo</code>.</u></b>
	 * <p>Agrega un valor retazo al ArrayList <code>LISTA_RETAZOS</code>.</p>
	 * @param valor Representa el retazo que va a ingresarse al ArrayList.
	 */
	
	public static void addRetazo(RetazoModelo valor) {
		if (valor != null)
			LISTA_RETAZOS.add(valor);
	}
	
	public static ArrayList<RetazoModelo> getListaRetazos() {
		return LISTA_RETAZOS;
	}
	
	public static Integer getCantidadRetazos() {
		return LISTA_RETAZOS.size();
	}

	public static ASTPrincipal getEjecutable() {
		return EJECUTABLE;
	}

	public static void setEjecutable(ASTPrincipal ejecutable) {
		EJECUTABLE = ejecutable;
	}
	
}