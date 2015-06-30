package ar.edu.ub.colchita.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import ar.edu.ub.colchita.modelo.FuncionModelo;

public class FuncionDAO extends FuncionModelo implements ListaCallback {
	
	private static String tablaSQL = "";
	private static String camposSQL = "";
	private static String prefSQL = "";
	private static String idSQL = "";
	private static String nombreSQL = "";
	private static String descripcionSQL = "";
	
	private void registrar() throws Exception {
		tablaSQL = "Funcion";
		prefSQL = "Funcion_";
		idSQL = prefSQL + "Id";
		nombreSQL = prefSQL + "Nombre";
		descripcionSQL = prefSQL + "Descripcion";
		camposSQL = idSQL + ","  + nombreSQL + "," + descripcionSQL;
	}
	
	public FuncionDAO(String id, String nombre, String desc) throws Exception {
		super(id, nombre, desc);
		this.registrar();
		this.altaFuncion();
	}
	
	public FuncionDAO(String nombre, String desc) throws Exception {
		this("0", nombre, desc);
	}
	
	public FuncionDAO(long id) throws Exception {
		super();
		this.registrar();
		this.setId(id);
		this.selectFuncion();
	}
	
	public FuncionDAO() throws Exception {
		this.registrar();
	}
	
	/**
	 * A partir de un objeto FuncionModel y el resultSet acorde a este, se inicializan todos los atributos
	 * del FuncionModel en base al resultSet.
	 * @param propiedad
	 * @param resultSet
	 * @throws Exception
	 */
	
	private void completarObjetoFuncion(FuncionModelo propiedad, ResultSet resultSet) throws Exception {
		propiedad.setId(resultSet.getLong(1));
		propiedad.setNombre(resultSet.getString(2));
		propiedad.setDescripcion(resultSet.getString(3));
	}
	
	/**
	 * Permite seleccionar una funcion de la base de datos segun la id del objeto FuncionDAO actual.
	 * @throws Exception
	 */
	
	private void selectFuncion() throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.selectObjeto(idSQL + " = " + this.getId(), this);
		} finally {
			conexion.cerrarConexion();
		}
	}
	
	/**
	 * Es un switch que segun el identificador de campo que le entregemos, nos devuelve el atributo del
	 * FuncionDAO correspondiente a nuestro identificador.
	 * 
	 * @param nombreCampoTabla
	 * @return Devuelve el atributo de el objeto FuncionDAO correspondiente a el nombreCampoTabla ingresado
	 */
	
	public String getCampo(String nombreCampoTabla) {
		if (nombreCampoTabla == null)
			return null;
		if (nombreCampoTabla.compareToIgnoreCase("ID") == 0)
			return idSQL;
		if (nombreCampoTabla.compareToIgnoreCase("NOMBRE") == 0)
			return nombreSQL;
		if (nombreCampoTabla.compareToIgnoreCase("DESCRIPCION") == 0)
			return descripcionSQL;
		return null;
	}
	
	public boolean doCount() {
		return Boolean.FALSE;
	}
	
	public String listaTabla() {
		return tablaSQL;
	}
	
	public String listaCampos() {
		return camposSQL;
	}
	

	/**
	 * A partir de un resultSet, el objeto FuncionDAO invoca a la funcion completarObjetoFuncion pasandose
	 * a si mismo como parametro junto al resultSet, para asi poder rellenar todos los campos de si mismo. 
	 * @param resultSet
	 */
	public void listaFill(ResultSet resultSet) throws Exception {
		this.completarObjetoFuncion(this, resultSet);
	}
	
	public Object listaCallback(ResultSet resultSet) throws Exception {
		FuncionModelo propiedad = new FuncionModelo();
		this.completarObjetoFuncion(propiedad, resultSet);
		return propiedad;
	}
	
	public ArrayList<Object> seleccionarTodos() throws Exception {
		ArrayList<Object> vector = null;
		ConexionDB conexion = new ConexionDB();
		try {
			vector = conexion.listaObjetos(null, getCampo("NOMBRE"), false, -1, -1, this);
		} finally {
			conexion.cerrarConexion();
		}
		return vector;
	}
	
	public ArrayList<Object> seleccionarFuncionPorNombre() throws Exception {
		return this.seleccionarFuncion(null, getCampo("NOMBRE"), false, -1, -1);
	}
	
	public ArrayList<Object> seleccionarFuncion(String filtro, String orden, boolean ordenInverso, long from,
			long to) throws Exception {
		ArrayList<Object> vector = null;
		 ConexionDB conexion = new ConexionDB();
		try {
			vector = conexion.listaObjetos(filtro, getCampo(orden), ordenInverso, from, to, this);
		} finally {
			conexion.cerrarConexion();
		}
		return vector;
	}
	
	private void altaFuncion() throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			this.setId(conexion.insert(tablaSQL, camposSQL.substring(camposSQL.indexOf(',') + 1), "@ID@" + 
		",'" + this.getNombre() + "'," + "'" + this.getDescripcion() + "'"));
			conexion.commit();
		} finally {
			conexion.cerrarConexion();
		}
	}
	public void altaFuncion(long id, String nombre, String desc) throws Exception {
		this.setId(id);
		this.setNombre(nombre);
		this.altaFuncion();
	}
	
	public void bajaFuncion() throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.execute("DELETE FROM " + tablaSQL + " WHERE " + idSQL + " = " + this.getId());
			conexion.commit();
		} finally {
			conexion.cerrarConexion();
		}
	}
	
	public void modificarFuncion() throws Exception {
		this.updateFuncion(nombreSQL + " = '" + nombre + "' ");
	}
	
	public void updateFuncion(String upd) throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.execute("UPDATE " + tablaSQL + " SET " + upd + " WHERE " + idSQL + " = " + this.getId());
			conexion.commit();
		} finally {
			conexion.cerrarConexion();
		}
	}

	@Override
	public void setCount(long i) {
	}
	
}