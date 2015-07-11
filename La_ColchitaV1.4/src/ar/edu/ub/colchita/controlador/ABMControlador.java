package ar.edu.ub.colchita.controlador;

import ar.edu.ub.colchita.db.FuncionDAO;
import ar.edu.ub.colchita.utilidades.Accion;

public class ABMControlador {
	
	/**
	  * Operaciones programadas de ejecución en casos de error, por falta de nombre, descripcion o id. 
	  * Dar de alta una función nueva, modificar una excistente o borrar una función.
	  * @param tipo de tipo String
	  * @param accion de tipo Accion
	  * @param id de tipo Long
	  * @param nombre de tipo String
	  * @param descripcion de tipo String
	  * @throws Exception e
	  */
	
	public static Boolean ejecutar(String tipo, Accion accion, long id, String nombre, String descripcion) {
		if (tipo == null)
			return parametroErroneo("tipo");
		else if (accion == null)
			return parametroErroneo("accion");
		else {
			if (accion != Accion.ALTA && accion != Accion.BAJA && accion != Accion.MODIFICACION){
				InformadorControlador.mostrarMensaje("No es { A | B | M }", "Tipo de accion invalida", 2);
				return Boolean.FALSE;
			}
			if (accion == Accion.ALTA || accion == Accion.MODIFICACION) {
				if (nombre == null || nombre.length() < 1){
					InformadorControlador.mostrarMensaje("No se indica el objeto sobre el cual operar",
							"Falta parametro nombre", 2);
					return Boolean.FALSE;
				}
				if(descripcion == null || descripcion.length() < 1) {
					InformadorControlador.mostrarMensaje("No se indica el objeto sobre el cual operar",
							"Falta parametro descripcion", 2);
					return Boolean.FALSE;
				}
			}
			if (accion == Accion.BAJA || accion == Accion.MODIFICACION) {
				if (id == -1) {
					InformadorControlador.mostrarMensaje("No se indica el objeto sobre el cual operar",
							"Falta parametro id", 2);
					return Boolean.FALSE;
				}
			}
			try {
				if (tipo.equals("Funcion")) {
					FuncionDAO funcionDAO;
					if (accion == Accion.ALTA) {
						new FuncionDAO(nombre, descripcion);
					} else if (accion == Accion.MODIFICACION) {
						funcionDAO = new FuncionDAO(id);
						funcionDAO.setNombre(nombre);
						funcionDAO.modificarFuncion();
					} else if (accion == Accion.BAJA) {
						funcionDAO = new FuncionDAO(id);
						funcionDAO.bajaFuncion();
					}
				} 
				else {
					InformadorControlador.mostrarMensaje("No es un atributo valido",
							"Tipo de abm invalido", 2);
					return  Boolean.FALSE;
				}
			} 
			catch (Exception e) {
				InformadorControlador.mostrarMensaje("Tipo: "
						+ tipo + ", Accion: " + accion + ", Nombre: " + nombre + ", Id: " + id + " " +
						"Error: " + e.getMessage(),
						"Error en la ejecucion de la accion", 2);
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
	}
	
	/**
	  * En caso de parametro erroneo, se mostrará mensaje.
	  * @param parametro de tipo String
	  */
	
	private static Boolean parametroErroneo(String parametro) {
		InformadorControlador.mostrarMensaje("No se indica " + parametro,"Falta parametro "
				+ parametro, 2);
		return Boolean.FALSE;
	}
	
}