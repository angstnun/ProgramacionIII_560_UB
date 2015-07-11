package ar.edu.ub.colchita.controlador;

import java.io.File;

import ar.edu.ub.colchita.modelo.ImposibleCoserException;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.utilidades.NodoRetazo;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelAreaTrabajo;

/**
 * <b><big><u>Clase <code>Ejecutor</code>.</u></big></b>
 * <p>
 * Esta clase se encarga de ejecutar y manejar las funciones caracteristicas del lenguaje de programacion "La Colchita".
 */

public abstract class EjecutorControlador {
	
	/**
	 * <b><u>Metodo <code>crearRetazo</code>.</u></b>
	 * <p>
	 * Este metodo crea un nuevo objeto del tipo <code>Retazo</code> con las dimensiones ingresadas como parametro.
	 * @param ancho Ancho del retazo que se desea crear.
	 * @param alto Altura del retazo que se desea crear.
	 * @return  Un nuevo retazo cuyas dimensiones son del <code>alto</code> 
	 * y el <code>ancho</code> ingresados como parametro.
	 */
	
	public static RetazoModelo crearRetazo(int ancho,int alto) {
		return new RetazoModelo(ancho,alto);
	}
	
	/**
	 * <b><u>Metodo <code>girarRetazo</code>.</u></b>
	 * <p>
	 * Este metodo gira un objeto del tipo <code>NodoRetazo</code>.
	 * @param retazo El retazo que se desea girar.
	 * @param giros La cantidad de giros para el <code>retazo</code> ingresado.
	 */
	
	public static void girarRetazo(NodoRetazo retazo, int giros) {
		retazo.girar(giros);
	}
	
	/**
	 * <b><u>Metodo <code>mostrarResultadoEjecucion</code>.</u></b>
	 * <p>
	 * Este metodo muestra en pantalla el <code>NodoRetazo</code> final.
	 * @param nodoRetazo El retazo que se desea mostrar.
	 * @param areaTrabajo el PanelAreaTrabajo donde se muestra el resultado final.
	 */
	
	public static void mostrarResultadoEjecucion(NodoRetazo nodoRetazo, PanelAreaTrabajo areaTrabajo) {
		if(nodoRetazo != null)
			areaTrabajo.dibujarRetazo(nodoRetazo.getComposicionRetazo().get(0));
	}
	
	/**
	 * <b><u>Metodo <code>coserRetazo</code>.</u></b>
	 * <p>
	 * Lleva a cabo la costura de dos retazos involucrados.
	 * <p>
	 * @param retazo1 Uno de los dos retazos que se van a coser.
	 * @param retazo2 Uno de los dos retazos que se van a coser.
	 * @return Un Booleano cuya funcion es informar si se pudo coser los dos retazos.
	 */
	
	public static Boolean coserRetazo(NodoRetazo retazo1, NodoRetazo retazo2) {
		if(retazo1 != null && retazo2 != null) {
			try{
				retazo1.coser(retazo2);
				return Boolean.TRUE;
			}
			catch(ImposibleCoserException e) {
				InformadorControlador.mostrarMensaje(e.getMessage(),"Ejecutor",1);
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * <b><u>Metodo <code>eliminarArchivo</code>.</u></b>
	 * <p>
	 * Este metodo elimina un archivo ubicado en el <code>path</code> ingresado como parametro.
	 * @param path Fichero en el cual se encuentra el archivo a eliminar.
	 */
	
	public static void eliminarArchivo(String path) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
}