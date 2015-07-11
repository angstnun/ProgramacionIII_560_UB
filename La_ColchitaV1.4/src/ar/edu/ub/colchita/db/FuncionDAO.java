package ar.edu.ub.colchita.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import ar.edu.ub.colchita.modelo.FuncionModelo;

/**
 * <b><big><u>Clase <code>FuncionDAO</code>.</u></big></b>
 * <p>
 * Esta clase se utiliza para realizar las operaciones de ABM sobre la tabla Funcion, 
 * en base a la informacion cargada en un objeto FuncionDAO. 
 */

public class FuncionDAO extends FuncionModelo implements ListaCallback {
	
	private static String tablaSQL = "";
	private static String camposSQL = "";
	private static String prefSQL = "";
	private static String idSQL = "";
	private static String nombreSQL = "";
	private static String descripcionSQL = "";
	
	/**
	* <b><u>M&eacutetodo <code>registrar</code>.</u></b>
	* <p>Carga la informacion acerca de la tabla Funcion.
	* </p>
	*/
	
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
		this.seleccionarFuncion();
	}
	
	public FuncionDAO() throws Exception {
		this.registrar();
	}
	
	/**
	 * <b><u>M&eacutetodo <code>completarObjetoFuncion</code>.</u></b>
	 * <p>A partir de un objeto FuncionModel y un ResultSet acorde a este, se inicializan todos los atributos
	 * del FuncionModel en base al ResultSet.
	 * </p>
	 * @param funcionModelo 
	 * @param resultSet
	 */
	
	private void completarObjetoFuncion(FuncionModelo funcionModelo, ResultSet resultSet) throws Exception {
		funcionModelo.setId(resultSet.getLong(1));
		funcionModelo.setNombre(resultSet.getString(2));
		funcionModelo.setDescripcion(resultSet.getString(3));
	}
	
	/**
	 * <b><u>M&eacutetodo <code>seleccionarFuncion</code>.</u></b>
	 * <p>Permite seleccionar una funcion de la base de datos segun la id del objeto FuncionDAO actual.
	 * </p>
	 */
	
	private void seleccionarFuncion() throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.selectObjeto(idSQL + " = " + this.getId(), this);
		} finally {
			conexion.cerrarConexion();
		}
	}
	
	/**
	 * <b><u>M&eacutetodo <code>getCampo</code>.</u></b>
	 * <p>Es un switch que segun el identificador de campo que le entregemos, nos devuelve el atributo del
	 * FuncionDAO correspondiente a nuestro identificador.
	 * </p>
	 * @param nombreCampoTabla El nombre del campo que deseamos consultar.
	 * @return Devuelve el atributo correspondiente a el parametro nombreCampoTabla ingresado.
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
	 * <b><u>M&eacutetodo <code>listaFill</code>.</u></b>
	 * <p>A partir de un resultSet, el objeto FuncionDAO invoca a la funcion completarObjetoFuncion pasandose
	 * a si mismo como parametro junto al resultSet, para asi poder rellenar todos los campos de si mismo.
	 * </p> 
	 * @param resultSet
	 */
	
	public void listaFill(ResultSet resultSet) throws Exception {
		this.completarObjetoFuncion(this, resultSet);
	}
	
	/**
	 * <b><u>M&eacutetodo <code>listaCallback</code>.</u></b>
	 * <p>A partir de un resultSet, el objeto FuncionDAO invoca al metodo completarObjetoFuncion pasando
	 * un objeto FuncioModelo como parametro junto al resultSet, para asi poder rellenar todos los campos de si mismo.
	 * </p> 
	 * @param resultSet
	 * @return Object Devuelve un objeto FuncionModelo.
	 */
	
	public Object listaCallback(ResultSet resultSet) throws Exception {
		FuncionModelo funcion = new FuncionModelo();
		this.completarObjetoFuncion(funcion, resultSet);
		return funcion;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>seleccionarTodos</code>.</u></b>
	 * <p>Este m&eacutetodo devuelve todos los nombres de las funciones de la tabla Funcion.  
	 * </p>
	 * @return ArrayList<Object> Devuelve un array con todos los campos de la columna Nombre de la tabla Funcion.
	 */
	
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
	
	/**
	 * <b><u>M&eacutetodo <code>seleccionarFuncionPorNombre</code>.</u></b>
	 * <p>Este m&eacutetodo devuelve todas las filas de la tabla Funcion.  
	 * </p>
	 * @return ArrayList<Object> Devuelve un array con todos los campos de la tabla Funcion, ordenados segun su nombre.
	 */
	
	public ArrayList<Object> seleccionarFuncionPorNombre() throws Exception {
		return this.seleccionarFuncion(null, getCampo("NOMBRE"), false, -1, -1);
	}
	
	/**
	 * <b><u>M&eacutetodo <code>seleccionarFuncionPorNombre</code>.</u></b>
	 * <p>Este m&eacutetodo devuelve todas las filas de la tabla Funcion, segun un filtro, tipo de orden, si es orden inverso o
	 * limites determinados. 
	 * </p>
	 * @param filtro El filtro de la consulta.
	 * @param orden El orden en el que queremos que nos devuelva el resultado.
	 * @param ordenInverso Es un boolean que determina si queremos que nuestro resultado este ordenado inversamente.
	 * @param desde El numero de fila desde que el queremos obtener informacion.
	 * @param cant La cantidad de filas que queremos obtener como resultado.
	 * @return ArrayList<Object> Devuelve un array con todos los campos de la tabla Funcion, ordenados segun su nombre.
	 */
	
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
	
	/**
	 * <b><u>M&eacutetodo <code>altaFuncion</code>.</u></b>
	 * <p>Da de alta este objeto FuncionDAO en la tabla Funcion. 
	 * </p>
	 */
	
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
	
	/**
	 * <b><u>M&eacutetodo <code>altaFuncion</code>.</u></b>
	 * <p>Da de alta este objeto FuncionDAO en la tabla Funcion. 
	 * </p>
	 * @param id Id de la funcion a dar de alta.
	 * @param nombre Nombre de la funcion a dar de alta.
	 * @param desc Descripcion de la funcion a dar de alta.
	 */
	
	public void altaFuncion(long id, String nombre, String desc) throws Exception {
		this.setId(id);
		this.setNombre(nombre);
		this.altaFuncion();
	}
	
	/**
	 * <b><u>M&eacutetodo <code>bajaFuncion</code>.</u></b>
	 * <p>Da de baja este objeto FuncionDAO en la tabla Funcion. 
	 * </p>
	 */
	
	public void bajaFuncion() throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.ejecutarModificacionTabla("DELETE FROM " + tablaSQL + " WHERE " + idSQL + " = " + this.getId());
			conexion.commit();
		} finally {
			conexion.cerrarConexion();
		}
	}
	
	/**
	 * <b><u>M&eacutetodo <code>modificarFuncion</code>.</u></b>
	 * <p>Modifica el registro equivalente a este objeto FuncionDAO dentro de la tabla Funcion.
	 * </p>
	 */
	
	public void modificarFuncion() throws Exception {
		this.modificarFuncion(nombreSQL + " = '" + nombre + "' ");
	}
	
	/**
	 * <b><u>M&eacutetodo <code>modificarFuncion</code>.</u></b>
	 * <p>Modifica el registro equivalente a este objeto FuncionDAO dentro de la tabla Funcion.
	 * @param updQuery Es la query para actualizar el registro de la tabla Funcion.
	 * </p>
	 */
	
	public void modificarFuncion(String updQuery) throws Exception {
		ConexionDB conexion = new ConexionDB();
		try {
			conexion.ejecutarModificacionTabla("UPDATE " + tablaSQL + " SET " + updQuery + " WHERE " + idSQL + " = " + this.getId());
			conexion.commit();
		} finally {
			conexion.cerrarConexion();
		}
	}

	@Override
	public void setCount(long i) {
	}
	
}