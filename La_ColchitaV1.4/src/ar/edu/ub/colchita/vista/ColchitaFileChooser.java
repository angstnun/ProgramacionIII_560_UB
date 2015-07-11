package ar.edu.ub.colchita.vista;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import ar.edu.ub.colchita.controlador.InformadorControlador;
import ar.edu.ub.colchita.modelo.EscritorModelo;
import ar.edu.ub.colchita.modelo.LectorModelo;
import ar.edu.ub.colchita.utilidades.Contexto;

/**
* <h1>La Colchita - Vista - ColchitaFileChooser</h1>
* Ventana que nos permite explorar el filesystem.
*/

public class ColchitaFileChooser extends JFileChooser{
	
	private static final long serialVersionUID = 1L;
	
	public ColchitaFileChooser() {
		this.setDialogTitle("Guardar");
	}
	
	/**
	 * <b><u>Metodo <code>guardarArchivo</code>.</u></b>
	 * <p>
	 * Guarda el objeto pasado como parametro dentro del filesystem con extension .colchita
	 * @param obj El objeto a guardar.
	 */
	
	public Boolean guardarArchivo(Object obj) {
		if(obj == null) {
			InformadorControlador.mostrarMensaje("El contenido a guardar es null", "Error al guardar", 1);
			return Boolean.FALSE;
		}
		Integer valorRetorno = this.showSaveDialog(null);
		if (valorRetorno == JFileChooser.APPROVE_OPTION) {
			File file = this.getSelectedFile();
			if(file.exists()) {
				InformadorControlador.mostrarMensaje("El archivo ya existe", "Problema al guardar", 1);
				return Boolean.FALSE;
			}
			String ruta = file.getAbsolutePath();
			if(!ruta.endsWith(".colchita") ) {
				ruta = ruta + ".colchita";
			}
			EscritorModelo.abrir(ruta);
			if(obj instanceof String)
				EscritorModelo.escribir((String)obj);
			EscritorModelo.cerrar();
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
    }
	
	/**
	 * <b><u>Metodo <code>abrirArchivo</code>.</u></b>
	 * <p>
	 * Abre un archivo dentro del fileSystem mientras su extension sea .colchita y lo lee.
	 */

	public boolean abrirArchivo() {
		Integer valorRetorno = this.showOpenDialog(null);
		StringBuilder stringBuilder = new StringBuilder();;
		if (valorRetorno == JFileChooser.APPROVE_OPTION) {
			File file = this.getSelectedFile();
			String ruta = file.getAbsolutePath();
			if(!ruta.endsWith(".colchita")) {
				InformadorControlador.mostrarMensaje("Error al abrir el archivo", "Error al abrir", 1);
				return Boolean.FALSE;
			}
			ArrayList<String> stringLeido = new LectorModelo(ruta).leer();
			for(String string : stringLeido) stringBuilder.append(string +"\r\n ");
			Contexto.set("textoConsola", stringBuilder.toString());
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}