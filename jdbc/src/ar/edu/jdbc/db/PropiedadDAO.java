package ar.edu.jdbc.db;

import java.sql.ResultSet;
import java.util.ArrayList;

import ar.edu.jdbc.model.PropiedadModel;
import ar.edu.jdbc.util.Fecha;

public class PropiedadDAO extends PropiedadModel implements ListaCallback {
	
	private static String tablaSQL = "";
	private static String camposSQL = "";
	private static String prefSQL = "";
	private static String idSQL = "";
	private static String numeroSQL = "";
	private static String nombreSQL = "";
	private static String borradoSQL = "";
	
	private void registrar() throws Exception {
		tablaSQL = "Propiedad";
		prefSQL = "Propiedad_";
		idSQL = prefSQL + "Id";
		numeroSQL = prefSQL + "Numero";
		nombreSQL = prefSQL + "Nombre";
		borradoSQL = prefSQL + "Borrado";
		camposSQL = idSQL + "," + numeroSQL + "," + nombreSQL + "," + borradoSQL;
	}
	
	public PropiedadDAO(long numero, String nombre) throws Exception {
		super(0, numero, nombre);
		this.registrar();
		this.insertPropiedad();
	}
	
	public PropiedadDAO(long id) throws Exception {
		super();
		this.registrar();
		this.setId(id);
		this.selectPropiedad();
	}
	
	public PropiedadDAO() throws Exception {
		this.registrar();
	}
	
	private void completarObjetoPropiedad(PropiedadModel propiedad, ResultSet resultSet) throws Exception {
		propiedad.setId(resultSet.getString(1));
		propiedad.setNumero(resultSet.getLong(2));
		propiedad.setNombre(resultSet.getString(3));
		propiedad.setBorrado(resultSet.getTimestamp(4));
	}
	
	private void selectPropiedad() throws Exception {
		DbConn db = new DbConn();
		try {
			db.selectObjeto(idSQL + " = " + this.getId(), this);
		} finally {
			db.closeConnection();
		}
	}
	
	public String getCampo(String nombreCampoTabla) {
		if (nombreCampoTabla == null)
			return null;
		if (nombreCampoTabla.compareToIgnoreCase("NUMERO") == 0)
			return numeroSQL;
		if (nombreCampoTabla.compareToIgnoreCase("NOMBRE") == 0)
			return nombreSQL;
		if (nombreCampoTabla.compareToIgnoreCase("BORRADO") == 0)
			return borradoSQL;
		return null;
	}
	
	public void setCount(long l) {
		
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
	
	public void listaFill(ResultSet resultSet) throws Exception {
		this.completarObjetoPropiedad(this, resultSet);
	}
	
	public Object listaCallback(ResultSet resultSet) throws Exception {
		PropiedadModel propiedad = new PropiedadModel();
		this.completarObjetoPropiedad(propiedad, resultSet);
		return propiedad;
	}
	
	public ArrayList<Object> selectALL() throws Exception {
		ArrayList<Object> vector = null;
		DbConn db = new DbConn();
		try {
			vector = db.listaObjetos(null, null, getCampo("NOMBRE"), false, -1, -1, this);
		} finally {
			db.closeConnection();
		}
		return vector;
	}
	
	public ArrayList<Object> selectPropiedades() throws Exception {
		return this.selectPropiedades(null, getCampo("NOMBRE"), false, -1, -1);
	}
	
	public ArrayList<Object> selectPropiedades(String filtro, String orden, boolean ordenInverso, long from,
			long to) throws Exception {
		ArrayList<Object> vector = null;
		DbConn db = new DbConn();
		try {
			vector = db.listaObjetos(borradoSQL, filtro, getCampo(orden), ordenInverso, from, to, this);
		} finally {
			db.closeConnection();
		}
		return vector;
	}
	
	private void insertPropiedad() throws Exception {
		DbConn db = new DbConn();
		try {
			this.setId(db.Insert(tablaSQL, camposSQL.substring(camposSQL.indexOf(',') + 1), "@ID@" + ","
					+ this.getNumero() + ",'" + this.getNombre() + "'," + "null"));
			db.commit();
		} finally {
			db.closeConnection();
		}
	}
	public void insertPropiedad(long numero, String nombre) throws Exception {
		this.setNumero(numero);
		this.setNombre(nombre);
		this.insertPropiedad();
	}
	
	public void deletePropiedad() throws Exception {
		this.setBorrado();
		this.updatePropiedad(borradoSQL + " = " + Fecha.dbFecha(borradoFecha));
	}
	
	public void updatePropiedad() throws Exception {
		this.updatePropiedad(nombreSQL + " = '" + nombre + "' ");
	}
	
	public void updatePropiedad(String upd) throws Exception {
		DbConn db = new DbConn();
		try {
			db.execute("UPDATE " + tablaSQL + " SET " + upd + " WHERE " + idSQL + " = " + this.getId());
			db.commit();
		} finally {
			db.closeConnection();
		}
	}
	
}