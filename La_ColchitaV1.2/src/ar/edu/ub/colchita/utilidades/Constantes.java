package ar.edu.ub.colchita.utilidades;

import java.util.Properties;

public class Constantes {

	public static int TIPO_DB;
	public static String FUENTE_DB;
	public static String RUTA_ARCHIVOS_FUENTE;
	public static String RUTA_ICONO;
	
	private static String getVariable(Properties p, String var, String valorPredefinido) {
		String val;
		
		if ((val = p.getProperty(var)) == null) {
			System.err.println("La variable <" + var + "> esta indefinida, usando valor predefinido ("
					+ valorPredefinido + ")");
			val = valorPredefinido;
		}
		
		return val;
	}
	
	public static void cargarVariables(Properties p) {
		RUTA_ARCHIVOS_FUENTE = getVariable(p, "RUTA_ARCHIVOS_FUENTE", ".");
		RUTA_ICONO = getVariable(p, "RUTA_ICONO", ".");
	}
	
}