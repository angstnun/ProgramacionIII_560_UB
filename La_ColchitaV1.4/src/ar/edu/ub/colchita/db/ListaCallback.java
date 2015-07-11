package ar.edu.ub.colchita.db;

import java.sql.ResultSet;

/**
 * <b><big><u>Interface <code>ListaCallback</code>.</u></big></b>
 * <p>
 * Interface que aplica metodos utilizados repetidamente en las operaciones ABM a una base de datos, generalmente pasados
 * como parametros de otros metodos o dentro de estos.
 */

public interface ListaCallback {
	
	Object listaCallback(ResultSet rs) throws Exception;
	void listaFill(ResultSet rs) throws Exception;
	String listaTabla();
	String listaCampos();
	boolean doCount();
	void setCount(long i);
	
}