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
	
	private void setConexion() {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Files.copy(BuscadorRutas.getRecursoComoInputStream(this.fuenteDB), new File(Constantes.RUTA_TEMP_DB).toPath(), StandardCopyOption.REPLACE_EXISTING);
			this.conexion = DriverManager.getConnection(
					"jdbc:ucanaccess://" + Constantes.RUTA_TEMP_DB , "", "");
		} catch (SQLException e) {
			InformadorControlador.mostrarMensaje("Error con el driver JDBC", "ConexionDB", 1);
		} catch (IOException e) {
			InformadorControlador.mostrarMensaje("Imposible crear la base de datos temporal", "ConexionDB", 1);
		} catch (ClassNotFoundException e) {
			InformadorControlador.mostrarMensaje("Error con el driver ucanaccess", "ConexionDB", 1);
		}
		this.hayAlertasConexion();
	}
	
	public void setAutoCommit(Boolean b) {
		try {
			this.getConexion().setAutoCommit(b);
		} catch (SQLException e) {
			InformadorControlador.mostrarMensaje(e.getMessage(), "ConexionDB", 1);
		}	
	}
	
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
		return this.conexion;
	}
	
	public void cerrarConexion() {
		try {
			this.getConexion().close();
		} catch (Exception e) {
			InformadorControlador.mostrarMensaje("Error al cerrar la conexion : " + e.getMessage(), "ConexionDB", 1);
		};
	}
	
	public String advertenciasDB(SQLWarning w) {
		String txt = "";
		while (w != null) {
			if (txt.length() > 0)
				txt = "\n" + txt;
			txt = txt + w.getMessage();
			w = w.getNextWarning();
		}
		return txt;
	}
	
	public String errorDB(Exception ex, String query) {
		String txt = "";
		if (query != null)
			txt = "Action: " + query + "\n";
		StackTraceElement[] s;
		String thmsg = "(null)";
		int i;
		if (ex != null) {
			thmsg = "\n" + ex.toString() + " at \n";
			s = ex.getStackTrace();
			for (i = 0; i < s.length; i++) {
				thmsg += "\t" + s[i].getClassName() + "::" + s[i].getMethodName();
				if (!s[i].isNativeMethod())
					thmsg += " at " + s[i].getFileName() + ":" + s[i].getLineNumber();
				thmsg += "\n";
			}
		}
		txt = txt + thmsg;
		return txt;
	}
	
	public int colsCursor() {
		if (resultSet == null)
			return 0;
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public PreparedStatement openStatement(String s) throws Exception {
		if (conexion == null)
			return null;
		try {
			preparedStatement = conexion.prepareStatement(s);
		} catch (Exception e) {
			throw new Exception(errorDB(e, s));
		}
		return preparedStatement;
	}
	
	public void execStatement() throws Exception {
		try {
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "executeUpdate()"));
		} finally {
			preparedStatement.close();
		}
	}
	
	public ResultSet openCursor(String query) throws Exception {
		if (conexion == null)
			return null;
		try {
			statement = conexion.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			throw new Exception(errorDB(e, query));
		}
		return resultSet;
	}
	
	public void closeCursor() throws Exception {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "closeCursor"));
		} finally {
			resultSet = null;
			statement = null;
		}
	}
	
	public ArrayList<Object> Query(String query) throws Exception {
		ArrayList<Object> result, row;
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		int numCols, i;
		if (conexion == null)
			return null;
		try {
			stmt = conexion.createStatement();
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
	
	public String QuerySoloPrimero(String query) throws Exception {
		ArrayList<Object> l, f;
		int i;
		String s;
		if (conexion == null)
			return null;
		l = Query(query);
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
	
	public int Call(String query) throws Exception {
		Statement stmt = null;
		int rows = -1;
		if (conexion == null)
			return 0;
		try {
			stmt = conexion.createStatement();
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
	
	public int execute(String query) throws Exception {
		Statement stmt = null;
		int rows = -1;
		if (conexion == null)
			return 0;
		try {
			stmt = conexion.createStatement();
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
	
	public void commit() throws Exception {
		if (conexion == null)
			return;
		try {
			conexion.commit();
		} catch (Exception e) {
			throw new Exception(errorDB(e, "Commit"));
		}
	}
	
	private void addV(ArrayList<Object> vector, ListaCallback cb, ResultSet rs) throws Exception {
		Object o;
		if (valorDesde == -1 || (valorActual < valorHasta && valorActual >= valorDesde))
			if ((o = cb.listaCallback(rs)) != null)
				vector.add(o);
		valorActual++;
	}
	
	private boolean listaObjetos1(String consulta, ArrayList<Object> vector, ListaCallback cb)
			throws Exception {
		ResultSet rs;
		boolean existe = false;
		if (valorDesde >= 0 && valorActual >= valorHasta)
			return true;
		try {
			rs = openCursor(consulta);
			if (rs.next()) {
				existe = true;
				addV(vector, cb, rs);
			}
			while (rs.next() && (valorDesde == -1 || valorActual < valorHasta))
				addV(vector, cb, rs);
		} finally {
			closeCursor();
		}
		return existe;
	}
	
	public ArrayList<Object> listaObjetosUnion(String filtro, String orden,
			boolean ordenInverso, long desde, long cant, ListaCallback cb) throws Exception {
		return listaObjetos(filtro, orden, ordenInverso, desde, cant, cb);
	}
	
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
			limitBy = limite(desde, cant);
		return (hazPrimero(cb, filterBy, orderBy, limitBy));
	}
	
	private ArrayList<Object> hazPrimero(ListaCallback cb, String filterBy, String orderBy, String limitBy)
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
			closeCursor();
		}
		if (vector.size() > 0)
			return vector;
		return vector;
	}
	
	private int setCount(ListaCallback cb, String consulta) throws Exception {
		ResultSet rs;
		int c = 0;
		if (cb.doCount()) {
			cb.setCount(0);
			try {
				rs = openCursor(consulta);
				if (rs.next()) {
					c = rs.getInt(1);
					cb.setCount(c);
				}
			} finally {
				closeCursor();
			}
		}
		return c;
	}
	
	private boolean selectObjeto1(String consulta, ListaCallback cb) throws Exception {
		ResultSet rs;
		boolean existe = false;
		try {
			rs = openCursor(consulta);
			if (rs.next()) {
				existe = true;
				cb.listaFill(rs);
			}
		} finally {
			closeCursor();
		}
		return existe;
	}
	
	/**
	 * A partir de un id y un objeto ListaCallback 
	 * @param condId
	 * @param cb
	 * @throws Exception
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
	 * <b><u>Metodo <code>insert</code>.</u></b>
	 * <p>
	 * METODO DE LAST ID PARA ACCESS
	 */
	public String insert(String tabla, String pCampos, String pValues) throws Exception {
		int i;
		String values;
		String id = "";
		boolean auto_id = false;
		if ((i = pValues.indexOf("@ID@")) >= 0) {
			auto_id = true;
			values = pValues.substring(0, i);
			switch (tipoDB) {
				case MYSQL:
					values += "null";
					break;
				case ORACLE:
					id = getNextId(tabla);
					values += id;
					break;
				case ACCESS:
					values += "";
					break;
				default:
					throw new Exception("Base de Datos desconocida" + tipoDB);
			}
			values += pValues.substring(i + "@ID@".length() + 1);
		} else
			values = pValues;
		execute("INSERT INTO " + tabla + " (" + pCampos + ")" + " VALUES (" + values + ")");
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
	 * <b><u>Metodo <code>getLastId</code>.</u></b>
	 * <p>
	 * METODO DE LAST ID PARA ORACLE
	 */
	public String getNextId(String tabla) throws Exception {
		String ret;
		try {
			openCursor("SELECT seq_" + tabla + ".nextval FROM dual");
			if (!resultSet.next())
				throw new Exception("Error obteniendo el ID a insertar");
			ret = resultSet.getString(1);
		} finally {
			closeCursor();
		}
		return ret;
	}
	
	/**
	 * <b><u>Metodo <code>getLastId</code>.</u></b>
	 * <p>
	 * METODO DE LAST ID PARA SQL
	 */
	public String getLastId() throws Exception {
		String ret;
		try {
			openCursor("SELECT LAST_INSERT_ID()");
			if (!resultSet.next())
				throw new Exception("Error obteniendo el ID insertado");
			ret = resultSet.getString(1);
		} finally {
			closeCursor();
		}
		return ret;
	}
	
	/**
	 * <b><u>Metodo <code>getLastId</code>.</u></b>
	 * <p>
	 * METODO DE LAST ID PARA ACCESS
	 */
	public String getLastId(String tabla) throws Exception {
		String ret;
		try {
			openCursor("SELECT MAX(".concat(tabla).concat("_Id) FROM ").concat(tabla));
			if (!resultSet.next())
				throw new Exception("Error obteniendo el ID insertado");
			ret = resultSet.getString(1);
		} finally {
			closeCursor();
		}
		return ret;
	}
	
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