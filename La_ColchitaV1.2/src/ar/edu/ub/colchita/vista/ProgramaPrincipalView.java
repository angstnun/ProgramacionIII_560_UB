package ar.edu.ub.colchita.vista;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import ar.edu.ub.colchita.utilidades.Constantes;
import ar.edu.ub.colchita.controlador.*;
import ar.edu.ub.colchita.modelo.javacc.*;
import ar.edu.ub.colchita.modelo.Escritor;

public class ProgramaPrincipalView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panelCodigo;
	private JPanel panelAreaTrabajo;
	private JTextArea areaTexto;
	
	public ProgramaPrincipalView() {
		this.setTitle("La Colchita");
		this.setLayout(new BorderLayout());
		this.setSize(700,500);
		this.setLocation(400,200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(Constantes.RUTA_ICONO));
		this.setPanelCodigo(new JPanel());
		this.setPanelAreaTrabajo(new JPanel());
		this.setAreaTexto(new JTextArea(10,50));
		this.getPanelCodigo().setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Codigo"));
		this.getPanelCodigo().add(Box.createHorizontalBox().add(new JScrollPane(this.getAreaTexto())));
		this.getPanelAreaTrabajo().setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"AreaTrabajo"));
		this.add(new BarraView(new ActionListenerCompilar(this.getAreaTexto()),new ActionListenerEjecutar(),new ActionListenerGuardar()),BorderLayout.NORTH);
		this.add(this.getPanelCodigo(),BorderLayout.SOUTH);
		this.add(this.getPanelAreaTrabajo(),BorderLayout.CENTER);
		this.setVisible(Boolean.TRUE);
	}
	
	public JPanel getPanelCodigo() {
		return this.panelCodigo;
	}
	
	public void setPanelCodigo(JPanel panel1) {
		this.panelCodigo = panel1;
	}
	
	public JPanel getPanelAreaTrabajo() {
		return this.panelAreaTrabajo;
	}
	
	public void setPanelAreaTrabajo(JPanel panelAreaTrabajo) {
		this.panelAreaTrabajo = panelAreaTrabajo;
	}
	
	public JTextArea getAreaTexto() {
		return this.areaTexto;
	}
	
	public void setAreaTexto(JTextArea areaTexto) {
		this.areaTexto = areaTexto;
	}
	
	private class ActionListenerCompilar implements ActionListener {
		
		private JTextArea areaTexto;
		
		public ActionListenerCompilar(JTextArea areaTexto) {
			super();
			this.setAreaTexto(areaTexto);
		}
		
		public void actionPerformed(ActionEvent e) {
			try{
				Escritor.abrir();
				new Colchita(new StringReader(this.getAreaTexto().getText())).principal();
				JOptionPane.showMessageDialog(null,"Compilacion correcta.","Compilador",JOptionPane.PLAIN_MESSAGE);
				Escritor.cerrar();
			}
			catch(ParseException ex) {
				JOptionPane.showMessageDialog(null,ex.getMessage(),"Compilador",JOptionPane.WARNING_MESSAGE);
				File file=new File(Constantes.RUTA_ARCHIVOS_FUENTE);
				if(file!=null) {
					file.delete();
				}
				Escritor.cerrar();
			}
			catch(TokenMgrError er) {
				JOptionPane.showMessageDialog(null,er.getMessage(),"Compilador",JOptionPane.WARNING_MESSAGE);
				File file=new File(Constantes.RUTA_ARCHIVOS_FUENTE);
				if(file!=null) {
					file.delete();
				}
				Escritor.cerrar();
			}
		}
		
		public JTextArea getAreaTexto() {
			return this.areaTexto;
		}
		
		public void setAreaTexto(JTextArea areaTexto) {
			this.areaTexto = areaTexto;
		}
	}
	
	private class ActionListenerEjecutar implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(new Analizador(Constantes.RUTA_ARCHIVOS_FUENTE).ejecutar());
			//MEJORAR....
		}
	}
	
	private class ActionListenerGuardar implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//TERMINAR....
		}
	}
	
	public class BarraView extends JToolBar{
		
		private static final long serialVersionUID = 1L;
		private JButton botonCompilar;
		private JButton botonEjecutar;
		private JButton botonGuardar;
		
		public BarraView(ActionListener actionListenerCompilar,ActionListener actionListenerEjecutar,ActionListener actionListenerGuardar) {
			this.setBotonCompilar(new JButton("Compilar"));
			this.setBotonEjecutar(new JButton("Ejecutar"));
			this.setBotonGuardar(new JButton("Guardar"));
			this.getBotonCompilar().addActionListener(actionListenerCompilar);
			this.getBotonEjecutar().addActionListener(actionListenerEjecutar);
			this.getBotonGuardar().addActionListener(actionListenerGuardar);
			this.add(this.getBotonCompilar());
			this.add(this.getBotonEjecutar());
			this.add(this.getBotonGuardar());
		}
		
		public JButton getBotonCompilar() {
			return botonCompilar;
		}
		
		public void setBotonCompilar(JButton botonCompilar) {
			this.botonCompilar = botonCompilar;
		}
		
		public JButton getBotonEjecutar() {
			return botonEjecutar;
		}
		
		public void setBotonEjecutar(JButton botonEjecutar) {
			this.botonEjecutar = botonEjecutar;
		}
		
		public JButton getBotonGuardar() {
			return botonGuardar;
		}
		
		public void setBotonGuardar(JButton botonGuardar) {
			this.botonGuardar = botonGuardar;
		}
	}
	
}