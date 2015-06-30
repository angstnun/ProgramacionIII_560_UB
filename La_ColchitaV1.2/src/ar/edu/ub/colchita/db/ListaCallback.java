package ar.edu.ub.colchita.db;

import java.sql.ResultSet;

public interface ListaCallback {
	
	Object listaCallback(ResultSet rs) throws Exception;
	void listaFill(ResultSet rs) throws Exception;
	String listaTabla();
	String listaCampos();
	boolean doCount();
	void setCount(long i);
	
}