package ar.edu.ub.colchita.utilidades;

import java.util.ArrayList;

import javax.swing.JPanel;

import ar.edu.ub.colchita.modelo.Cosible;
import ar.edu.ub.colchita.modelo.Girable;
import ar.edu.ub.colchita.modelo.ImposibleCoserException;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.vista.ContenedorRetazoVista;
import ar.edu.ub.colchita.vista.VistaRetazo;

/**
 * <b><big><u>Clase <code>NodoRetazo</code>.</u></big></b>
 * <p>
 * Extiende de <code>NodoColchita</code>. Esta clase caracteriza un retazo en base a los par&aacutemetros ingresados por el usuario.
 * <p>
 */

public class NodoRetazo extends NodoColchita implements Girable, Cosible{
	
	private ArrayList<JPanel> composicionRetazo;
	private Integer altoTotal;
	private Integer anchoTotal;
	
	public NodoRetazo(RetazoModelo r) {
		this.setComposicionRetazo(new ArrayList<JPanel>());
		this.getComposicionRetazo().add(new VistaRetazo(r.getId(), r.getDimension()));
		this.setAnchoTotal(r.getDimension().width);
		this.setAltoTotal(r.getDimension().height);
	}
	
	public ArrayList<JPanel> getComposicionRetazo() {
		return composicionRetazo;
	}

	private void setComposicionRetazo(ArrayList<JPanel> composicionRetazo) {
		this.composicionRetazo = composicionRetazo;
	}
	
	public Integer getAnchoTotal() {
		return anchoTotal;
	}

	private void setAnchoTotal(Integer anchoTotal) {
		this.anchoTotal = anchoTotal;
	}

	public Integer getAltoTotal() {
		return altoTotal;
	}

	private void setAltoTotal(Integer altoTotal) {
		this.altoTotal = altoTotal;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>equals</code>.</u></b>
	 * <p>
	 * Compara la composici&oacuten de los dos retazos para probar si son iguales.
	 */
	
	public boolean equals(Object objeto){
		if(objeto==null){
			return false;
		}
		if(!(objeto instanceof NodoRetazo)){
			return false;
		}
		if(!this.getComposicionRetazo().equals(((NodoRetazo)objeto).getComposicionRetazo())) {
			return false;
		}
		return true;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>toString</code>.</u></b>
	 * <p>
	 * @return Devuelve el nombre de la clase en un string.
	 */

	public String toString() {
    	return "NodoRetazo x: " + this.getAnchoTotal() + " " + "y: " + this.getAltoTotal(); 
    }
	
	/**
	 * <b><u>Sobrecarga del m&eacutetodo <code>coser</code>.</u></b>
	 * <p>
	 * Construye un nuevo retazo cuya dimension <code>ancho</code> 
	 * representa la suma de los valores <code>ancho</code> de cada retazo involucrado.
	 * <p>
	 * @param nodoRetazo Representa al retazo que se cose junto al retazo actual.
	 * @throws ImposibleCoserException Se lanza la excepcion en caso de que el <code>alto</code> de los retazos involucrados no coincida.
	 */

	@Override
	public void coser(NodoRetazo nodoRetazo) throws ImposibleCoserException {
		if(!nodoRetazo.getAltoTotal().equals(this.getAltoTotal())) {
			throw new ImposibleCoserException();
		}
		else{
			ContenedorRetazoVista nuevoContenedor = new ContenedorRetazoVista();
			for(JPanel retazos1 : this.getComposicionRetazo()) { nuevoContenedor.add(retazos1); }
			for(JPanel retazos2 : nodoRetazo.getComposicionRetazo()) { nuevoContenedor.add(retazos2); }
			this.getComposicionRetazo().clear();
			this.getComposicionRetazo().add(nuevoContenedor);
			this.setAnchoTotal(getAnchoTotal() + nodoRetazo.getAnchoTotal());
		}
	}
	
	
	/**
	 * <b><u>Sobrecarga del m&eacutetodo <code>girar</code>.</u></b>
	 * <p>
	 * Intercambia los valores de los atributos <code>alto</code> y <code>ancho</code> 
	 * de la <code>dimension</code> del retazo.
	 * <p>
	 * @param giros La cantidad de giros del retazo en cuestión.
	 */

	@Override
	public void girar(Integer giros) {
		for(int i = 0; i < giros%4; i++) {
			Integer aux = this.getAnchoTotal();
			this.setAnchoTotal(this.getAltoTotal());
			this.setAltoTotal(aux);
		}
		for(JPanel panelRetazo : getComposicionRetazo()) {
			if(panelRetazo instanceof ContenedorRetazoVista)((ContenedorRetazoVista)panelRetazo).girar(giros);
			else ((VistaRetazo)panelRetazo).girar(giros);			
		}
	}
	
}