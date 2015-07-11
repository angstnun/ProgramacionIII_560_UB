package ar.edu.ub.colchita.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import ar.edu.ub.colchita.modelo.Girable;

public class VistaRetazo extends JPanel implements Girable{

	private static final long serialVersionUID = 1L;
	private Integer tamanoLadoDetalle;
	private Dimension dimensionViewRetazo;
	private Dimension dimensionLogicaRetazo;
	private Integer giros;
	private Integer id;
	private Area areaRetazo;
	private Area areaDetalle;
	private Integer gradoActual;
	
	public VistaRetazo(Integer id, Dimension dTotal) {
		this.setGiros(0);
		this.setGradoActual(0);
		this.setId(id);
		this.setDimensionViewRetazo(dTotal);
		this.setDimensionLogicaRetazo(dTotal);
		this.setTamanoLadoDetalle(dTotal);
		this.setTamanoPanel(dTotal);
		this.setOpaque(Boolean.TRUE);
		this.setBackground(Color.BLACK);
	}
	
	private void setTamanoPanel(Dimension dimensionPanel) {
		this.setPreferredSize(dimensionPanel);
		this.setMaximumSize(dimensionPanel);
		this.setMinimumSize(dimensionPanel);
	}

	public Dimension getDimensionLogicaRetazo() {
		return dimensionLogicaRetazo;
	}

	private void setDimensionLogicaRetazo(Dimension dimensionLogicaRetazo) {
		this.dimensionLogicaRetazo = dimensionLogicaRetazo;
	}

	public Integer getTamanoLadoDetalle() {
		return tamanoLadoDetalle;
	}
	
	public static Integer getTamanoLadoDetalle(Dimension dimensionRetazo) {
		return dimensionRetazo.height <= dimensionRetazo.width ? 
				(dimensionRetazo.height*30)/100 : (dimensionRetazo.width*30)/100;
	}

	private void setTamanoLadoDetalle(Dimension dimensionRetazo) {
		this.tamanoLadoDetalle = dimensionRetazo.height <= dimensionRetazo.width ? 
				(dimensionRetazo.height*30)/100 : (dimensionRetazo.width*30)/100;
	}

	public Dimension getDimensionViewRetazo() {
		return dimensionViewRetazo;
	}

	private void setDimensionViewRetazo(Dimension dimension) {
		this.dimensionViewRetazo = dimension;
	}
	
	public Integer getGiros() {
		return giros;
	}

	private void setGiros(Integer giros) {
		this.giros = giros;
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	private Area getAreaRetazo() {
		return areaRetazo;
	}

	private void setAreaRetazo(Area areaRetazo) {
		this.areaRetazo = areaRetazo;
	}

	private Area getAreaDetalle() {
		return areaDetalle;
	}

	private void setAreaDetalle(Area areaDetalle) {
		this.areaDetalle = areaDetalle;
	}

	public Integer getGradoActual() {
		return gradoActual;
	}

	public void setGradoActual(Integer gradoActual) {
		this.gradoActual = gradoActual;
	}

	public void paintComponent(Graphics g){
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setColor(Color.BLUE);
	    this.setAreaRetazo(new Area(new Rectangle2D.Double(0, 0,
	    		this.getDimensionViewRetazo().getWidth(),
	    		this.getDimensionViewRetazo().getHeight())));
	    this.setAreaDetalle(new Area(new Rectangle2D.Double(0, 0,
	    		this.getTamanoLadoDetalle(), this.getTamanoLadoDetalle())));
	    this.getAreaRetazo().subtract(this.getAreaDetalle());
	    double x = 0;
	    double y = 0;
	    if(this.getGiros() > 0){
	    	switch(this.getGradoActual()){
	    	case 90:
	    		x = 0 + getDimensionViewRetazo().getHeight()/2;
	    		y = 0 + getDimensionViewRetazo().getWidth()/2;
	    		break;
	    	case 180:
	    		y = 0 + getDimensionViewRetazo().getHeight()/2;
	    		x = 0 + getDimensionViewRetazo().getWidth()/2;
	    		break;
	    	case 270:
	    		x = 0 + getDimensionViewRetazo().getHeight()/2;
	    		y = 0 + getDimensionViewRetazo().getWidth()/2;
	    		break;
	    	default:
	    		x = 0 + getDimensionViewRetazo().getHeight()/2;
	    		y = 0 + getDimensionViewRetazo().getWidth()/2;
	    		break;
	    	}
	    	g2d.translate(x,y);
	    	g2d.rotate(Math.toRadians(this.getGradoActual()));
	    	g2d.translate(-getDimensionViewRetazo().getWidth()/2, (-getDimensionViewRetazo().getHeight()/2));
	    }
	    g2d.fill(this.getAreaRetazo());
	}
	
	/**
	 * <b><u>Metodo <code>gradoActual</code>.</u></b>
	 * <p>
	 * Este metodo calcula en que grado se encuentra actualmente el retazo. (Ej. 360, 270, etc)
	 * @param nGiros La cantidad de giros a realizar.
	 */
	
	public Integer calcularGradoActual(Integer nGiros) {
    	return nGiros * 90 + this.getGradoActual();
	}
	
	/**
	 * <b><u>Metodo <code>girar</code>.</u></b>
	 * <p>
	 * Este metodo primero cambia el valor del parametro giros de si mismo al valor pasado como parametro nGiros,
	 * 	gira su propia dimension y luego utiliza esta nueva dimension para definir el tamano de si mismo como JPanel.
	 * Al llamar a la funcion repaint() es donde ocurre el giro de la imagen en si,
	 *  segun la cantidad de giros que se pasaron como parametro.
	 * @param nGiros La cantidad de giros a realizar.
	 */
	
	public void girar(Integer nGiros) {
		if (nGiros != null){
			this.setGiros(nGiros);
			this.setGradoActual(this.calcularGradoActual(nGiros));
			for(int i = 0; i < this.getGiros()%4; i++) {
				Integer anchoAux = this.getDimensionLogicaRetazo().width;
				Integer altoAux = this.getDimensionLogicaRetazo().height;
				this.setDimensionLogicaRetazo(new Dimension(altoAux, anchoAux));
			}
			this.setTamanoPanel(this.getDimensionLogicaRetazo());
			this.setGradoActual(this.getGradoActual());
		}
	}
	
	/**
	 * <b><u>Metodo <code>toString</code>.</u></b>
	 * <p>
	 * Convierte el tamano del objeto invocante a un string.
	 * @return String Devuelve el tamano del objeto VistaRetazo invocante como un string.
	 */
	
	
	public String toString() {
		return this.getDimensionLogicaRetazo().toString();
	}
	
	/**
	 * <b><u>Metodo <code>equals</code>.</u></b>
	 * <p>
	 * Este metodo compara la id del objeto VistaRetazo invocante con la de otro objeto VistaRetazo.
	 * @return boolean Devuelve TRUE en caso que los retazos sean iguales.
	 */
	
	public boolean equals(Object obj) {
		if(obj == null) return Boolean.FALSE;
		if(!(obj instanceof Integer)) return Boolean.FALSE;
		if(this.getId().equals((Integer)obj)) return Boolean.TRUE;
		return Boolean.FALSE;
	}

}