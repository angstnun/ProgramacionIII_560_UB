package ar.edu.ub.colchita.modelo;

import java.awt.Dimension;
import ar.edu.ub.colchita.utilidades.Contexto;

/**
 * <b><big><u>Clase <code>Retazo</code>.</u></big></b>
 * Implementa 2 interfaces: <code>Girable , Cosible</code>
 * <p>
 * Esta clase representa el objeto basico con el que trabaja el lenguaje de programacion "Colchita", 
 * un retazo de tela con una determinada dimension.
 * <p>
 */

public class RetazoModelo {
	
	private Dimension dimension;
	private Integer id;
	
	/**
	 * <u>Constructor de 2 parametros.</u>
	 * <p>
	 * Contruye un retazo de dimensiones <code>ancho</code> X <code>alto</code> cuyo <code>id</code> 
	 * se obtiene a partir de la cantidad de retazos del <code>Contexto</code>.
	 * <p>
	 * @param ancho Representa el ancho de la dimension del retazo.
	 * @param alto Representa el alto de la dimension del retazo.
	 */
	
	public RetazoModelo(int ancho,int alto){
		this.setDimension(new Dimension(ancho,alto));
		this.setId(Contexto.getCantidadRetazos() + 1);
	}
	
	/**
	 * <u>Constructor de 1 parametro.</u>
	 * <p> 
	 * Construye un retazo cuadrado, es decir, de igual <code>alto</code> y <code>ancho</code> 
	 * a partir del {@link Retazo(int ancho,int alto) constructor} de dos parametros.
	 * <p> 
	 * @param anchoalto Representa el ancho y el alto de la dimension del retazo.
	 */
	
	public RetazoModelo(Dimension dimension){
		this.setDimension(dimension);
		this.setId(Contexto.getCantidadRetazos() + 1);
	}
	
	public RetazoModelo(int anchoalto){
		this(anchoalto,anchoalto);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Dimension getDimension() {
		return this.dimension;
	}
	
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public int getAncho() {
		return (int)this.getDimension().getWidth();
	}

	public void setAncho(int ancho) {
		this.getDimension().width = ancho;
	}

	public int getAlto() {
		return (int)this.getDimension().getHeight();
	}

	public void setAlto(int alto) {
		this.getDimension().height = alto;
	}
	
	public boolean equals(Object objeto){
		if(objeto==null){
			return false;
		}
		if(!(objeto instanceof RetazoModelo)){
			return false;
		}
		if(((RetazoModelo)objeto).getAlto()!=this.getAlto()){
			return false;
		}
		return true;
	}
	
	/**
	 * <b><u>Metodo <code>toString</code>.</u></b>
	 * <p>
	 * Devuelve informacion detallada de un objeto de la clase.
	 * <p>
	 * @return Descripcion detallada de las dimensiones del retazo y su identificador.
	 */
	
	public String toString() {
		return "Retazo" + id + "    " + "X: " + this.getAncho() + " Y: " + this.getAlto();
	}

}