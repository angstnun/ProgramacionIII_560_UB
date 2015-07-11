package ar.edu.ub.colchita.vista;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
* <h1>La Colchita - Vista - ColchitaNuevoRetazoInput</h1>
* Ventana que nos permite indicar la dimension de un nuevo retazo a ser creado.
*/

public class ColchitaNuevoRetazoInput extends JPanel{

	private static final long serialVersionUID = 1L;
	private JSpinner ancho;
	private JSpinner alto;

	public ColchitaNuevoRetazoInput() {
		this.setAncho(new JSpinner(new SpinnerNumberModel(10, 0, 200, 1)));
		this.setAlto(new JSpinner(new SpinnerNumberModel(10, 0, 200, 1)));
		this.add(new JLabel("Ancho: "));
		this.add(ancho);
		this.add(new JLabel("Alto: "));
		this.add(alto);
	}
	
	private void setAncho(JSpinner ancho) {
		this.ancho = ancho;
	}

	private void setAlto(JSpinner alto) {
		this.alto = alto;
	}
	
	private JSpinner getAncho() {
		return this.ancho;
	}
	
	private JSpinner getAlto() {
		return this.alto;
	}

	public Integer getValorAncho() {
		return (int)Long.decode(this.getAncho().getValue().toString()).longValue();
	}
	
	public Integer getValorAlto() {
		return (int)Long.decode(this.getAlto().getValue().toString()).longValue();
	}

}