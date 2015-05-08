package ar.edu.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import ar.edu.jdbc.util.Constantes;

public class DbConn {
	
	private Connection connection;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String dataSourceName = Constantes.DATA_SOURCE_NAME;
	private int dbType = Constantes.DBTYPE;
	private long valorDesde = -1;
	private long valorHasta = -1;
	private long valorActual = 0;
	
	private final int ORACLE = 0x0001;
	private final int MYSQL = 0x0002;
	private final int ACCESS = 0x0003;
	
	public DbConn() throws Exception {
		SQLWarning warn;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new Exception("No encuentra la clase del Driver: " + e);
		}
		try {
			// NOTE: Variante por ODBC
			// connection =
			// DriverManager.getConnection("jdbc:odbc:".concat(dataSourceName),
			// "", "");
			
			// NOTE: Variante por path
			connection = DriverManager.getConnection(
				"jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=".concat(Constantes.URL_DATA_BASE),
				"", "");
		} catch (Exception e) {
			throw new Exception("No se pudo establecer la conexion: ".concat(dataSourceName).concat(" : ")
				.concat(e.getMessage()));
		}
		warn = connection.getWarnings();
		if (warn != null)
			throw new Exception(this.dbWarning(warn));
		this.getConnection().setAutoCommit(false);
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			this.getConnection().close();
		} catch (Exception e) {
		};
	}
	
	public String dbWarning(SQLWarning w) {
		String txt = "";
		while (w != null) {
			if (txt.length() > 0)
				txt = "\n" + txt;
			txt = txt + w.getMessage();
			w = w.getNextWarning();
		}
		return txt;
	}
	
	public String dbError(Exception ex, String query) {
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
		if (connection == null)
			return null;
		try {
			preparedStatement = connection.prepareStatement(s);
		} catch (Exception e) {
			throw new Exception(dbError(e, s));
		}
		return preparedStatement;
	}
	
	public void execStatement() throws Exception {
		try {
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new Exception(dbError(e, "executeUpdate()"));
		} finally {
			preparedStatement.close();
		}
	}
	
	public ResultSet openCursor(String query) throws Exception {
		if (connection == null)
			return null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			throw new Exception(dbError(e, query));
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
			throw new Exception(dbError(e, "closeCursor"));
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
		if (connection == null)
			return null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			rsmd = rs.getMetaData();
			numCols = rsmd.getColumnCount();
		} catch (Exception e) {
			throw new Exception(dbError(e, query));
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
			throw new Exception(dbError(e, query));
		}
		return result;
	}
	
	public String Query1(String query) throws Exception {
		ArrayList<Object> l, f;
		int i;
		String s;
		if (connection == null)
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
		if (connection == null)
			return 0;
		try {
			stmt = connection.createStatement();
			rows = stmt.executeUpdate("BEGIN " + query + "END;");
		} catch (Exception e) {
			throw new Exception(dbError(e, query));
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
		if (connection == null)
			return 0;
		try {
			stmt = connection.createStatement();
			rows = stmt.executeUpdate(query);
		} catch (Exception e) {
			throw new Exception(dbError(e, query));
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		};
		return rows;
	}
	
	public void commit() throws Exception {
		if (connection == null)
			return;
		try {
			connection.commit();
		} catch (Exception e) {
			throw new Exception(dbError(e, "Commit"));
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
	
	public ArrayList<Object> listaObjetosUnion(String borrado, String filtro, String orden,
			boolean ordenInverso, long desde, long cant, ListaCallback cb) throws Exception {
		return listaObjetos(borrado, filtro, orden, ordenInverso, desde, cant, cb);
	}
	
	public ArrayList<Object> listaObjetos(String borrado, String filtro, String orden, boolean ordenInverso,
			long desde, long cant, ListaCallback cb) throws Exception {
		String orderBy = "";
		String filterBy = "";
		String limitBy = "";
		if (orden != null) {
			if (orden != null) {
				orderBy = "ORDER BY " + orden;
				if (ordenInverso)
					orderBy += " DESC";
			}
		}
		if (borrado != null || filtro != null)
			filterBy = " WHERE ";
		if (borrado != null)
			filterBy = filterBy + borrado + " is null ";
		if (filtro != null) {
			if (borrado != null)
				filterBy = filterBy + " AND (" + filtro + ")";
			else
				filterBy = filterBy + filtro;
		}
		valorDesde = -1;
		valorHasta = -1;
		valorActual = 0;
		if (cant > 0 && desde >= 0)
			limitBy = limite(desde, cant);
		return (doFirst(cb, filterBy, orderBy, limitBy));
	}
	
	private ArrayList<Object> doFirst(ListaCallback cb, String filterBy, String orderBy, String limitBy)
			throws Exception {
		ArrayList<Object> vector = new ArrayList<Object>();
		String s1, s2;
		s1 = " " + filterBy;
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
	
	public void selectObjeto(String condId, ListaCallback cb) throws Exception {
		boolean existe = false;
		String s1, s2;
		s1 = "SELECT " + cb.listaCampos() + " FROM ";
		s2 = " WHERE " + condId;
		existe = selectObjeto1(s1 + cb.listaTabla() + s2, cb);
		if (!existe)
			throw new Exception("El objeto no existe: Tabla::" + cb.listaTabla() + ", Condicion::" + condId);
	}
	
	public String Insert(String tabla, String pCampos, String pValues) throws Exception {
		int i;
		String values;
		String id = "";
		boolean auto_id = false;
		if ((i = pValues.indexOf("@ID@")) >= 0) {
			auto_id = true;
			values = pValues.substring(0, i);
			switch (dbType) {
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
					throw new Exception("Base de Datos desconocida" + dbType);
			}
			values += pValues.substring(i + "@ID@".length() + 1);
		} else
			values = pValues;
		execute("INSERT INTO " + tabla + " (" + pCampos + ")" + " VALUES (" + values + ")");
		if (auto_id) {
			switch (dbType) {
				case MYSQL:
					return (getLastId());
				case ACCESS:
					return (getLastId(tabla));
			}
		}
		return id;
	}
	
	/*** Oracle ***/
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
	
	/*** MySQL ***/
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
	
	/*** ACCESS ***/
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
		switch (dbType) {
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
				throw new Exception("Base de Datos desconocida" + dbType);
		}
		
	}
	
}