package ar.edu.jdbc.controller;

import ar.edu.jdbc.db.PropiedadDAO;
import ar.edu.jdbc.util.Contexto;
import ar.edu.jdbc.util.ContextoClaves;

public class ListadoController {
	
	private ListadoController() {
		
	}
	
	public static InfoController execute() {
		try {
			PropiedadDAO propiedadDAO = new PropiedadDAO();
			Contexto.set(ContextoClaves.PROPIEDADES, propiedadDAO.selectPropiedades());
		} catch (Exception e) {
			return (new InfoController(InfoController.METODO, "Error en la creacion del listado",
				"PopiedadDAO.selectPropiedades()", e));
		}
		
		return new InfoController();
		
	}
	
	public static InfoController execute(long from, long cant, String orderOrder, String orderBy) {
		
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
			PropiedadDAO propiedadDAO = new PropiedadDAO();
			Contexto.set(ContextoClaves.PROPIEDADES, propiedadDAO.selectPropiedades(null, orderBy, inverso,
				from, from + cant));
		} catch (Exception e) {
			return (new InfoController(InfoController.METODO, "Error en la creacion del listado",
				"PopiedadDAO.selectPropiedades()", e));
		}
		
		return new InfoController();
		
	}
	
	public static InfoController execute(String id) {
		
		if (id == null)
			return (new InfoController(InfoController.PARAM, "Error de parametros",
				"No se indica el solicitante", null));
		
		try {
			Contexto.set(ContextoClaves.PROPIEDAD, new PropiedadDAO(Long.decode(id).longValue()));
		} catch (Exception e) {
			return (new InfoController(InfoController.METODO, "Error en el procesamiento de los datos",
				"PropiedadDAO()", e));
		}
		
		return new InfoController();
		
	}
	
}