package ar.edu.ub.colchita.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.utilidades.BuscadorRutas;
import ar.edu.ub.colchita.utilidades.Constantes;

/**
 * <b><big><u>Clase <code>ConexionDB</code>.</u></big></b>
 * <p>
 * Esta clase contiene todos los metodos relacionados a la interaccion con la base de datos, desde la conexion hasta
 * la ejecucion de queries preparadas.
 * <p>
 */

public class ConexionDB {
	
	private Connection conexion;
	private String fuenteDB = Constantes.RUTA_DB;
	private int tipoDB = Constantes.TIPO_DB;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private long valorDesde = -1;
	private long valorHasta = -1;
	private long valorActual = 0;
	
	private final int ORACLE = 0x0001;
	private final int MYSQL = 0x0002;
	private final int ACCESS = 0x0003;
	
	public ConexionDB() {
		this.setConexion();
		this.setAutoCommit(Boolean.FALSE);
	}

	private void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	private PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	private void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	private Statement getStatement() {
		return statement;
	}

	private void setStatement(Statement statement) {
		this.statement = statement;
	}

	private ResultSet getResultSet() {
		return resultSet;
	}

	private void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	/**
	* <b><u>M&eacutetodo <code>setConexion</code>.</u></b>
	* <p>Inicializa el atributo conexion de la clase. 
	* </p>
	* @throws Exception en caso de que haya un error al crear el objeto conexion.
	*/

