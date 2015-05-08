package ar.edu.jdbc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ar.edu.jdbc.util.Constantes;
import ar.edu.jdbc.view.PropiedadView;

public class MainController {
	
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(
				new File("src/ar/edu/jdbc/resources/config/Config.properties")));
			Constantes.loadVars(properties);
		} catch (IOException e) {
			throw new IOException("No se pudo leer el archivo de Configuraciones: ".concat(
				"Config.properties").concat(" : ").concat(e.getMessage()));
		}
		
		new PropiedadView();
	}
	
}