package ar.edu.ub.colchita.modelo;

import javax.swing.JOptionPane;

public abstract class Ejecutor {
	
	public static Retazo crear(int ancho,int alto) {
		return new Retazo(ancho,alto);
	}
	
	public static Retazo girar(Retazo retazo,int giros) {
		if(retazo==null) {
			return null;
		}
		for(int i=0;i<giros;i++) {
			retazo.girar();
		}
		return retazo;
	}
	
	public static Retazo coser(Retazo retazo1,Retazo retazo2) {
		if(retazo1==null||retazo2==null) {
			return null;
		}
		Retazo retazo3=null;
		try{
			retazo3=retazo1.coser(retazo2);
		}
		catch(ImposibleCoserException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Ejecutor",JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return retazo3;
	}
}
