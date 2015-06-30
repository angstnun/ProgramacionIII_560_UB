package ar.edu.ub.colchita.vista;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColchitaNuevoRetazoInput extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextField ancho;
	private JTextField alto;

	public ColchitaNuevoRetazoInput() {
		this.setAncho(new JTextField(3));
		this.setAlto(new JTextField(3));
		this.add(new JLabel("Ancho: "));
		this.add(ancho);
		this.add(new JLabel("Alto: "));
		this.add(alto);		
	}
	
	private JTextField getAncho() {
		return ancho;
	}

	private void setAncho(JTextField ancho) {
		this.ancho = ancho;
	}
	
	private JTextField getAlto() {
		return alto;
	}

	private void setAlto(JTextField alto) {
		this.alto = alto;
	}

	public Integer getValorAncho() {
		return (int)Long.decode(this.getAncho().getText()).longValue();
	}
	
	public Integer getValorAlto() {
		return (int)Long.decode(this.getAlto().getText()).longValue();
	}

}