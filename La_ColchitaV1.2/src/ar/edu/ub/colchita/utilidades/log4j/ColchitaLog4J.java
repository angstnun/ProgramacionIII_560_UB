package ar.edu.ub.colchita.utilidades.log4j;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ColchitaLog4J {
	
	public static final Logger INFO_LOGGER = LogManager.getLogger("ColchitaInfoLogger");
	public static final Logger ERROR_LOGGER = LogManager.getLogger("ColchitaErrorLogger");
	
}