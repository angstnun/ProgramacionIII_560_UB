package ar.edu.jdbc.controller;

import ar.edu.jdbc.db.PropiedadDAO;
import ar.edu.jdbc.util.Accion;

public class ABMController {
	
	private ABMController() {
		
	}
	
	public static InfoController execute(String tipo, Accion accion, long id, long numero, String nombre) {
		
		if (tipo == null)
			return (badpar("tipo"));
		
		if (accion == null)
			return (badpar("accion"));
		
		if (accion != Accion.ALTA && accion != Accion.BAJA && accion != Accion.MODIFICACION)
			return (new InfoController(InfoController.PARAM, "Tipo de accion invalida",
				"No es { A | B | M }", null));
		
		if (accion == Accion.ALTA || accion == Accion.MODIFICACION) {
			if (nombre == null || nombre.length() < 1) {
				return (new InfoController(InfoController.PARAM, "No se indica el objeto "
						+ "sobre el cual operar", "Falta parametro nombre", null));
			}
		}
		
		if (accion == Accion.BAJA || accion == Accion.MODIFICACION) {
			if (id == -1) {
				return (new InfoController(InfoController.PARAM, "No se indica el objeto "
						+ "sobre el cual operar", "Falta parametro id", null));
			}
		}
		
		try {
			if (tipo.compareTo("Propiedad") == 0) {
				PropiedadDAO propiedadDAO;
				
				if (accion == Accion.ALTA) {
					new PropiedadDAO(numero, nombre);
				} else if (accion == Accion.MODIFICACION) {
					propiedadDAO = new PropiedadDAO(id);
					propiedadDAO.setNombre(nombre);
					propiedadDAO.updatePropiedad();
				} else if (accion == Accion.BAJA) {
					propiedadDAO = new PropiedadDAO(id);
					propiedadDAO.deletePropiedad();
				}
			} else {
				return (new InfoController(InfoController.PARAM, "Tipo de abm invalido",
					"No es un atributo valido", null));
			}
		} catch (Exception e) {
			return (new InfoController(InfoController.METODO, "Error en la ejecucion de la accion", "Tipo: "
					+ tipo + ", Accion: " + accion + ", Nombre: " + nombre + ", Id: " + id, e));
		}
		
		return (new InfoController(InfoController.OK, "Accion ejecutada con exito", "", null));
		
	}
	
	private static InfoController badpar(String parname) {
		return (new InfoController(InfoController.PARAM, "No se indica " + parname, "Falta parametro "
				+ parname, null));
	}
	
}