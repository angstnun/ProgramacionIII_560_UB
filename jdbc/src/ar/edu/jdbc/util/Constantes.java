package ar.edu.jdbc.util;

import java.util.Properties;

public class Constantes {
	
	public static int DBTYPE;
	public static String DATA_SOURCE_NAME;
	public static String URL_DATA_BASE;
	public static String APPNAME;
	public static String PATH_IMAGE;
	
	private static String getVar(Properties p, String var, String defaultValue) {
		String val;
		
		if ((val = p.getProperty(var)) == null) {
			System.err.println("Variable <" + var + "> indefaultValueinida, usando defaultValueault ("
					+ defaultValue + ")");
			val = defaultValue;
		}
		
		return val;
	}
	
	private static int getIVar(Properties p, String var, String defaultValue) {
		return (int) Long.decode(getVar(p, var, defaultValue)).longValue();
	}
	
	private static long getLVar(Properties p, String var, String defaultValue) {
		return (long) Long.decode(getVar(p, var, defaultValue)).longValue();
	}
	
	public static void loadVars(Properties p) {
		DBTYPE = getIVar(p, "DBTYPE", "1");
		DATA_SOURCE_NAME = getVar(p, "DATA_SOURCE_NAME", "XXX");
		URL_DATA_BASE = getVar(p, "URL_DATA_BASE", ".");
		APPNAME = getVar(p, "APPNAME", "XXX");
		PATH_IMAGE = getVar(p, "PATH_IMAGE", ".");
	}
	
}