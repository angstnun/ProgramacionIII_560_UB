package ar.edu.ub.colchita.modelo;

import java.io.*;

import javax.swing.JOptionPane;

import ar.edu.ub.colchita.utilidades.Constantes;

public class Escritor implements Cerrable {
	
	private static FileWriter fileWriter;
	private static BufferedWriter writer;

	public static void abrir() {
	    try {
	    	File file = new File(Constantes.RUTA_ARCHIVOS_FUENTE);
			if(file!=null) file.delete();
	        fileWriter = new FileWriter(file, true);
	        writer = new BufferedWriter(fileWriter);
	    } 
	    catch (final IOException e) {
	    	JOptionPane.showMessageDialog(null,e.getMessage(),"Escritor",JOptionPane.WARNING_MESSAGE);
	    }
	}
	
	public static void cerrar() {
		try{
			if(writer != null)
				writer .close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Escritor",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void escribir(String input) {
		try {
			writer.write(input);
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Escritor",JOptionPane.WARNING_MESSAGE);
		}
	}

}
