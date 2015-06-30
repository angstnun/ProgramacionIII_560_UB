package ar.edu.ub.colchita.vista;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ar.edu.ub.colchita.modelo.LectorModelo;
import ar.edu.ub.colchita.utilidades.Constantes;

public class ColchitaInfoLogs extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextArea logPanel;

	public ColchitaInfoLogs() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(550,400);
		this.setLocation(350,150);
		this.setResizable(Boolean.FALSE);
		this.setIconImage(Constantes.COLCHITA_ICONO);
		this.setLayout(new BorderLayout());
		this.setLogPanel(new JTextArea());
		this.getLogPanel().setEditable(Boolean.FALSE);
		this.leerColchitaInfoLog();
		this.add(new JScrollPane(this.getLogPanel()));
		this.setVisible(Boolean.TRUE);
	}

	public JTextArea getLogPanel() {
		return logPanel;
	}

	private void setLogPanel(JTextArea logPanel) {
		this.logPanel = logPanel;
	}
	
	private void leerColchitaInfoLog() {
		StringBuilder stringBuilder = new StringBuilder();
		ArrayList<String> stringLeido = new LectorModelo(Constantes.RUTA_INFO_LOG).leer();
		for(String string : stringLeido) stringBuilder.append(string + "\r\n ");
		this.getLogPanel().setText(stringBuilder.toString());
	}

}