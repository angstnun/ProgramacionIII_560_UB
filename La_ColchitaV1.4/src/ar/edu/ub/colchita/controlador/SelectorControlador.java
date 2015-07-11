package ar.edu.ub.colchita.controlador;

import java.io.StringReader;

import javax.swing.JTextArea;

import ar.edu.ub.colchita.modelo.EscritorModelo;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.modelo.javacc.Colchita;
import ar.edu.ub.colchita.modelo.javacc.ParseException;
import ar.edu.ub.colchita.modelo.javacc.TokenMgrError;
import ar.edu.ub.colchita.utilidades.Accion;
import ar.edu.ub.colchita.utilidades.Constantes;
import ar.edu.ub.colchita.utilidades.Contexto;
import ar.edu.ub.colchita.vista.ColchitaFileChooser;
import ar.edu.ub.colchita.vista.ColchitaNuevoRetazoInput;
import ar.edu.ub.colchita.vista.VistaProgramaPrincipal.PanelAreaTrabajo;

/**
* <h1>La Colchita - Controlador - Selector</h1>
* Selección y manejo de retazos
*/

public abstract class SelectorControlador {
	
	/**
	  * Metodo para compilar, ejecutar, guardar, crear retazo y guardar función, administrado por un Switch
	  * @param accion tipo String
	  * @param areaTexto tipo JTextArea
	  * @param panelDibujo tipo PanelAreaTrabajo
	  * @throws ParseException
	  * @throws TokenMgrError
	  */

	public static void seleccionar(String accion,JTextArea areaTexto, PanelAreaTrabajo areaTrabajo) {
		
		switch(accion) {
		case "Compilar":
			if(areaTexto.equals(""))
				InformadorControlador.mostrarMensaje("No hay nada para compilar", "Colchita", 2);
			else{				
				try{
					EjecutorControlador.eliminarArchivo(Constantes.RUTA_ARCHIVOS_FUENTE);
					EscritorModelo.abrir();
					Contexto.setEjecutable(new Colchita(new StringReader(areaTexto.getText())).principal());
					EscritorModelo.cerrar();
					InformadorControlador.mostrarMensaje("Compilacion Correcta.","Compilador",0);
				}
				catch(ParseException ex) {
					EjecutorControlador.eliminarArchivo(Constantes.RUTA_ARCHIVOS_FUENTE);
					InformadorControlador.mostrarMensaje(ex.getMessage(),"Compilador",1);
				}
				catch(TokenMgrError er) {
					EjecutorControlador.eliminarArchivo(Constantes.RUTA_ARCHIVOS_FUENTE);
					InformadorControlador.mostrarMensaje(er.getMessage(),"Compilador",1);
				}
			}
			break;
		case "Ejecutar":
			if(Contexto.getEjecutable() != null) {
				AnalizadorControlador analizador = new AnalizadorControlador((RetazoModelo)Contexto.get("preseleccion1"), (RetazoModelo)Contexto.get("preseleccion2"));
				Contexto.getEjecutable().childrenAccept(analizador, null);
				EjecutorControlador.mostrarResultadoEjecucion(analizador.analizarLista(), areaTrabajo);			
			}
			else InformadorControlador.mostrarMensaje("Debe compilar su codigo antes de ejecutarlo","Compilador",1);
			break;
		case "Guardar":
			new ColchitaFileChooser().guardarArchivo(areaTexto.getText());
			break;
		case "Abrir":
			if(new ColchitaFileChooser().abrirArchivo()) {
				areaTexto.setText("");
				areaTexto.setText((String)Contexto.get("textoConsola"));
			}
			break;
		case "Crear retazo":
			ColchitaNuevoRetazoInput colchitaNuevoRetazoInput = new ColchitaNuevoRetazoInput();
			if(InformadorControlador.mostrarMensajeInput(colchitaNuevoRetazoInput, "Nuevo retazo", 2).equals("0"))	
				Contexto.addRetazo(EjecutorControlador.crearRetazo(colchitaNuevoRetazoInput.getValorAncho(), colchitaNuevoRetazoInput.getValorAlto()));				
			break;
		case "Guardar Funcion":
			String nombreFuncion = InformadorControlador.mostrarMensajeInput("Por favor ingrese el nombre de su funcion: ", "Guardar funcion", 1);
			Boolean resultado = ABMControlador.ejecutar("Funcion", Accion.ALTA, 0, nombreFuncion, areaTexto.getText());
			if(resultado == Boolean.TRUE) 
				InformadorControlador.mostrarMensaje(nombreFuncion + " " + "ha sido guardada.", "Guardar funcion", 2);
			break;
		}
	}
	
}