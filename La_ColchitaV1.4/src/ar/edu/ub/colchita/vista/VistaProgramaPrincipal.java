package ar.edu.ub.colchita.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import ar.edu.ub.colchita.controlador.ABMControlador;
import ar.edu.ub.colchita.controlador.ListadorControlador;
import ar.edu.ub.colchita.controlador.SelectorControlador;
import ar.edu.ub.colchita.modelo.FuncionModelo;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.utilidades.Accion;
import ar.edu.ub.colchita.utilidades.Constantes;
import ar.edu.ub.colchita.utilidades.Contexto;
import ar.edu.ub.colchita.utilidades.ContextoClaves;

/**
* <h1>La Colchita - Vista - ProgramaPrincipalView</h1>
* Ventana principal del programa
*/

public class VistaProgramaPrincipal extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel panelMenu;
	private JPanel panelSur;
	private PanelAreaTrabajo panelAreaTrabajo;
	private JPanel panelRetazos;
	private JPanel panelPreseleccion;
	
	/**
     * Declaracion de parametros generales de la ventana (Titulo, Icono, Tamaño, Localización).
     * Incorporación de paneles (Menu, Sur, AreaTrabajo, PanelRetazos, Preselección).
     */
	
	public VistaProgramaPrincipal(){
		this.setTitle("La Colchita");
		this.setIconImage(Constantes.COLCHITA_ICONO);
		this.setLayout(new BorderLayout());
		this.setSize(850,600);
		this.setLocation(350,150);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPanelSur(new PanelSur());
		this.setPanelAreaTrabajo(new PanelAreaTrabajo());
		this.setPanelMenu(new PanelMenu(getPanelSur().getPanelConsola().getAreaTexto(),getPanelAreaTrabajo()));
		this.setPanelRetazos(new PanelRetazos());
		this.setPanelPreseleccion(new PanelPreseleccion());
		this.add(this.getPanelMenu(),BorderLayout.NORTH);
		this.add(this.getPanelSur(),BorderLayout.SOUTH);
		this.add(this.getPanelAreaTrabajo(),BorderLayout.CENTER);
		this.add(this.getPanelRetazos(),BorderLayout.WEST);
		this.add(this.getPanelPreseleccion(),BorderLayout.EAST);
		this.pack();
	}

	public PanelSur getPanelSur() {
		return (PanelSur)this.panelSur;
	}

	private void setPanelSur(PanelSur panelSur) {
		this.panelSur = panelSur;
	}
	
	public PanelAreaTrabajo getPanelAreaTrabajo() {
		return this.panelAreaTrabajo;
	}
	
	private void setPanelAreaTrabajo(PanelAreaTrabajo panelAreaTrabajo) {
		this.panelAreaTrabajo = panelAreaTrabajo;
	}
	
	public JPanel getPanelMenu() {
		return this.panelMenu;
	}
	
	private void setPanelMenu(JPanel panelMenu) {
		this.panelMenu = panelMenu;
	}
	
	public JPanel getPanelRetazos() {
		return this.panelRetazos;
	}
	
	private void setPanelRetazos(JPanel panelRetazos) {
		this.panelRetazos = panelRetazos;
	}
	
	public JPanel getPanelPreseleccion() {
		return this.panelPreseleccion;
	}
	
	private void setPanelPreseleccion(JPanel panelPreseleccion) {
		this.panelPreseleccion = panelPreseleccion;
	}
	
	/**
	 * <b><u>Metodo <code>borrarTodo</code>.</u></b>
	 * <p>
	 * Limpia el area de trabajo, la lista de retazos, el area de preseleccion y el area de escritura.
	 */
	public void borrarTodo() {
		getPanelAreaTrabajo().limpiarAreaTrabajo();
		getPanelSur().getPanelConsola().getAreaTexto().setText("");
		((PanelRetazos)getPanelRetazos()).getModeloListaRetazos().removeAllElements();
		Contexto.getListaRetazos().clear();
		((VistaPanelRetazo)((PanelPreseleccion)getPanelPreseleccion()).getPrimerPanelSeleccion()).limpiarLienzo();
		((VistaPanelRetazo)((PanelPreseleccion)getPanelPreseleccion()).getSegundoPanelSeleccion()).limpiarLienzo();
	}
	
	public class PanelMenu extends JPanel{
		
		private static final long serialVersionUID = 1L;

		public PanelMenu(JTextArea textArea,PanelAreaTrabajo panelDibujo) {
			this.setLayout(new BorderLayout());
			this.add(new BarraMenuVista(textArea,panelDibujo), BorderLayout.WEST);
		}
		
	}
	
	public class PanelRetazos extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private DefaultListModel modeloListaRetazos;
		private JList listaRetazos;
		private JButton botonNuevoRetazo;
		private ArrayList<VistaRetazo> vistaRetazos;

		public PanelRetazos() {
			this.setLayout(new BorderLayout());
			this.setVistaRetazos(new ArrayList<VistaRetazo>());
			this.setModeloListaRetazos(new DefaultListModel());
			this.setListaRetazos(new JList(this.getModeloListaRetazos()));
			this.setBotonNuevoRetazo(new JButton());
			this.getBotonNuevoRetazo().setIcon(UIManager.getIcon("FileView.fileIcon"));
			this.getBotonNuevoRetazo().addActionListener(new EscuchadorNuevoRetazo());
			this.getListaRetazos().setVisibleRowCount(20);
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Retazos"));
			this.add(new JScrollPane(this.getListaRetazos()), BorderLayout.CENTER);
			this.add(getBotonNuevoRetazo(), BorderLayout.SOUTH);
		}
		
		public ArrayList<VistaRetazo> getVistaRetazos() {
			return vistaRetazos;
		}

		private void setVistaRetazos(ArrayList<VistaRetazo> vistaRetazos) {
			this.vistaRetazos = vistaRetazos;
		}

		private void setBotonNuevoRetazo(JButton boton) {
			this.botonNuevoRetazo = boton;
		}
		
		protected JButton getBotonNuevoRetazo() {
			return botonNuevoRetazo;
		}

		public DefaultListModel getModeloListaRetazos() {
			return modeloListaRetazos;
		}

		private void setModeloListaRetazos(DefaultListModel modeloListaRetazos) {
			this.modeloListaRetazos = modeloListaRetazos;
		}

		public JList getListaRetazos() {
			return listaRetazos;
		}

		private void setListaRetazos(JList listaRetazos) {
			this.listaRetazos = listaRetazos;
		}
		
		/**
		 * <b><u>Metodo <code>cargarLista</code>.</u></b>
		 * <p>
		 * Prepara y carga la lista de retazos.
		 */
		
		public void cargarLista() {
			this.getModeloListaRetazos().clear();
			this.getVistaRetazos().clear();
			for (RetazoModelo retazo : Contexto.getListaRetazos()) {
				this.getVistaRetazos().add(new VistaRetazo(retazo.getId(), retazo.getDimension()));
				this.getModeloListaRetazos().addElement(retazo);
			}
		}
		
	}
	
	public class PanelPreseleccion extends JPanel{

		private static final long serialVersionUID = 1L;
		private VistaPanelRetazo primerPanelSeleccion;
		private VistaPanelRetazo segundoPanelSeleccion;

		public PanelPreseleccion() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Preseleccion"));
			this.setPrimerPanelSeleccion(new VistaPanelRetazo(1));
			this.setSegundoPanelSeleccion(new VistaPanelRetazo(2));
			this.add(Box.createVerticalStrut(5));
			this.add(getPrimerPanelSeleccion());
			this.add(Box.createVerticalStrut(5));
			this.add(getSegundoPanelSeleccion());
			this.add(Box.createVerticalStrut(5));
		}

		public VistaPanelRetazo getPrimerPanelSeleccion() {
			return primerPanelSeleccion;
		}

		private void setPrimerPanelSeleccion(VistaPanelRetazo primerPanelSeleccion) {
			this.primerPanelSeleccion = primerPanelSeleccion;
		}

		public VistaPanelRetazo getSegundoPanelSeleccion() {
			return segundoPanelSeleccion;
		}

		private void setSegundoPanelSeleccion(VistaPanelRetazo segundoPanelSeleccion) {
			this.segundoPanelSeleccion = segundoPanelSeleccion;
		}	
		
	}
	
	
	public class PanelAreaTrabajo extends JPanel{
		
		private static final long serialVersionUID = 1L;

		public PanelAreaTrabajo() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Area de trabajo"));
		}
		
		/**
		 * <b><u>Metodo <code>dibujarRetazo</code>.</u></b>
		 * <p>
		 * Limpia el area de trabajo y luego muestra el JPanel pasado como parametro.
		 * @param retazo Es un JPanel que representa la vista de un retazo.
		 */
		
		public void dibujarRetazo(JPanel retazo) {
			this.limpiarAreaTrabajo();
			this.add(Box.createVerticalGlue());
			this.add(retazo);
			this.add(Box.createVerticalGlue());
			this.repaint();
			this.revalidate();
		}
		
		public void limpiarAreaTrabajo() {
			this.removeAll();
			this.repaint();
		}
		
	}
	
	public class PanelSur extends JPanel{

		private static final long serialVersionUID = 1L;
		private PanelListaFunciones panelListaFunciones;
		private PanelConsola panelConsola;

		public PanelSur() {
			this.setPanelListaFunciones(new PanelListaFunciones());
			this.setPanelConsola(new PanelConsola());
			this.setLayout(new BorderLayout());
			this.add(getPanelListaFunciones(), BorderLayout.WEST);
			this.add(getPanelConsola(), BorderLayout.CENTER);
		}
		
		private void setPanelConsola(PanelConsola panelConsola) {
			this.panelConsola = panelConsola;
		}

		private void setPanelListaFunciones(PanelListaFunciones panelListaFunciones) {
			this.panelListaFunciones = panelListaFunciones;
		}

		public PanelConsola getPanelConsola() {
			return panelConsola;
		}
		
		public PanelListaFunciones getPanelListaFunciones() {
			return panelListaFunciones;
		}
	}
	
	public class PanelListaFunciones extends JPanel{
		
		private DefaultListModel<FuncionModelo> modeloListaPropiedades;
		private JList<FuncionModelo> listaFunciones;
		private JPanel botonera;
		private JButton botonUtilizarFuncion;
		private JButton botonGuardarFuncion;
		private JButton botonBorrarFuncion;
		
		private static final long serialVersionUID = 1L;

		public PanelListaFunciones() {
			this.setLayout(new BorderLayout());
			this.setBotonera(new JPanel());
			this.setModeloListaPropiedades(new DefaultListModel<FuncionModelo>());
			this.setListaFunciones(new JList<FuncionModelo>(this.getModeloListaPropiedades()));
			this.setBotonGuardarFuncion(new JButton());
			this.getBotonGuardarFuncion().setText("Guardar");
			this.getBotonGuardarFuncion().addActionListener(new EscuchadorFunciones());
			this.setBotonUtilizarFuncion(new JButton());
			this.getBotonUtilizarFuncion().setText("Utilizar");
			this.getBotonUtilizarFuncion().addActionListener(new EscuchadorFunciones());
			this.setBotonBorrarFuncion(new JButton());
			this.getBotonBorrarFuncion().setText("Borrar");
			this.getBotonBorrarFuncion().addActionListener(new EscuchadorFunciones());
			this.getListaFunciones().setVisibleRowCount(8);
			this.cargarLista();
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Funciones"));
			this.add(new JScrollPane(getListaFunciones()), BorderLayout.CENTER);
			this.getBotonera().setLayout(new FlowLayout());
			this.getBotonera().add(getBotonGuardarFuncion());
			this.getBotonera().add(getBotonUtilizarFuncion());
			this.getBotonera().add(getBotonBorrarFuncion());
			this.add(getBotonera(), BorderLayout.SOUTH);
		}
		
		public JButton getBotonBorrarFuncion() {
			return botonBorrarFuncion;
		}

		public void setBotonBorrarFuncion(JButton botonBorrarFuncion) {
			this.botonBorrarFuncion = botonBorrarFuncion;
		}

		private DefaultListModel<FuncionModelo> getModeloListaPropiedades() {
			return modeloListaPropiedades;
		}

		private void setModeloListaPropiedades(
				DefaultListModel<FuncionModelo> modeloListaPropiedades) {
			this.modeloListaPropiedades = modeloListaPropiedades;
		}

		private JPanel getBotonera() {
			return botonera;
		}

		private void setBotonera(JPanel botonera) {
			this.botonera = botonera;
		}

		private JButton getBotonUtilizarFuncion() {
			return botonUtilizarFuncion;
		}

		private void setBotonUtilizarFuncion(JButton botonUtilizarFuncion) {
			this.botonUtilizarFuncion = botonUtilizarFuncion;
		}

		private JButton getBotonGuardarFuncion() {
			return botonGuardarFuncion;
		}

		private void setBotonGuardarFuncion(JButton botonGuardarFuncion) {
			this.botonGuardarFuncion = botonGuardarFuncion;
		}

		private void setListaFunciones(JList<FuncionModelo> listaFunciones) {
			this.listaFunciones = listaFunciones;
		}
		
		public JList<FuncionModelo> getListaFunciones() {
			return listaFunciones;
		}
		
		/**
		 * <b><u>Metodo <code>cargarLista</code>.</u></b>
		 * <p>
		 * Prepara y carga el listado de funciones almacenadas en la base de datos.
		 */
		
		protected void cargarLista() {
			int anchoLista = 0;
			this.getModeloListaPropiedades().clear();
			ListadorControlador.ejecutar();
			ArrayList<Object> vector = (ArrayList<Object>) Contexto.get(ContextoClaves.PROPIEDADES);
			for (Object funcion : vector) {
				if (((FuncionModelo) funcion).getNombre().length() > anchoLista)
					anchoLista = ((FuncionModelo) funcion).getNombre().length();
				this.getModeloListaPropiedades().addElement((FuncionModelo) funcion);
			}
		}
		
	}
	
	public class PanelConsola extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private JTextArea areaTexto;
		
		public PanelConsola() {
			this.setAreaTexto(new JTextArea(8,60));
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Consola"));
			this.add(Box.createHorizontalBox().add(new JScrollPane(getAreaTexto())));
		}

		public JTextArea getAreaTexto() {
			return areaTexto;
		}

		public void setAreaTexto(JTextArea areaTexto) {
			this.areaTexto = areaTexto;
		}	
	
	}
	
	private class EscuchadorFunciones implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(getPanelSur().getPanelListaFunciones().getBotonGuardarFuncion())) {
				SelectorControlador.seleccionar("Guardar Funcion", getPanelSur().getPanelConsola().getAreaTexto(), null);
				getPanelSur().getPanelListaFunciones().cargarLista();
			}
			else if(e.getSource().equals(getPanelSur().getPanelListaFunciones().getBotonUtilizarFuncion())){
				if(getPanelSur().getPanelListaFunciones().getListaFunciones().getSelectedValue() != null) {
					String descripcion = getPanelSur().getPanelListaFunciones().getListaFunciones().getSelectedValue().getDescripcion();
					getPanelSur().getPanelConsola().getAreaTexto().setText("");;					
					getPanelSur().getPanelConsola().getAreaTexto().append(descripcion);	
				}
			}
			else if(e.getSource().equals(getPanelSur().getPanelListaFunciones().getBotonBorrarFuncion())){
				if(getPanelSur().getPanelListaFunciones().getListaFunciones().getSelectedValue() != null) {
					ABMControlador.ejecutar("Funcion", Accion.BAJA, 
							((FuncionModelo) getPanelSur().getPanelListaFunciones().getListaFunciones().getSelectedValue()).getId(), "", "");
					getPanelSur().getPanelListaFunciones().cargarLista();
				}
			}
		}
		
	}
	
	private class EscuchadorNuevoRetazo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SelectorControlador.seleccionar("Crear retazo", null, null);
			((PanelRetazos) getPanelRetazos()).cargarLista();
		}
		
	}
	
}