package ar.edu.ub.colchita.controlador;

import javax.swing.JOptionPane;

import ar.edu.ub.colchita.modelo.RetazoModelo;

/**
* <h1>La Colchita - Controlador - Informador</h1>
* Mensajes de dialogo
*/

public abstract class InformadorControlador {
	
	/**
	 * Mensajes de dialogo según código, manejado por un switch.
	 * @param descripcion de tipo String
	 * @param titulo de tipo String
	 * @paramcodigo de tipo int
	 */
	
	public static void mostrarMensaje(String descripcion, String titulo, int codigo) {
		switch(codigo) {
		case 0:
			JOptionPane.showMessageDialog(null, descripcion, titulo, JOptionPane.PLAIN_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(null, descripcion, titulo, JOptionPane.WARNING_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(null, descripcion, titulo, JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
	
	/**
	  * En caso de excistir un retazo, se muestra el mensaje con su respectivo titulo
	  * @param retazo de tipo Retazo
	  * @param titulo de tipo String
	  */
	
	public static void mostrarMensaje(RetazoModelo retazo, String titulo) {
		if(retazo!=null) {
			JOptionPane.showMessageDialog(null, retazo.toString(), titulo, JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	  * Método generador de mensaje de pregunta con título y descripción.
	  * @param descripcion de tipo String
	  * @param titulo de tipo String
	  * @param codigo de tipo int
	  */
	
	public static String mostrarMensajeInput(Object descripcion, String titulo, int codigo) {
		String valorParaDevolver = null;
		if(codigo == 1)
			return valorParaDevolver = JOptionPane.showInputDialog(null, descripcion, titulo, JOptionPane.QUESTION_MESSAGE);
		if(codigo == 2){
			return ((Integer)JOptionPane.showConfirmDialog(null, descripcion, titulo, JOptionPane.OK_CANCEL_OPTION)).toString();
		}
		return valorParaDevolver;
	}
}
