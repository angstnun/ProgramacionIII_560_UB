package ar.edu.ub.colchita.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ar.edu.ub.colchita.utilidades.BuscadorRutas;
import ar.edu.ub.colchita.utilidades.Constantes;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal;

/**
* <h1>La Colchita - Controlador - Prueba Principal</h1>
* Carga de configuraciones predeterminadas	
*/

public class ControladorPrincipal {
	
	/**
	  * Método de carga del archivo con configuraciones predeterminadas.
	  */
	
	public static void cargarPropiedades(InputStream inputStream) {
		Properties propiedades = new Properties();
		try {
			propiedades.load(inputStream);
			Constantes.cargarVariables(propiedades);
		} catch (FileNotFoundException e) {
			InformadorControlador.mostrarMensaje("El archivo : " + "Config.propiedades" + " no pudo ser encontrado.", "Colchita", 1);
		} catch (IOException e) {
			InformadorControlador.mostrarMensaje("Error al leer :" + "Config.propiedades", "Colchita", 1);
		}
	}
	
	/**
	  * Este metodo vuelve visible la vista del programa principal.
	  */
	
	public static void mostrarProgramaPrincipalView() {
		new VistaProgramaPrincipal().setVisible(Boolean.TRUE);
	}
	
	public static void main(String[] args) {
		ControladorPrincipal.cargarPropiedades(BuscadorRutas.getRecursoComoInputStream("ar/edu/ub/colchita/configuracion/Config.properties"));
		ControladorPrincipal.mostrarProgramaPrincipalView();
	}
}