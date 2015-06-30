package ar.edu.ub.colchita.modelo;

public class FuncionModelo {
	
	protected long id;
	protected String nombre;
	protected String descripcion;
	
	public FuncionModelo(){
		
	}
	
	public FuncionModelo(String id, String nombre , String descripcion) {
		implementarFuncion(Long.decode(id).longValue(), nombre, descripcion);
	}
	
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