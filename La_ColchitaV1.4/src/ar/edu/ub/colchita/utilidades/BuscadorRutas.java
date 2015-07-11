package ar.edu.ub.colchita.utilidades;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import ar.edu.ub.colchita.controlador.InformadorControlador;

/**
 * <b><big><u>Clase<code>BuscadorRutas</code>.</u></big></b>
 * <p>
 * Esta clase contiene metodos utilizados para la conversion de rutas relativas a absolutas, encontrar la ubicacion actual del programa
 * y obtener un recurso como un InputStream.
 * <p>
 */

public class BuscadorRutas {
	
	/**
	* <b><u>M&eacutetodo <code>getRutaRecurso</code>.</u></b>
	* <p>M&eacutetodo para encontrar la ruta absoluta de un recurso segun su ruta relativa.
	* </p>
    * @param rutaRelativa es la ruta relativa del recurso a encontrar.
    * @return String la ruta absoluta del recurso.
	*/
	
    public static String getRutaRecurso(String rutaRelativa)
    {
		URL url = Thread.currentThread().getContextClassLoader().getResource(rutaRelativa);
		String ruta = url.toString().substring(url.toString().indexOf("/") + 1);
		if (ruta.indexOf('!') != -1) ruta = ruta.substring(ruta.indexOf('!') + 2);
        return ruta;
    }
    
    /**
    * <b><u>M&eacutetodo <code>getRecursoComoInputStream</code>.</u></b>
    * <p>M&eacutetodo para loguear las excepciones, haciendo uso del ERROR_LOGGER de nuestro ColchitaLog4J.
    * </p>
    * @param rutaRelativa es la ruta relativa del recurso a devolver.
    * @return InputStream el recurso a devolver.
    */
    
    public static InputStream getRecursoComoInputStream(String rutaRelativa) {
    	 return Thread.currentThread().getContextClassLoader().getResourceAsStream(rutaRelativa);
    }
    
    /**
    * <b><u>M&eacutetodo <code>getCurrentLocation</code>.</u></b>
    * <p>M&eacutetodo para averiguar donde se encuentra el programa dentro del sistema.
    * </p>
    * @return File devuelve la ubicacion actual del programa dentro de un file.
    */
    
    public static File getCurrentLocation() {
    	File file = null;
		try {
			file = new File(BuscadorRutas.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(), "Buscador rutas", 1);
		}
    	return file;
    }

}