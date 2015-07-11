package ar.edu.ub.colchita.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import ar.edu.ub.colchita.utilidades.*;
import ar.edu.ub.colchita.vista.ProgramaPrincipalView;

public class PruebaPrincipal {
	
	public static void main(String[] args) {
		Properties propiedades = new Properties();
		try {
			propiedades.load(new FileInputStream (
					new File("src/ar/edu/ub/colchita/configuracion/Config.properties")));
			Constantes.cargarVariables(propiedades);
		} catch (FileNotFoundException e) {
			System.out.println("El archivo : " + "Config.propiedades" + " no pudo ser encontrado. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al leer :" + "Config.propiedades" + " - " + e.getMessage());
		}
		new ProgramaPrincipalView();
	}
}