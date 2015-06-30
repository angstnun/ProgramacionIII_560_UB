package ar.edu.ub.colchita.vista;

import javax.swing.*;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.utilidades.Constantes;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
* <h1>La Colchita - Vista - MenuAyuda</h1>
* Pestaña "Ayuda" de la barra de menu principal
*/

public class MenuAyudaVista extends JMenu{
	
	private static final long serialVersionUID = 1L;
	private JMenuItem itemAyuda;
	private JMenuItem itemAcerca;
	
	 /**
     * Declaración de los JMenuItem (Opciones de la pestaña Ayuda) y sus acciones correspondientes
     */
	
	public MenuAyudaVista() {
		this.setText("Ayuda");
		this.setItemAyuda(new JMenuItem("Ayuda"));
		this.setItemAcerca(new JMenuItem("Acerca de"));
		this.getItemAyuda().addActionListener(new ActionListenerItemMenu());
		this.getItemAcerca().addActionListener(new ActionListenerItemMenu());
		this.add(this.getItemAyuda());
		this.add(this.getItemAcerca());
	}
	
	public JMenuItem getItemAyuda() {
		return this.itemAyuda;
	}
	
	public void setItemAyuda(JMenuItem itemAyuda) {
		this.itemAyuda = itemAyuda;
	}
	
	public JMenuItem getItemAcerca() {
		return this.itemAcerca;
	}
	
	public void setItemAcerca(JMenuItem itemAcerca) {
		this.itemAcerca = itemAcerca;
	}
	
	private class ActionListenerItemMenu implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(getItemAcerca())) {
				JOptionPane optionPane = new JOptionPane(new JLabel("<html><body><div align='center'><p>La Colchita 2015</p><p>Creado por: <br>Admirall, Matias<br>Martin Ezequiel<br>Mosteiro, Nicolas <br>Nuñez, Angel <br>Romano, Martin</p></div></body></html>",JLabel.CENTER));
				JDialog dialog = optionPane.createDialog("Acerca de");
				dialog.setModal(Boolean.TRUE);
				dialog.setIconImage(Constantes.COLCHITA_ICONO);
				dialog.setVisible(Boolean.TRUE);
			}
			else if(e.getSource().equals(getItemAyuda())) {
				if (Desktop.isDesktopSupported()) {
				    try {
				        File file = new File(Constantes.RUTA_ARCHIVO_AYUDA);
				        Desktop.getDesktop().open(file);
				    } catch (IOException ex) {
				    	InformadorControlador.mostrarMensaje("Error al abrir el archivo de ayuda", "Ayuda", 1);
				    }
				}
			}
		}
	}
}
