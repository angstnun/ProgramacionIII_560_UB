package ar.edu.ub.colchita.controlador;

import ar.edu.ub.colchita.db.FuncionDAO;
import ar.edu.ub.colchita.utilidades.Contexto;
import ar.edu.ub.colchita.utilidades.ContextoClaves;

/**
* <h1>La Colchita - Controlador - Listador</h1>
* Generacion de la lista de las funciones contenidas en la base de datos
*/

public class ListadorControlador {
	
	/**
	  * Método para cargar las funciones de la base de datos, en la lista, ordenadas por su nombre.
	  * @throws Exception e
	  */
	
	public static void ejecutar() {
		try {
			FuncionDAO FuncionDAO = new FuncionDAO();
			Contexto.set(ContextoClaves.PROPIEDADES, FuncionDAO.seleccionarFuncionPorNombre());
		} catch (Exception e) {
			InformadorControlador.mostrarMensaje("FuncionDAO.seleccionarFuncion()" + e.getMessage(), "Error en la creacion del listado", 2);
		}
	}
	
	/**
	  * 
	  * @param from de tipo long
	  * @param cant de tipo long
	  * @param orderOrder de tipo String
	  * @param orderby de tipo String
	  * @throws Exception e
	  */
	
	public static void ejecutar(long from, long cant, String orderOrder, String orderBy) {
		
		boolean inverso = false;
		
		if (from == -1)
			from = 1;
		
		if (cant == -1)
			cant = 10;
		
		if (orderOrder != null) {
			if (orderOrder.compareTo("aba") == 0)
				inverso = true;
			Contexto.set(ContextoClaves.LISTADO_ORDER_ORDER, orderOrder);
		}
		
		if (orderBy != null)
			Contexto.set(ContextoClaves.LISTADO_ORDER_BY, orderBy);
		
		Contexto.set(ContextoClaves.LISTADO_FROM, from);
		Contexto.set(ContextoClaves.LISTADO_CANT, cant);
		
		try {
			FuncionDAO FuncionDAO = new FuncionDAO();
			Contexto.set(ContextoClaves.PROPIEDADES, FuncionDAO.seleccionarFuncion(null, orderBy, inverso,
				from, from + cant));
		} catch (Exception e) {
			InformadorControlador.mostrarMensaje("FuncionDAO.seleccionarFuncion()" + e.getMessage(), "Error en la creacion del listado", 2);
		}
	}
	
	/**
	  * Método para cargar las funciones de la base de datos, segun su id.
	  * @param id de tipo String
	  * @throws Exception e
	  */
	
	public static void ejecutar(String id) {
		if (id == null)
			InformadorControlador.mostrarMensaje("No se indica el solicitante", "Error de parametros", 2);
		try {
			Contexto.set(ContextoClaves.PROPIEDAD, new FuncionDAO(Long.decode(id).longValue()));
		} catch (Exception e) {
			InformadorControlador.mostrarMensaje("FuncionDAO()" + e.getMessage(), "Error en el procesamiento de los datos", 2);
		}
	}
	
}