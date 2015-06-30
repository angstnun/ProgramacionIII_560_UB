package ar.edu.ub.colchita.modelo;

public class ImposibleEjecutarException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public ImposibleEjecutarException(){
		super("No se puede ejecutar debido a errores en su codigo");
	}

}
