package ar.edu.ub.colchita.utilidades;

import java.io.InputStream;
import java.net.URL;

public class BuscadorRutas {
	
    public static String getRutaRecurso(String rutaRelativa)
    {
		URL url = Thread.currentThread().getContextClassLoader().getResource(rutaRelativa);
		String ruta = url.toString().substring(url.toString().indexOf("/") + 1);
		if (ruta.indexOf('!') != -1) ruta = ruta.substring(ruta.indexOf('!') + 2);
        return ruta;
    }
    
    public static InputStream getRecursoComoInputStream(String rutaRelativa) {
    	 return Thread.currentThread().getContextClassLoader().getResourceAsStream(rutaRelativa);
    }

}