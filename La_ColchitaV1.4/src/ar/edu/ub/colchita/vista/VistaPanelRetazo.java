package ar.edu.ub.colchita.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.utilidades.Constantes;
import ar.edu.ub.colchita.utilidades.Contexto;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelPreseleccion;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelRetazos;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelSur;

/**
* <h1>La Colchita - Vista - PanelRetazo</h1>
* Estructura del panel de retazos, en el cual se puede aplicar acciones sobre ellos. (Girar, utilizar, limpiar).
*/

public class VistaPanelRetazo extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JPanel lienzoRetazo;
	private JPanel panelBotones;
	private JButton botonGirar;
	private JButton botonUtilizar;
	private JButton botonCoser;
	private Integer id;
	
    /**
     * Colocación de los elementos (JPanel y JButton), en el panel.
     * Ubicados en el sur y centrados.
     */
	
	public VistaPanelRetazo(Integer id){
		this.setId(id);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setLayout(new BorderLayout());
		this.setLienzoRetazo(new JPanel());
		this.getLienzoRetazo().setLayout(new BoxLayout(getLienzoRetazo(),BoxLayout.Y_AXIS));
		this.setPanelBotones(new JPanel());
		this.setBotonGirar(new JButton(""));
		this.getBotonGirar().setIcon(new ImageIcon(Constantes.GIRAR_RETAZO_ICONO));
		this.setBotonUtilizar(new JButton(""));
		this.getBotonUtilizar().setIcon(new ImageIcon(Constantes.SELECCIONAR_RETAZO_ICONO));
		this.setBotonCoser(new JButton(""));
		this.getBotonCoser().setIcon(new ImageIcon(Constantes.COSER_RETAZO_ICONO));
		this.getBotonGirar().addActionListener(new EscuchadorBotones());
		this.getBotonUtilizar().addActionListener(new EscuchadorBotones());
		this.getBotonCoser().addActionListener(new EscuchadorBotones());
		this.getPanelBotones().add(this.getBotonGirar());
		this.getPanelBotones().add(this.getBotonUtilizar());
		this.getPanelBotones().add(this.getBotonCoser());
		this.add(this.getLienzoRetazo(),BorderLayout.CENTER);
		this.add(this.getPanelBotones(),BorderLayout.SOUTH);
	}
	
	
	public Integer getId() {
		return id;
	}


	private void setId(Integer id) {
		this.id = id;
	}


	public JButton getBotonCoser() {
		return botonCoser;
	}


	private void setBotonCoser(JButton botonCoser) {
		this.botonCoser = botonCoser;
	}


	public JPanel getLienzoRetazo() {
		return lienzoRetazo;
	}


	private void setLienzoRetazo(JPanel lienzoRetazo) {
		this.lienzoRetazo = lienzoRetazo;
	}
	
	/**
	 * <b><u>Metodo <code>dibujarRetazo</code>.</u></b>
	 * <p>
	 * Dibuja un retazo dentro del lienzo de preseleccion.
	 * @param imagenRetazo El retazo a dibujar.
	 */
	
	public void dibujarRetazo(JLabel imagenRetazo) {
		if(imagenRetazo != null) {
			imagenRetazo.setAlignmentX(CENTER_ALIGNMENT);
			if(imagenRetazo != null) {
				this.getLienzoRetazo().removeAll();
				this.getLienzoRetazo().add(Box.createVerticalGlue());
				this.getLienzoRetazo().add(imagenRetazo);
				this.getLienzoRetazo().add(Box.createVerticalGlue());
				revalidate();
				repaint();			
			}			
		}
	}
	
	/**
	 * <b><u>Metodo <code>limpiarLienzo</code>.</u></b>
	 * <p>
	 * Limpia el lienzo de preseleccion.
	 */
	
	public void limpiarLienzo() {
		if(this.getLienzoRetazo().getComponents().length > 0) {
			this.getLienzoRetazo().removeAll();
			this.repaint();			
		}
	}

	public JButton getBotonGirar() {
		return this.botonGirar;
	}
	
	public void setBotonGirar(JButton botonGirar) {
		this.botonGirar = botonGirar;
	}
	
	public JButton getBotonUtilizar() {
		return this.botonUtilizar;
	}
	
	private void setBotonUtilizar(JButton botonUtilizar) {
		this.botonUtilizar = botonUtilizar;
	}
	
	public JPanel getPanelBotones() {
		return this.panelBotones;
	}
	
	private void setPanelBotones(JPanel panelBotones) {
		this.panelBotones = panelBotones;
	}

	private class EscuchadorBotones implements ActionListener {
		
		public EscuchadorBotones(){}
		
		private RetazoModelo getRetazoSeleccionado() {
			if(((PanelRetazos)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor(getLienzoRetazo())).getPanelRetazos()).getListaRetazos().getSelectedValue() == null) {
				InformadorControlador.mostrarMensaje("Primero debe seleccionar un retazo de la lista o crear uno", "Colchita", 2);
				return null;
			}
			else
				return ((RetazoModelo)((PanelRetazos)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor(getLienzoRetazo())).getPanelRetazos()).getListaRetazos().getSelectedValue());
		}
		
		private JLabel getDibujoRetazo(RetazoModelo retazoModelo) {
			if(retazoModelo != null) {
				VistaRetazo vistaRetazo = ((PanelRetazos)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor(getLienzoRetazo())).getPanelRetazos()).getVistaRetazos().get(retazoModelo.getId()-1);
				BufferedImage img = new BufferedImage(vistaRetazo.getDimensionLogicaRetazo().width, vistaRetazo.getDimensionLogicaRetazo().height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = img.createGraphics();
				g2d.setColor(Color.BLUE);
				Area areaRetazo = new Area(new Rectangle2D.Double(0, 0, vistaRetazo.getDimensionLogicaRetazo().getWidth(),
						vistaRetazo.getDimensionLogicaRetazo().getHeight()));
				Area areaDetalle = new Area(new Rectangle2D.Double(0, 0, vistaRetazo.getTamanoLadoDetalle(), vistaRetazo.getTamanoLadoDetalle()));
				areaRetazo.subtract(areaDetalle);
				g2d.fill(areaRetazo);
				vistaRetazo.printAll(g2d);
				g2d.dispose();
				return new JLabel(new ImageIcon(img));				
			}
			return null;
		}
	
		public void actionPerformed(ActionEvent actionEvent) {
			
			if (actionEvent.getSource().equals(getBotonUtilizar())) {
				if(getId()==1){
					((PanelPreseleccion)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelPreseleccion()).getPrimerPanelSeleccion().dibujarRetazo(this.getDibujoRetazo(this.getRetazoSeleccionado()));
					Contexto.set("preseleccion1", this.getRetazoSeleccionado());
				}
				if(getId()==2){
					((PanelPreseleccion)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelPreseleccion()).getSegundoPanelSeleccion().dibujarRetazo(this.getDibujoRetazo(this.getRetazoSeleccionado()));
					Contexto.set("preseleccion2", this.getRetazoSeleccionado());
				}
			}
			
			if (actionEvent.getSource().equals(getBotonGirar())) {
				if(getId()==1){
					((PanelSur)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelSur()).getPanelConsola().getAreaTexto().append("girar(retazo1)");
				}
				if(getId()==2){
					((PanelSur)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelSur()).getPanelConsola().getAreaTexto().append("girar(retazo2)");
				}
			}
			
			if (actionEvent.getSource().equals(getBotonCoser())) {
				if(getId()==1){
					((PanelSur)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelSur()).getPanelConsola().getAreaTexto().append("coser(retazo1,");
				}
				if(getId()==2){
					((PanelSur)((VistaProgramaPrincipal)SwingUtilities.getWindowAncestor((Component)actionEvent.getSource())).getPanelSur()).getPanelConsola().getAreaTexto().append("coser(retazo2,");
				}
			}
		}	
	}
	
}