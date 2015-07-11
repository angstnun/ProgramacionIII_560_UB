package ar.edu.ub.colchita.vista;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
* <h1>La Colchita - Vista - ContenedorRetazoVista</h1>
* Permite almacenar varios objetos RetazoVista u otros Contenedores.
*/

import ar.edu.ub.colchita.modelo.Girable;

public class ContenedorRetazoVista extends JPanel implements Girable {

	private static final long serialVersionUID = 1L;

	private Integer ejeActual;

	public ContenedorRetazoVista() {
		this.setEjeActual(BoxLayout.X_AXIS);
		this.setLayout(new BoxLayout(this, this.getEjeActual()));
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	private Integer getEjeActual() {
		return this.ejeActual;
	}

	private void setEjeActual(Integer ejeActual) {
		this.ejeActual = ejeActual;
	}
	
	/**
	 * <b><u>Metodo <code>girar</code>.</u></b>
	 * <p>
	 * Este metodo hace que el contenedor gire y por lo tanto gire tambien su contenido.
	 */

	public void girar(Integer giros) {
		if(this.getComponents().length > 0) {
			for(int i = 0; i < giros%4; i++){
				if(getEjeActual() == BoxLayout.X_AXIS){
					this.setEjeActual(BoxLayout.Y_AXIS);
				}
				else{
					this.setEjeActual(BoxLayout.X_AXIS);
				}
				this.setLayout(new BoxLayout(this, getEjeActual()));
			}	
			for(Component panelRetazo : this.getComponents()) {
				if(panelRetazo instanceof ContenedorRetazoVista)
					((ContenedorRetazoVista)panelRetazo).girar(giros);
				else 
					((VistaRetazo)panelRetazo).girar(giros);	
			}			
		}
	}

}