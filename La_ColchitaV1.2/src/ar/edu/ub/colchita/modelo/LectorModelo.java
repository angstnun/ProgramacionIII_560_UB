package ar.edu.ub.colchita.modelo;

import java.io.*;
import java.util.ArrayList;

import ar.edu.ub.colchita.controlador.InformadorControlador;

/**
 * <b><big><u>Clase <code>Lector</code>.</u></big></b>
 * Implementa: Interfaz <code>Cerrable</code>
 * <p>
 * Esta clase realiza la lectura linea por linea de un archivo de texto ubicado en un determinado <code>path</code>. 
 * Es una clase que se instancia.
 * <p>
 */

public class LectorModelo implements Cerrable{
	
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	/**
	 * <u>Constructor que recibe el path de un archivo como parametro.</u>
	 * <p>
	 * En el caso de no haber un archivo en el <code>path</code> generado, se creara uno nuevo con ese destino.
	 * <p>
	 * @param path Fichero donde se encuentra o encontrara el archivo de texto a leer.
	 */
	
	public LectorModelo(String path){
		try{
			this.setFileReader(new FileReader(path));
			this.setBufferedReader(new BufferedReader(this.getFileReader()));
		}
		catch(IOException e){
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
	}
	
	private FileReader getFileReader() {
		return this.fileReader;
	}
	
	private void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}
	
	private BufferedReader getBufferedReader() {
		return this.bufferedReader;
	}
	
	private void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	/**
	 * <b><u>Metodo <code>cerrar</code>.</u></b>
	 * <p>
	 * Este metodo se encarga de cerrar el archivo de texto del <code>BufferedWriter</code>.
	 */
	
	public void cerrar(){
		try{
			if(this.getBufferedReader()!=null){
				this.getBufferedReader().close();
			}
		}
		catch(IOException e){
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
	}
	
	/**
	 * <b><u>Metodo <code>leer</code>.</u></b>
	 * <p>
	 * Lee una linea en un archivo de texto y la almacena dentro de un <code>ArrayList</code>. Maneja dos excepciones.
	 * <p>
	 * @return lineas El <code>ArrayList</code> compuesto por las lineas almacenadas.
	 */
	
	public ArrayList<String> leer(){
		ArrayList<String> lineas=new ArrayList<String>();
		String linea="";
		try{
			while((linea=this.getBufferedReader().readLine())!=null){
				lineas.add(linea);
			}
		}
		catch(IOException e){
			InformadorControlador.mostrarMensaje(e.getMessage(),"Escritor",1);
		}
		catch(NullPointerException e){
			return new ArrayList<String>();
		}
		this.cerrar();
		return lineas;
	}
}
