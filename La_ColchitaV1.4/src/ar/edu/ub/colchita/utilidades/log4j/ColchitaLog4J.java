package ar.edu.ub.colchita.utilidades.log4j;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * <b><big><u>Clase <code>ColchitaLog4J</code>.</u></big></b>
 * <p>
 * Esta clase contiene los loggers del archivo de configuracion log4j, utilizados para escribir en sus respectivos logs
 * los eventos occurridos durante la ejecucion del programa.
 * <p>
 */

public class ColchitaLog4J {
	
	public static final Logger INFO_LOGGER = LogManager.getLogger("ColchitaInfoLogger");
	public static final Logger ERROR_LOGGER = LogManager.getLogger("ColchitaErrorLogger");
	
}