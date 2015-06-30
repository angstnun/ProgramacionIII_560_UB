package ar.edu.ub.colchita.utilidades;

import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.modelo.javacc.ASTPrincipal;

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