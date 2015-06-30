package ar.edu.ub.colchita.vista;

import javax.swing.*;

import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelAreaTrabajo;

/**
* <h1>La Colchita - Vista - BarraMenu</h1>
* Barra de menu principal contenedora
*/

public class BarraMenuVista extends JMenuBar{
	
	private static final long serialVersionUID = 1L;
	private MenuArchivoVista menuArchivo;
	private VistaMenuHerramientas menuHerramientas;
	private MenuAyudaVista menuAyuda;
	

    /**
    * Constructor principal de la barra del menu, en el cual se insertar las diferentes pestañas
    * del menu.
    * @param jTextArea
    * @param areaTexto
    * @param PanelAreaTrabajo
    * @param panelDibujo
    */
	
	public BarraMenuVista(JTextArea areaTexto,PanelAreaTrabajo panelDibujo){
		this.setMenuArchivo(new MenuArchivoVista(areaTexto,panelDibujo));
		this.setMenuHerramientas(new VistaMenuHerramientas());
		this.setMenuAyuda(new MenuAyudaVista());
		this.add(this.getMenuArchivo());
		this.add(this.getMenuHerramientas());
		this.add(this.getMenuAyuda());
	}
	
	public MenuArchivoVista getMenuArchivo() {
		return this.menuArchivo;
	}
	
	public void setMenuArchivo(MenuArchivoVista menuArchivo) {
		this.menuArchivo = menuArchivo;
	}
	
	public VistaMenuHerramientas getMenuHerramientas() {
		return this.menuHerramientas;
	}
	
	public void setMenuHerramientas(VistaMenuHerramientas menuHerramientas) {
		this.menuHerramientas = menuHerramientas;
	}
	
	public MenuAyudaVista getMenuAyuda() {
		return this.menuAyuda;
	}
	
	public void setMenuAyuda(MenuAyudaVista menuAyuda) {
		this.menuAyuda = menuAyuda;
	}
}