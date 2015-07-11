package ar.edu.ub.colchita.vista;

import javax.swing.*;
import java.awt.event.*;

/**
* <h1>La Colchita - Vista - MenuHerramientas</h1>
* Pestaña "Herramientas" de la barra de menu principal
*/

public class VistaMenuHerramientas extends JMenu{
	
	private static final long serialVersionUID = 1L;
	private JMenuItem itemLogs;
	
	 /**
     * Declaración de los JMenuItem (Opciones de la pestaña Herramientas) y sus acciones correspondientes
     */
	
	public VistaMenuHerramientas(){
		this.setText("Herramientas");
		this.setItemLogs(new JMenuItem("Logs"));
		this.getItemLogs().addActionListener(new ActionListenerItemMenu());
		this.add(this.getItemLogs());
	}
	
	public JMenuItem getItemLogs() {
		return this.itemLogs;
	}
	
	public void setItemLogs(JMenuItem itemLogs) {
		this.itemLogs = itemLogs;
	}
	
	private class ActionListenerItemMenu implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("Logs"))
				new ColchitaInfoLogs();
		}
	}
}