	private void setConexion() {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Files.copy(BuscadorRutas.getRecursoComoInputStream(this.fuenteDB), new File(Constantes.RUTA_TEMP_DB).toPath(), StandardCopyOption.REPLACE_EXISTING);
			this.setConexion(DriverManager.getConnection(
					"jdbc:ucanaccess://" + Constantes.RUTA_TEMP_DB , "", ""));
		} catch (SQLException e) {
			InformadorControlador.mostrarMensaje("Error con el driver JDBC", "ConexionDB", 1);
		} catch (IOException e) {
			InformadorControlador.mostrarMensaje("Imposible crear la base de datos temporal", "ConexionDB", 1);
		} catch (ClassNotFoundException e) {
			InformadorControlador.mostrarMensaje("Error con el driver ucanaccess", "ConexionDB", 1);
		}
		this.hayAlertasConexion();
	}
	
	/**
	* <b><u>M&eacutetodo <code>setAutoCommit</code>.</u></b>
	* <p>M&eacutetodo para confirmar las modificaciones a la base de datos automaticamente.  
	* </p>
	*/
	
	public void setAutoCommit(Boolean b) {
		try {
			this.getConexion().setAutoCommit(b);
		} catch (SQLException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(), "ConexionDB", 1);
		}	
	}
	
	/**
	* <b><u>M&eacutetodo <code>hayAlertasConexion</code>.</u></b>
	* <p>M&eacutetodo para identificar los problemas ocurridos al conectarse a la base de datos.
	* </p>
	*/
	
	private void hayAlertasConexion() {
		SQLWarning warn;
		try {
			warn = conexion.getWarnings();
			if (warn != null)
				InformadorControlador.mostrarMensaje(this.advertenciasDB(warn), "Error en la Conexion", 1);
		} catch (SQLException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(), "Error en la Conexion", 1);
		}
	}
	
	public Connection getConexion() {
		return this.getConexion();
	}
	
	/**
	* <b><u>M&eacutetodo <code>cerrarConexion</code>.</u></b>
	* <p>M&eacutetodo para cerrar la conexion a la base de datos.
	* </p>
	*/
	
	public void cerrarConexion() {
		try {
			this.getConexion().close();
		} catch (Exception e) {
			InformadorControlador.mostrarMensaje("Error al cerrar la conexion : " + e.getMessage(), "ConexionDB", 1);
		};
	}
	
	/**
	* <b><u>M&eacutetodo <code>advertenciaDB</code>.</u></b>
	* <p>A partir de una excepcion SQLWarning, este m&eacutetodo formatea su contenido en un string y lo devuelve. 
	* </p>
	* @param warning la excepcion a convertir a string.
	* @return String la excepcion formateada a string, preparada para ser facilmente legible.
	*/
	
	public String advertenciasDB(SQLWarning warning) {
		String txt = "";
		while (warning != null) {
			if (txt.length() > 0)
				txt = "\n" + txt;
			txt = txt + warning.getMessage();
			warning = warning.getNextWarning();
		}
		return txt;
	}
	
	/**
	* <b><u>M&eacutetodo <code>errorDB</code>.</u></b>
	* <p>A partir de una excepcion generica y una query, este m&eacutetodo formatea sus contenidos en un string y lo devuelve. 
	* </p>
	* @param excepcion La excepcion lanzada.
	* @param query La consulta que genero el error.
	* @return String la excepcion y la query formateadas en un string, preparadas para ser facilmente legibles.
	*/
	
	public String errorDB(Exception excepcion, String query) {
		String texto = "";
		if (query != null)
			texto = "Action: " + query + "\n";
		StackTraceElement[] s;
		String mensaje = "(null)";
		int i;
		if (excepcion != null) {
			mensaje = "\n" + excepcion.toString() + " at \n";
			s = excepcion.getStackTrace();
			for (i = 0; i < s.length; i++) {
				mensaje += "\t" + s[i].getClassName() + "::" + s[i].getMethodName();
				if (!s[i].isNativeMethod())
					mensaje += " at " + s[i].getFileName() + ":" + s[i].getLineNumber();
				mensaje += "\n";
			}
		}
		texto += mensaje;
		return texto;
	}
	
	/**
	* <b><u>M&eacutetodo <code>colsCursor</code>.</u></b>
	* <p>Devuelve el numero de columnas del atributo resultSet sobre el que se esta trabajando dentro de la clase. 
	* </p>
	* @return int El numero de columnas que contiene el resultSet de la clase
	*/
	
	public int colsCursor() {
		if (this.getResultSet() == null)
			return 0;
		try {
			return this.getResultSet().getMetaData().getColumnCount();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	* <b><u>M&eacutetodo <code>inicializarPreparedStatement</code>.</u></b>
	* <p>Inicializa el atributo preparedStatement de la clase, en base a la query pasada como parametro.
	* </p>
	* @param query La consulta a realizar.
	* @throws Exception En caso de que falle la modificacion, se lanza una nueva excepcion.
	*/
	
	public PreparedStatement inicializarPreparedStatement(String query) throws Exception {
		if (this.getConexion() == null)
			return null;
		try {
			this.setPreparedStatement(this.getConexion().prepareStatement(query)); 
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		return this.getPreparedStatement();
	}
	
	/**
	* <b><u>M&eacutetodo <code>ejecutarPreparedStatement</code>.</u></b>
	* <p>Ejecuta el atributo preparedStatement de la clase. 
	* </p>
	* @throws Exception En caso de que falle la modificacion, se lanza una nueva excepcion.
	*/
	
	public void ejecutarPreparedStatement() throws Exception {
		try {
			this.getPreparedStatement().executeUpdate();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "executeUpdate()"));
		} finally {
			this.getPreparedStatement().close();
		}
	}
	
	/**
	* <b><u>M&eacutetodo <code>ejecutarQuery</code>.</u></b>
	* <p>Devuelve el resultado(ResultSet) de ejecutar una consulta a la base de datos. 
	* </p>
	* @param query La consulta a realizar a la base de datos.
	* @return ResultSet Los resultados de la consulta realizada.
	* @throws Exception En caso de que falle la consulta, se lanza una nueva excepcion.
	*/
	
	public ResultSet ejecutarQuery(String query) throws Exception {
		if (this.getConexion() == null)
			return null;
		try {
			this.setStatement(this.getConexion().createStatement());
			this.setResultSet(this.getStatement().executeQuery(query));
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		return this.getResultSet();
	}
	
	/**
	* <b><u>M&eacutetodo <code>cerrarResultSetYStatement</code>.</u></b>
	* <p>Este m&eacutetodo cierra los atributos statement y resultSet de la clase si es que se encuentran inicializados. 
	* </p>
	* @throws Exception En caso de que falle el cierre, se lanza una nueva excepcion.
	*/
	
	public void cerrarResultSetYStatement() throws Exception {
		try {
			if (this.getResultSet() != null)
				this.getResultSet().close();
			if (this.getStatement() != null)
				this.getStatement().close();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "closeCursor"));
		} finally {
			this.setResultSet(null);
			this.setStatement(null);
		}
	}
	
	/**
	* <b><u>M&eacutetodo <code>ejecutarQuery1</code>.</u></b>
	* <p>Devuelve el resultado(ArrayList<Object>) de ejecutar una consulta a la base de datos. 
	* </p>
	* @param query La consulta a realizar a la base de datos.
	* @return ArrayList<Object> El resultado de la consulta.
	* @throws Exception En caso de que falle la consulta, se lanza una nueva excepcion.
	*/
	
	public ArrayList<Object> ejecutarQuery1(String query) throws Exception {
		ArrayList<Object> result, row;
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		int numCols, i;
		if (this.getConexion() == null)
			return null;
		try {
			stmt = this.getConexion().createStatement();
			rs = stmt.executeQuery(query);
			rsmd = rs.getMetaData();
			numCols = rsmd.getColumnCount();
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		result = new ArrayList<Object>();
		try {
			while (rs.next()) {
				row = new ArrayList<Object>();
				for (i = 1; i <= numCols; i++)
					row.add(rs.getString(i));
				result.add(row);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		return result;
	}
	
	/**
	* <b><u>M&eacutetodo <code>querySoloPrimero</code>.</u></b>
	* <p>Este m&eacutetodo realiza la consulta indicada por la query a la base de datos 
	* y devuelve la primera fila del resultSet.
	* </p>
	* @param query La consulta a realizar en la tabla que estamos trabajando.
	* @return String La primera fila de nuestra consulta.
	*/
	
	public String querySoloPrimero(String query) throws Exception {
		ArrayList<Object> l, f;
		int i;
		String s;
		if (this.getConexion() == null)
			return null;
		l = ejecutarQuery1(query);
		if (l.size() < 1)
			return ("");
		f = ((ArrayList<Object>) l.get(0));
		if (f.size() < 1)
			return ("");
		s = "";
		for (i = 0; i < f.size(); i++) {
			if (i > 0)
				s = s + " ";
			s = s + (String) f.get(i);
		}
		return s;
	}
	
	/**
	* <b><u>M&eacutetodo <code>call</code>.</u></b>
	* <p>Este m&eacutetodo realiza la modificacion indicada por la query a la base de datos como si fuese 
	* un bloque indivisible y devuelve el numero de filas de la tabla.
	* </p>
	* @param query La modificacion a realizar en la tabla que estamos trabajando.
	* @return int La cantidad de filas que contiene la tabla luego de ser modificada.
	* @throws Exception En caso de que falle la modificacion, se lanza una nueva excepcion.
	*/
	
	public int call(String query) throws Exception {
		Statement stmt = null;
		int rows = -1;
		if (this.getConexion() == null)
			return 0;
		try {
			stmt = this.getConexion().createStatement();
			rows = stmt.executeUpdate("BEGIN " + query + "END;");
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		};
		return rows;
	}
	
	/**
	* <b><u>M&eacutetodo <code>ejecutarModificacionTabla</code>.</u></b>
	* <p>Este m&eacutetodo realiza la modificacion indicada por la query a la base de datos y devuelve el numero de filas de la tabla. 
	* </p>
	* @param query La modificacion a realizar en la tabla que estamos trabajando.
	* @return int La cantidad de filas que contiene la tabla luego de ser modificada.
	* @throws Exception En caso de que falle la modificacion, se lanza una nueva excepcion.
	*/
	
	public int ejecutarModificacionTabla(String query) throws Exception {
		Statement stmt = null;
		int rows = -1;
		if (this.getConexion() == null)
			return 0;
		try {
			stmt = this.getConexion().createStatement();
			rows = stmt.executeUpdate(query);
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		};
		return rows;
	}
	
	/**
	* <b><u>M&eacutetodo <code>commit</code>.</u></b>
	* <p>Torna permanentes los cambios realizados a la base de datos. 
	* </p>
	*/
	
	public void commit() throws Exception {
		if (this.getConexion() == null)
			return;
		try {
			this.getConexion().commit();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "Commit"));
		}
	}
	
	/**
	* <b><u>M&eacutetodo <code>addV</code>.</u></b>
	* <p>M&eacutetodo para agregar a un vector la informacion contenida dentro de un objeto ListaCallback a partir de un ResultSet
	* </p>
	* @param vector El vector a completar.
	* @param cb Es el objeto ListaCallback del que vamos a sacar la informacion para poder completar el vector a completar.
	* @param rs Es el objeto ListaCallback del que vamos a sacar la informacion para poder completar el vector a completar.
	*/
	
	private void addV(ArrayList<Object> vector, ListaCallback cb, ResultSet rs) throws Exception {
		Object o;
		if (valorDesde == -1 || (valorActual < valorHasta && valorActual >= valorDesde))
			if ((o = cb.listaCallback(rs)) != null)
				vector.add(o);
		valorActual++;
	}
	
	/**
	* <b><u>M&eacutetodo <code>listaObjetos1</code>.</u></b>
	* <p>Este m&eacutetodo realiza la consulta indicada a la base de datos y completa el vector pasado como atributo
	* en base al objeto ListaCallback y el resultado de la query. 
	* </p>
	* @param query La consulta a realizar.
	* @param vector El vector a completar con la informacion obtenida del ResultSet.
	* @param cb El objeto ListaCallback que utilizamos para completar el vector pasado como parametro.
	* @return Boolean Si la consulta realizada devuelve resultados, el metodo devuelve TRUE.
	*/
	
	private boolean listaObjetos1(String query, ArrayList<Object> vector, ListaCallback cb)
			throws Exception {
		ResultSet rs;
		boolean existe = false;
		if (valorDesde >= 0 && valorActual >= valorHasta)
			return true;
		try {
			rs = ejecutarQuery(query);
			if (rs.next()) {
				existe = true;
				addV(vector, cb, rs);
			}
			while (rs.next() && (valorDesde == -1 || valorActual < valorHasta))
				addV(vector, cb, rs);
		} finally {
			this.cerrarResultSetYStatement();
		}
		return existe;
	}
	
	/**
	* <b><u>M&eacutetodo <code>listaObjetos</code>.</u></b>
	* <p>Segun un filtro, orden e indice de fila y cantidad de filas que queremos obtener,
	*  este m&eacutetodo genera la consulta en base a estos parametros y devuelve los resultados de dicha consulta.
	* </p>
	* @param cb El objeto ListaCallback que utilizamos para obtener informacion sobre la tabla que deseamos consultar.
	* @param filtro El filtro de la consulta.
	* @param orden El orden en el que queremos que nos devuelva el resultado.
	* @param ordenInverso Es un boolean que determina si queremos que nuestro resultado este ordenado inversamente.
	* @param desde El numero de fila desde que el queremos obtener informacion.
	* @param cant La cantidad de filas que queremos obtener como resultado.
	* @return ArrayList<Object> Devuelve un vector con los resultados de la consulta
	*/
	
	public ArrayList<Object> listaObjetos(String filtro, String orden, boolean ordenInverso,
			long desde, long cant, ListaCallback cb) throws Exception {
		String orderBy = "";
		String filterBy = "";
		String limitBy = "";
		if (orden != null) {
			orderBy = "ORDER BY " + orden;
			if (ordenInverso)
				orderBy += " DESC";
		}
		if (filtro != null)
			filterBy = " WHERE " + filtro;
		this.valorDesde = -1;
		this.valorHasta = -1;
		this.valorActual = 0;
		if (cant > 0 && desde >= 0)
			limitBy = this.limite(desde, cant);
		return (getResultadoConsultaConFiltroYOrden(cb, filterBy, orderBy, limitBy));
	}
	
	/**
	* <b><u>M&eacutetodo <code>getResultadoConsultaConFiltroYOrden</code>.</u></b>
	* <p>Segun un filtro y orden, este m&eacutetodo genera la consulta en base a estos parametros y devuelve los resultados de dicha consulta.
	* </p>
	* @param cb El objeto ListaCallback que utilizamos para obtener informacion sobre la tabla que deseamos consultar.
	* @param filterBy El filtro de la query.
	* @param orderBy El orden en el que queremos que nos devuelva el resultado
	* @param limitBy El limite en numero de filas de la consulta.
	* @return ArrayList<Object> Devuelve un vector con los resultados de la consulta
	*/
	
	private ArrayList<Object> getResultadoConsultaConFiltroYOrden(ListaCallback cb, String filterBy, String orderBy, String limitBy)
			throws Exception {
		ArrayList<Object> vector = new ArrayList<Object>();
		String s1 = "";
		String s2 = "";
		if(filterBy.length() > 0)
			s1 = " " + filterBy;
		if(orderBy.length() > 0 && limitBy.length() > 0)
			s2 = s1 + " " + orderBy + " " + limitBy;
		try {
			setCount(cb, "SELECT count(1) FROM " + cb.listaTabla() + s1);
			listaObjetos1("SELECT " + cb.listaCampos() + " FROM " + cb.listaTabla() + s2, vector, cb);
		} finally {
			this.cerrarResultSetYStatement();
		}
		if (vector.size() > 0)
			return vector;
		return vector;
	}
	
	/**
	* <b><u>M&eacutetodo <code>setCount</code>.</u></b>
	* <p>A partir de una query y un objeto ListaCallback, este m&eacutetodo setea el contador de ids del objeto ListaCallback. 
	* </p>
	* @param cb El objeto ListaCallback sobre el que vamos a trabajar.
	* @param query La consulta a realizar a la base de datos.
	* @return int Devuelve la id de la fila de la consulta realizada.
	*/
	
	private int setCount(ListaCallback cb, String query) throws Exception {
		ResultSet rs;
		int count = 0;
		if (cb.doCount()) {
			cb.setCount(0);
			try {
				rs = ejecutarQuery(query);
				if (rs.next()) {
					count = rs.getInt(1);
					cb.setCount(count);
				}
			} finally {
				this.cerrarResultSetYStatement();
			}
		}
		return count;
	}
	
	/**
	* <b><u>M&eacutetodo <code>selectObjeto1</code>.</u></b>
	* <p>A partir de una query y un objeto ListaCallback, este m&eacutetodo completa la ListaCallback en base a
	* los resultados obtenidos al ejecutar la query.
	* </p>
	* @param consulta Es la consulta que vamos a hacerle a la base de datos.
	* @param cb Es el objeto ListaCallback que vamos a completar con el resultSet obtenido de la consulta.
	* @return boolean Un booleano que representa si el registro que quisimos obtener existe o no.
	*/
	
	private boolean selectObjeto1(String consulta, ListaCallback cb) throws Exception {
		ResultSet rs;
		boolean existe = false;
		try {
			rs = ejecutarQuery(consulta);
			if (rs.next()) {
				existe = true;
				cb.listaFill(rs);
			}
		} finally {
			this.cerrarResultSetYStatement();
		}
		return existe;
	}
	
	/**
	* <b><u>M&eacutetodo <code>selectObjeto</code>.</u></b>
	* <p>M&eacutetodo para consultar la base de datos y obtener un registro a partir de su id.
	* </p>
	* @param condId Es la id del registro que deseamos consultar.
	* @param cb Es el objeto ListaCallback del que vamos a sacar la informacion para poder consultar la base de datos.
	* @throws Exception en caso que el objeto ListaCallBack no tenga equivalente dentro de la tabla, se lanza una excepcion.
	*/
	
	public void selectObjeto(String condId, ListaCallback cb) throws Exception {
		boolean existe = false;
		String s1, s2;
		s1 = "SELECT " + cb.listaCampos() + " FROM ";
		s2 = " WHERE " + condId;
		existe = selectObjeto1(s1 + cb.listaTabla() + s2, cb);
		if (!existe)
			throw new Exception("El objeto no existe: Tabla::" + cb.listaTabla() + ", Condicion::" + condId);
	}
	
	/**
	* <b><u>M&eacutetodo <code>insert</code>.</u></b>
	* <p>M&eacutetodo para insertar un nuevo registro despues de la ultima ID dada de alta dentro 
	* de la tabla sobre la que estamos trabajando.
	* </p>
	* @param tabla La tabla que estamos modificando.
	* @param pCampos Los campos a modificar.
	* @param pValores Los valores a agregar.
	* @return String la id del ultimo registro agregado.
	* @throws Exception En caso que el atributo tipoDB de la clase no se encuentre seteado o sea incorrecto,
	*  se lanza una excepcion.
	*/
	
	public String insert(String tabla, String pCampos, String pValores) throws Exception {
		int i;
		String valores;
		String id = "";
		boolean auto_id = false;
		if ((i = pValores.indexOf("@ID@")) >= 0) {
			auto_id = true;
			valores = pValores.substring(0, i);
			switch (tipoDB) {
				case MYSQL:
					valores += "null";
					break;
				case ORACLE:
					id = getNextId(tabla);
					valores += id;
					break;
				case ACCESS:
					valores += "";
					break;
				default:
					throw new Exception("Base de Datos desconocida" + tipoDB);
			}
			valores += pValores.substring(i + "@ID@".length() + 1);
		} else
			valores = pValores;
		ejecutarModificacionTabla("INSERT INTO " + tabla + " (" + pCampos + ")" + " VALUES (" + valores + ")");
		if (auto_id) {
			switch (tipoDB) {
				case MYSQL:
					return (getLastId());
				case ACCESS:
					return (getLastId(tabla));
			}
		}
		return id;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>getLastId</code>.</u></b>
	 * <p>Este m&eacutetodo se utiliza para averiguar la id del ultimo registro agregado a una tabla dentro
	 * de una base de datos ORACLE. 
	 * @param tabla La tabla que deseamos consultar.
	 * @return String La ultima id agregada a la tabla sobre la que estamos trabajando.
	 * @throws Exception En caso que la query a la tabla no devuelva resultados, se lanza una excepcion.
	 */
	
	public String getNextId(String tabla) throws Exception {
		String ret;
		try {
			ejecutarQuery("SELECT seq_" + tabla + ".nextval FROM dual");
			if (!this.getResultSet().next())
				throw new Exception("Error obteniendo el ID a insertar");
			ret = this.getResultSet().getString(1);
		} finally {
			this.cerrarResultSetYStatement();
		}
		return ret;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>getLastId</code>.</u></b>
	 * <p>Este m&eacutetodo se utiliza para averiguar la id del ultimo registro agregado a una tabla dentro
	 * de una base de datos SQL. 
	 * @return String La ultima id agregada a la tabla sobre la que estamos trabajando.
	 * @throws Exception En caso que la query a la tabla no devuelva resultados, se lanza una excepcion.
	 */
	
	public String getLastId() throws Exception {
		String ret;
		try {
			ejecutarQuery("SELECT LAST_INSERT_ID()");
			if (!this.getResultSet().next())
				throw new Exception("Error obteniendo el ID insertado");
			ret = this.getResultSet().getString(1);
		} finally {
			this.cerrarResultSetYStatement();
		}
		return ret;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>getLastId</code>.</u></b>
	 * <p>Este m&eacutetodo se utiliza para averiguar la id del ultimo registro agregado a una tabla dentro
	 * de una base de datos SQL.
	 * @param tabla La tabla que deseamos consultar.
	 * @return String La ultima id agregada a la tabla sobre la que estamos trabajando.
	 * @throws Exception En caso que la query a la tabla no devuelva resultados, se lanza una excepcion.
	 */
	
	public String getLastId(String tabla) throws Exception {
		String ret;
		try {
			ejecutarQuery("SELECT MAX(".concat(tabla).concat("_Id) FROM ").concat(tabla));
			if (!resultSet.next())
				throw new Exception("Error obteniendo el ID insertado");
			ret = resultSet.getString(1);
		} finally {
			this.cerrarResultSetYStatement();
		}
		return ret;
	}
	
	/**
	 * <b><u>M&eacutetodo <code>limite</code>.</u></b>
	 * <p>Este m&eacutetodo se utiliza para setear los limites (En numero de filas) relevantes a una consulta.
	 * @param desde Desde que columna queremos comenzar a consultar.
	 * @param cant La cantidad de columnas que deseamos obtener.
	 * @return String En caso que la base de datos sea de tipo SQL, este m&eacutetodo devuelve un string indicando los limites. 
	 * @throws Exception En caso que el tipo de base de datos de la clase no este inicializado o sea incorrecto, se lanza una excepcion.
	 */
	
	private String limite(long desde, long cant) throws Exception {
		switch (tipoDB) {
			case MYSQL:
				return "LIMIT " + desde + "," + cant;
			case ORACLE:
				valorDesde = desde;
				valorHasta = desde + cant;
				valorActual = 0;
				return "";
			case ACCESS:
				valorDesde = desde;
				valorHasta = desde + cant;
				valorActual = 0;
				return "";
			default:
				throw new Exception("Base de Datos desconocida" + tipoDB);
		}
		
	}
	
}