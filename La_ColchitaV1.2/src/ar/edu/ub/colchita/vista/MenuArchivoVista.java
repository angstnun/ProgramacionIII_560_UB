package ar.edu.ub.colchita.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.controlador.SelectorControlador;
import ar.edu.ub.colchita.utilidades.Teclas;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelAreaTrabajo;

/**
* <h1>La Colchita - Vista - MenuArchivo</h1>
* Pestaña "Archivo" de la barra de menu principal
*/

public class MenuArchivoVista extends JMenu{
	
	private static final long serialVersionUID = 1L;
	private JMenuItem itemNuevo;
	private JMenuItem itemCompilar;
	private JMenuItem itemEjecutar;
	private JMenuItem itemGuardar;
	private JMenuItem itemAbrir;
	private JMenuItem itemSalir;
	
	  /**
     * Declaración de los JMenuItem (Opciones de la pestaña Archivo) y sus acciones correspondientes
     * @param jTextArea 
     * @param areaTexto
     * @param PanelAreaTrabajo panelDibujo
     */
	
	public MenuArchivoVista(JTextArea areaTexto,PanelAreaTrabajo panelDibujo){
		this.setText("Archivo");
		this.setItemNuevo(new JMenuItem("Nuevo"));
		this.setItemCompilar(new JMenuItem("Compilar"));
		this.setItemEjecutar(new JMenuItem("Ejecutar"));
		this.setItemGuardar(new JMenuItem("Guardar"));
		this.setItemAbrir(new JMenuItem("Abrir"));
		this.setItemSalir(new JMenuItem("Salir"));
		this.getItemNuevo().setAccelerator(Teclas.CTRLN.getKeyStroke());
		this.getItemGuardar().setAccelerator(Teclas.CTRLS.getKeyStroke());
		this.getItemCompilar().setAccelerator(Teclas.CTRLD.getKeyStroke());
		this.getItemEjecutar().setAccelerator(Teclas.CTRLE.getKeyStroke());
		this.getItemNuevo().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.getItemCompilar().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.getItemEjecutar().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.getItemAbrir().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.getItemGuardar().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.getItemSalir().addActionListener(new ActionListenerItemMenu(areaTexto,panelDibujo));
		this.add(this.getItemNuevo());
		this.add(this.getItemAbrir());
		this.add(this.getItemGuardar());
		this.add(this.getItemCompilar());
		this.add(this.getItemEjecutar());
		this.add(this.getItemSalir());
	}
	
	public JMenuItem getItemNuevo() {
		return itemNuevo;
	}

	private void setItemNuevo(JMenuItem itemNuevo) {
		this.itemNuevo = itemNuevo;
	}

	public JMenuItem getItemCompilar() {
		return this.itemCompilar;
	}
	
	private void setItemCompilar(JMenuItem itemCompilar) {
		this.itemCompilar = itemCompilar;
	}
	
	public JMenuItem getItemEjecutar() {
		return this.itemEjecutar;
	}
	
	private void setItemEjecutar(JMenuItem itemEjecutar) {
		this.itemEjecutar = itemEjecutar;
	}
	
	public JMenuItem getItemGuardar() {
		return this.itemGuardar;
	}
	
	private void setItemGuardar(JMenuItem itemGuardar) {
		this.itemGuardar = itemGuardar;
	}
	
	public JMenuItem getItemAbrir() {
		return itemAbrir;
	}

	private void setItemAbrir(JMenuItem itemAbrir) {
		this.itemAbrir = itemAbrir;
	}

	public JMenuItem getItemSalir() {
		return this.itemSalir;
	}
	
	private void setItemSalir(JMenuItem itemSalir) {
		this.itemSalir = itemSalir;
	}
	
	private class ActionListenerItemMenu implements ActionListener{
		
		private JTextArea areaTexto;
		private PanelAreaTrabajo panelDibujo;
		
		public ActionListenerItemMenu(JTextArea areaTexto,PanelAreaTrabajo panelDibujo){
			this.setAreaTexto(areaTexto);
			this.setPanelDibujo(panelDibujo);
		}
		
		public JTextArea getAreaTexto() {
			return areaTexto;
		}
		
		private void setAreaTexto(JTextArea areaTexto) {
			this.areaTexto = areaTexto;
		}
		
		public PanelAreaTrabajo getPanelDibujo() {
			return this.panelDibujo;
		}
		
		private void setPanelDibujo(PanelAreaTrabajo panelDibujo) {
			this.panelDibujo = panelDibujo;
		}
		
		public void actionPerformed(ActionEvent e) {
			String titulo = null;
			if(e.getActionCommand().equals("Salir")) 
				System.exit(0);
			else if(e.getActionCommand().equals("Nuevo"))
				((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor(this.areaTexto)).borrarTodo();
			else if(e.getActionCommand().equals("Guardar"))
				SelectorControlador.seleccionar(e.getActionCommand(), this.getAreaTexto(), null);
			else if(e.getActionCommand().equals("Abrir")){
				SelectorControlador.seleccionar(e.getActionCommand(), this.getAreaTexto(), null);				
			}
			else {
				switch(e.getActionCommand()) {
				case "Compilar":
					titulo = "Error al compilar";
					break;
				case "Ejecutar":
					titulo = "Error al ejecutar";
					break;
				}
				
				if(this.areaTexto.getText().length() > 0) {
					SelectorControlador.seleccionar(e.getActionCommand(),this.getAreaTexto(),this.getPanelDibujo());				
				}
				else {
					InformadorControlador.mostrarMensaje("El area de texto se encuentra vacia.", titulo, 2);
				}				
			}
		}
	}
}
