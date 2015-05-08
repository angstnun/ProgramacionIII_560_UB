package ar.edu.jdbc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ar.edu.jdbc.controller.ABMController;
import ar.edu.jdbc.controller.InfoController;
import ar.edu.jdbc.controller.ListadoController;
import ar.edu.jdbc.model.PropiedadModel;
import ar.edu.jdbc.util.Accion;
import ar.edu.jdbc.util.Constantes;
import ar.edu.jdbc.util.Contexto;
import ar.edu.jdbc.util.ContextoClaves;

public class PropiedadView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DefaultListModel modeloListaPropiedades;
	private JList listaPropiedades;
	private JTextField campoTextoNumero;
	private JTextField campoTextoNombre;
	private JButton botonAlta;
	private JButton botonBaja;
	private JButton botonModificacion;
	private JButton botonLimpiar;
	
	public PropiedadView() {
		super(Constantes.APPNAME);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize((int) this.getDimensionCentrada().getWidth() / 2, (int) this.getDimensionCentrada()
			.getHeight() / 2);
		this.setLocation((int) this.getDimensionCentrada().getWidth() / 4, (int) this.getDimensionCentrada()
			.getHeight() / 4);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(Constantes.PATH_IMAGE));
		this.setLayout(new BorderLayout());
		this.add(this.getPanelPropiedadesConfigurado(), BorderLayout.NORTH);
		this.add(this.getPanelBotoneraConfigurado(), BorderLayout.SOUTH);
		this.pack();
		this.setVisible(Boolean.TRUE);
	}
	
	private Dimension getDimensionCentrada() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension tamPan = kit.getScreenSize();
		int altPan = tamPan.height;
		int anchoPan = tamPan.width;
		return new Dimension(anchoPan, altPan);
	}
	
	private JPanel getPanelPropiedadesConfigurado() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(this.getPanelListaPropiedadesConfigurado(), BorderLayout.WEST);
		panel.add(this.getPanelDatosPropiedadesConfigurado(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel getPanelListaPropiedadesConfigurado() {
		JPanel panel = new JPanel();
		this.modeloListaPropiedades = new DefaultListModel();
		this.listaPropiedades = new JList(this.modeloListaPropiedades);
		this.listaPropiedades.setVisibleRowCount(10);
		this.cargarLista();
		this.listaPropiedades.addListSelectionListener(new EscuchadorLista());
		panel.add(new JScrollPane(this.listaPropiedades));
		return panel;
	}
	
	private JPanel getPanelDatosPropiedadesConfigurado() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("Numero: "));
		this.campoTextoNumero = new JTextField(10);
		panel.add(this.campoTextoNumero);
		panel.add(new JLabel("Nombre: "));
		this.campoTextoNombre = new JTextField(10);
		panel.add(this.campoTextoNombre);
		return panel;
	}
	
	private JPanel getPanelBotoneraConfigurado() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		this.botonAlta = new JButton("Agregar");
		this.botonAlta.addActionListener(new EscuchadorBoton());
		this.botonBaja = new JButton("Eliminar");
		this.botonBaja.addActionListener(new EscuchadorBoton());
		this.botonModificacion = new JButton("Modificar");
		this.botonModificacion.addActionListener(new EscuchadorBoton());
		this.botonLimpiar = new JButton("Limpiar");
		this.botonLimpiar.addActionListener(new EscuchadorBoton());
		panel.add(this.botonAlta);
		panel.add(this.botonBaja);
		panel.add(this.botonModificacion);
		panel.add(this.botonLimpiar);
		return panel;
	}
	
	protected void cargarLista() {
		int anchoLista = 0;
		this.modeloListaPropiedades.clear();
		mostrarDialogo(ListadoController.execute(), Boolean.FALSE);
		ArrayList<Object> vector = (ArrayList<Object>) Contexto.get(ContextoClaves.PROPIEDADES);
		for (Object prop : vector) {
			if (((PropiedadModel) prop).getNombre().length() > anchoLista)
				anchoLista = ((PropiedadModel) prop).getNombre().length();
			this.modeloListaPropiedades.addElement((PropiedadModel) prop);
		}
	}
	
	private void mostrarDialogo(InfoController infoController, boolean mostrarOK) {
		if (infoController.getCode() != InfoController.OK)
			JOptionPane.showMessageDialog(null, infoController.getMessage(), "Información",
				JOptionPane.ERROR_MESSAGE);
		else if (mostrarOK)
			JOptionPane.showMessageDialog(null, infoController.getMessage(), "Información",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void limpiarCampos() {
		this.campoTextoNumero.setText("");
		this.campoTextoNombre.setText("");
	}
	
	private class EscuchadorBoton implements ActionListener {
		
		public void actionPerformed(ActionEvent actionEvent) {
			
			if (actionEvent.getSource() == botonAlta) {
				mostrarDialogo(ABMController.execute("Propiedad", Accion.ALTA, -1, Long
					.parseLong(campoTextoNumero.getText()), campoTextoNombre.getText()), Boolean.TRUE);
				cargarLista();
			}
			
			if (actionEvent.getSource() == botonBaja) {
				mostrarDialogo(ABMController.execute("Propiedad", Accion.BAJA,
					((PropiedadModel) listaPropiedades.getSelectedValue()).getId(), 0, ""), Boolean.TRUE);
				cargarLista();
			}
			
			if (actionEvent.getSource() == botonModificacion) {
				mostrarDialogo(ABMController.execute("Propiedad", Accion.MODIFICACION,
					((PropiedadModel) listaPropiedades.getSelectedValue()).getId(), Long
						.parseLong(campoTextoNumero.getText()), campoTextoNombre.getText()), Boolean.TRUE);
				cargarLista();
			}
			
			limpiarCampos();
			
		}
		
	}
	
	private class EscuchadorLista implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			
			if (listaPropiedades.getSelectedValue() != null) {
				PropiedadModel propiedad = (PropiedadModel) listaPropiedades.getSelectedValue();
				campoTextoNumero.setText(String.valueOf(propiedad.getNumero()));
				campoTextoNombre.setText(propiedad.getNombre());
			}
			
		}
		
	}
	
}