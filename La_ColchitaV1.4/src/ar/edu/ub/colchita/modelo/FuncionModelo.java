package ar.edu.ub.colchita.modelo;

/**
 * <b><big><u>Clase <code>FuncionModelo</code>.</u></big></b>
 * <p>
 * Esta clase se utiliza para cargar los registros de la tabla Funciones de la base de datos en memoria. 
 */

public class FuncionModelo {
	
	protected long id;
	protected String nombre;
	protected String descripcion;
	
	public FuncionModelo(){}
	
	public FuncionModelo(String id, String nombre , String descripcion) {
		implementarFuncion(Long.decode(id).longValue(), nombre, descripcion);
	}
	
	/**
	 * <b><u>M&eacutetodo <code>implementarFuncion</code>.</u></b>
	 * <p>Rellena todos los atributos de este objeto FuncionModelo en base a los parametros.
	 * </p>
	 * @param id Es la id de la funcion.
	 * @param nombre Es el nombre la funcion.
	 * @param descripcion Es la funcion en si misma.
	 */
	
	private void implementarFuncion(long id, String nombre, String descripcion) {
		this.setId(id);
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		if (this.nombre == null)
			return ("--");
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdStr() {
		return Long.toString(this.getId(), 10);
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(String id) {
		if (id != null)
			this.id = Long.decode(id).longValue();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * <b><u>Metodo <code>toString</code>.</u></b>
	 * <p>
	 * @return Devuelve el nombre de la funcion en un string.
	 */
	
	public String toString() {
		return this.getNombre().toUpperCase();
	}
	
}