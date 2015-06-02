package ar.edu.ub.colchita.controlador;

import ar.edu.ub.colchita.modelo.*;
import java.util.ArrayList;
import java.util.Random;

public class Analizador {
	
	private ArrayList<String> lineas;
	public static final int MAXIMO=10;
	
	public Analizador(String path) {
		this.setLineas(new Lector(path).leer());
	}

	public ArrayList<String> getLineas() {
		return this.lineas;
	}

	public void setLineas(ArrayList<String> lineas) {
		this.lineas = lineas;
	}
	
	public Retazo ejecutar() {
		Random random=new Random();
		Retazo retazo=null;
		if(this.getLineas()!=null) {
			switch(this.cantidadCoser()) {
			case 0:
				retazo=Ejecutor.girar(Ejecutor.crear(random.nextInt(MAXIMO)+1,random.nextInt(MAXIMO)+1),this.cantidadGirarRetazos(1)[0]);
				break;
			default:
				int cantidad=this.cantidadRetazos();
				int indice=cantidad-1;
				retazo=Ejecutor.girar(Ejecutor.crear(random.nextInt(MAXIMO)+1,random.nextInt(MAXIMO)+1),this.cantidadGirarRetazos(cantidad)[indice]);
				indice--;
				for(int i=0;i<this.cantidadCoser();i++,indice--) {
					retazo=Ejecutor.coser(Ejecutor.girar(Ejecutor.crear(random.nextInt(MAXIMO)+1,random.nextInt(MAXIMO)+1),this.cantidadGirarRetazos(cantidad)[indice]),retazo);
					retazo=Ejecutor.girar(retazo,this.cantidadGirarCoser(this.cantidadCoser())[this.cantidadCoser()-i-1]);
				}
			}
		}
		return retazo;
	}
	
	private int cantidadRetazos() {
		int suma=0;
		for(String linea:this.getLineas()) {
			for(int i=0;i<linea.length();i++) {
				try{
					if(linea.toCharArray()[i]=='r'&&linea.toCharArray()[i+1]=='e') {
						suma++;
					}
				}
				catch(IndexOutOfBoundsException e) {
					return suma;
				}
			}
		}
		return suma;
	}
	
	private int[] cantidadGirarRetazos(int cantidadRetazos) {
		int[] cantidad=new int[cantidadRetazos];
		int suma=0;
		int indice=0;
		for(String linea:this.getLineas()) {
			for(int i=0;i<linea.length();i++) {
				try{
					if(linea.toCharArray()[i]=='g'&&linea.toCharArray()[i+1]=='i') {
						suma++;
					}
					if(linea.toCharArray()[i]=='c'&&linea.toCharArray()[i+1]=='o') {
						suma=0;
					}
					if(linea.toCharArray()[i]=='r'&&linea.toCharArray()[i+1]=='e') {
						cantidad[indice]=suma;
						suma=0;
						indice++;
					}
				}
				catch(IndexOutOfBoundsException e) {
					return cantidad;
				}
			}
		}
		return cantidad;
	}
	
	private int cantidadCoser() {
		int suma=0;
		for(String linea:this.getLineas()) {
			for(int i=0;i<linea.length();i++) {
				try{
					if(linea.toCharArray()[i]=='c'&&linea.toCharArray()[i+1]=='o') {
						suma++;
					}
				}
				catch(IndexOutOfBoundsException e) {
					return suma;
				}
			}
		}
		return suma;
	}
	
	private int[] cantidadGirarCoser(int cantidadCoser) {
		int[] cantidad=new int[cantidadCoser];
		int indice=0;
		int suma=0;
		for(String linea:this.getLineas()) {
			for(int i=0;i<linea.length();i++) {
				try{
					if(linea.toCharArray()[i]=='g'&&linea.toCharArray()[i+1]=='i') {
						suma++;
					}
					if(linea.toCharArray()[i]=='r'&&linea.toCharArray()[i+1]=='e') {
						suma=0;
					}
					if(linea.toCharArray()[i]=='c'&&linea.toCharArray()[i+1]=='o') {
						cantidad[indice]=suma;
						suma=0;
						indice++;
					}
				}
				catch(IndexOutOfBoundsException e) {
					return cantidad;
				}
			}
		}
		return cantidad;
	}
}
