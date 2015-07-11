package ar.edu.ub.colchita.modelo;

import ar.edu.ub.colchita.utilidades.NodoRetazo;

/**
 * <b><big><u>Interface <code>Cosible</code>.</u></big></b>
 * <p>
 * Interface que aplica el metodo <code>coser</code>.
 */
public interface Cosible {
	
	public void coser(NodoRetazo retazo)throws ImposibleCoserException;
}
