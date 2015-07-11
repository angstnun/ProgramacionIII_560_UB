package ar.edu.ub.colchita.utilidades;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Properties;

/**
 * <b><big><u>Clase <code>Constantes</code>.</u></big></b>
 * <p>
 * Esta clase contiene variables de tipo static que van a ser las constantes al iniciar el programa.
 * Posee las rutas donde est&aacuten guardados los archivos necesarios, el tipo de base de datos, entre otras cosas.
 * <p>
 */

public class Constantes {

	public static int TIPO_DB;
	public static String RUTA_DB;
	public static String RUTA_TEMP_DB;
	public static String RUTA_ARCHIVOS_FUENTE;
	public static String RUTA_INFO_LOG;
	public static String RUTA_ARCHIVO_AYUDA;
	public static String RUTA_ICONO;
	public static String RUTA_GIRAR_RETAZO_ICONO;
	public static String RUTA_SELECCIONAR_RETAZO_ICONO;
	public static String RUTA_COSER_RETAZO_ICONO;
	public static Image COLCHITA_ICONO;
	public static Image GIRAR_RETAZO_ICONO;
	public static Image COSER_RETAZO_ICONO;
	public static Image SELECCIONAR_RETAZO_ICONO;
	
	/**
	 * <b><u>M&eacutetodo <code>getVariable</code>.</u></b>
	 * <p>M&eacutetodo para inicializar las pseudo-constantes del programa. 
	 * Toma las propiedades y las devuelve como un entero.
	 * </p>
	 * @param propiedades 
	 * @param variable
	 * @param valorPredeterminado
	 * @return <code>valor</code> el entero resultante.
	 */
	
	private static String getVariable(Properties propiedades, String variable, String valorPredeterminado) {
		String valor;
		
		if ((valor = propiedades.getProperty(variable)) == null) {
			System.err.println("La variable <" + variable + "> esta indefinida, usando valor predefinido ("
					+ valorPredeterminado + ")");
			valor = valorPredeterminado;
		}
		
		return valor;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>getLastId</code>.</u></b>
	 * <p>Devuelve el valor de una propiedad como entero.</p>
	 * @param propiedades
	 * @param variable
	 * @param valorPredeterminado
	 * @return El valor resultante a partir de los par&aacutemetros de entrada ingresados.
	 */
	
	private static int getValorEntero(Properties propiedades, String variable, String valorPredeterminado) {
		return (int) Long.decode(getVariable(propiedades, variable, valorPredeterminado)).longValue();
	}
	
	/**
	 * <b><u>M&eacutetodo <code>cargarVariables</code>.</u></b>
	 * <p>Inicializa las pseudo-constantes de la clase y las carga en memoria.</p>
	 * @param propiedades
	 */
	
	public static void cargarVariables(Properties propiedades) {
		RUTA_ARCHIVOS_FUENTE = getVariable(propiedades, "RUTA_ARCHIVOS_FUENTE", ".");
		RUTA_ICONO = getVariable(propiedades, "RUTA_ICONO", ".");
		RUTA_GIRAR_RETAZO_ICONO = getVariable(propiedades, "RUTA_GIRAR_RETAZO_ICONO", ".");
		RUTA_COSER_RETAZO_ICONO = getVariable(propiedades, "RUTA_COSER_RETAZO_ICONO", ".");
		RUTA_SELECCIONAR_RETAZO_ICONO = getVariable(propiedades, "RUTA_SELECCIONAR_RETAZO_ICONO", ".");
		RUTA_DB = getVariable(propiedades, "RUTA_DB", ".");
		TIPO_DB = getValorEntero(propiedades, "TIPO_DB", "3");
		COLCHITA_ICONO = Toolkit.getDefaultToolkit().getImage(BuscadorRutas.getRutaRecurso(Constantes.RUTA_ICONO));
		GIRAR_RETAZO_ICONO = Toolkit.getDefaultToolkit().getImage(BuscadorRutas.getRutaRecurso(Constantes.RUTA_GIRAR_RETAZO_ICONO));
		COSER_RETAZO_ICONO = Toolkit.getDefaultToolkit().getImage(BuscadorRutas.getRutaRecurso(Constantes.RUTA_COSER_RETAZO_ICONO));
		SELECCIONAR_RETAZO_ICONO = Toolkit.getDefaultToolkit().getImage(BuscadorRutas.getRutaRecurso(Constantes.RUTA_SELECCIONAR_RETAZO_ICONO));
		RUTA_INFO_LOG = "ar/edu/ub/colchita/logs/colchita-info.log";
		RUTA_ARCHIVO_AYUDA = "";
		RUTA_TEMP_DB = System.getenv("TEMP").replace('\\', '/') + "/" + RUTA_DB.substring(RUTA_DB.lastIndexOf("/"), RUTA_DB.length()); 
	}
	
}