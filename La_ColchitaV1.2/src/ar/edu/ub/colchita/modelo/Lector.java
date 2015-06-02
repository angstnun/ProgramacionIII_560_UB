package ar.edu.ub.colchita.modelo;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Lector implements Cerrable {
	
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	public Lector(String path) {
		try{
			this.setFileReader(new FileReader(path));
			this.setBufferedReader(new BufferedReader(this.getFileReader()));
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Lector",JOptionPane.WARNING_MESSAGE);
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
	
	public void cerrar() {
		try{
			if(this.getBufferedReader()!=null) {
				this.getBufferedReader().close();
			}
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Lector",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public ArrayList<String> leer() {
		ArrayList<String> lineas=new ArrayList<String>();
		String linea="";
		try{
			while((linea=this.getBufferedReader().readLine())!=null) {
				lineas.add(linea);
			}
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Lector",JOptionPane.WARNING_MESSAGE);
		}
		catch(NullPointerException e) {}
		this.cerrar();
		return lineas;
	}
	
}