package ar.edu.ub.colchita.modelo;

import java.io.*;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.utilidades.BuscadorRutas;
import ar.edu.ub.colchita.utilidades.Constantes;

/**
 * <b><big><u>Clase <code>Escritor</code>.</u></big></b>
 * <p>
 * Esta clase se encarga de escribir un determinado string dentro de un archivo de texto. 
 */

public class EscritorModelo {
	
	private static FileWriter fileWriter;
	private static BufferedWriter bufferedWriter;
	
	/**
	 * <b><u>Metodo <code>abrir</code>.</u></b>
	 * <p>
	 * Este metodo se encarga de abrir el archivo fuente del <code>BufferedWriter</code>. Setea el <code>FileWriterr</code> para
	 * que abra el archivo en modo escritura.
	 */

	public static void abrir() {
	    try {
	    	File file = new File(BuscadorRutas.getRutaRecurso(Constantes.RUTA_ARCHIVOS_FUENTE));
			if(file!=null) file.delete();
	        fileWriter = new FileWriter(file, true);
	        bufferedWriter = new BufferedWriter(fileWriter);
	    } 
	    catch (final IOException e) {
	    	InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
	    }
	}
	
	/**
	 * <b><u>Metodo <code>abrir</code>.</u></b>
	 * <p>
	 * Este metodo se encarga de abrir el archivo fuente del <code>BufferedWriter</code>. Setea el <code>FileWriterr</code> para
	 * que abra el archivo entregado por nuestra <code>ruta</code> en modo escritura
	 * @param ruta La ruta donde vamos a guardar nuestro archivo.
	 */
	
	public static void abrir(String ruta) {
		try {
			File file = new File(ruta);
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);				
		} 
		catch (final IOException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
	}
	
	/**
	 * <b><u>Metodo <code>cerrar</code>.</u></b>
	 * <p>
	 * Este metodo se encarga de cerrar el archivo fuente del <code>BufferedWriter</code>.
	 */
	
	public static void cerrar() {
		try{
			if(bufferedWriter != null)
				bufferedWriter .close();
		}
		catch (IOException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
	}
	
	/**
	 * <b><u>Metodo <code>escribir</code>.</u></b>
	 * <p>
	 * Este metodo se encarga de escribir el parametro <code>input</code> de tipo <code>String</code> dentro del archivo 
	 * fuente del <code>BufferedWriter</code>.
	 * @param input Representa el texto que desea escribirse dentro del archivo.
	 */
	
	public static void escribir(String input) {
		try {
			bufferedWriter.write(input);
		}
		catch (IOException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
	}

}