package ar.edu.ub.colchita.utilidades;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Properties;

public class Constantes {

	public static int TIPO_DB;
	public static String RUTA_DB;
	public static String RUTA_TEMP_DB;
	public static String RUTA_ARCHIVOS_FUENTE;
	public static String RUTA_INFO_LOG;
	public static String RUTA_ARCHIVO_AYUDA;
	public static String RUTA_ICONO;
	public static Image COLCHITA_ICONO;
	
	/**
	 * <b><u>Metodo <code>getVariable</code>.</u></b>
	 * <p>Metodo para inicializar las pseudo-constantes del programa. 
	 * Se agarra la propiedades y se la devuelve como un entero</p>
	 * @param propiedades
	 * @param variable
	 * @param valorPredeterminado
	 * @return
	 */
	
	private static String getVariable(Properties propiedadeses, String variable, String valorPredeterminado) {
		String valor;
		
		if ((valor = propiedadeses.getProperty(variable)) == null) {
			System.err.println("La variable <" + variable + "> esta indefinida, usando valor predefinido ("
					+ valorPredeterminado + ")");
			valor = valorPredeterminado;
		}
		
		return valor;
	}
	
	/**
	 * <b><u>Metodo <code>getLastId</code>.</u></b>
	 * <p>Devuelve el valor de una propiedades como un entero.</p>
	 * @param propiedades
	 * @param variable
	 * @param valorPredeterminado
	 * @return
	 */
	
	private static int getValorEntero(Properties propiedades, String variable, String valorPredeterminado) {
		return (int) Long.decode(getVariable(propiedades, variable, valorPredeterminado)).longValue();
	}
	
	/**
	 * <b><u>Metodo <code>cargarVariables</code>.</u></b>
	 * <p>Inicializa las pseudo-constantes de la clase.</p>
	 * @param propiedades
	 */
	
	public static void cargarVariables(Properties propiedades) {
		RUTA_ARCHIVOS_FUENTE = getVariable(propiedades, "RUTA_ARCHIVOS_FUENTE", ".");
		RUTA_ICONO = getVariable(propiedades, "RUTA_ICONO", ".");
		RUTA_DB = getVariable(propiedades, "RUTA_DB", ".");
		TIPO_DB = getValorEntero(propiedades, "TIPO_DB", "3");
		COLCHITA_ICONO = Toolkit.getDefaultToolkit().getImage(BuscadorRutas.getRutaRecurso(Constantes.RUTA_ICONO));
		RUTA_INFO_LOG = "ar/edu/ub/colchita/logs/colchita-info.log";
		RUTA_ARCHIVO_AYUDA = "";
		RUTA_TEMP_DB = System.getenv("TEMP").replace('\\', '/') + "/" + RUTA_DB.substring(RUTA_DB.lastIndexOf("/"), RUTA_DB.length()); 
	}
	
}