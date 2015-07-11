package ar.edu.ub.colchita.utilidades.aspectj;

import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;

import ar.edu.ub.colchita.controlador.ControladorPrincipal;
import ar.edu.ub.colchita.utilidades.log4j.ColchitaLog4J;

/**
 * <b><big><u>Aspecto <code>ControladorPrincipalAspect</code>.</u></big></b>
 * <p>
 * Esta clase contiene los distintos puntos de corte de la Colchita, haciendo referencia a paquetes o funciones
 * cuya ejecucion debe ser logueada.
 * <p>
 */

public aspect ControladorPrincipalAspect {

	private pointcut cargaPropiedades() : call(void ControladorPrincipal.cargarPropiedades());
	private pointcut mostrarVista() : call(void ControladorPrincipal.mostrarProgramaPrincipalView());
	private pointcut modeloScope(): within(ar.edu.ub.colchita.modelo..*);
	private pointcut controladorScope(): within(ar.edu.ub.colchita.controlador..*);
	private pointcut javaCCScope(): within(ar.edu.ub.colchita.modelo.javacc..*);
	private pointcut baseDeDatosScope(): within(ar.edu.ub.colchita.db..*);
	private pointcut vistaScope(): within(ar.edu.ub.colchita.vista..*);
	private pointcut derpScope(): within(ar.edu.ub.colchita.utilidades..*);
	
	after(): cargaPropiedades()
	{
		ColchitaLog4J.INFO_LOGGER.info("Las propiedades se cargaron correctamente.");
	}
	
	after(): mostrarVista() {
		ColchitaLog4J.INFO_LOGGER.info("Colchita se ha iniciado correctamente.");
	}
     
    before (Throwable t): handler(Exception+) && args(t) && modeloScope() {
         
        logThrowable(t, thisJoinPointStaticPart, 
                thisEnclosingJoinPointStaticPart);
    }
    
    before (Throwable t): handler(Exception+) &&  args(t) && controladorScope() {
    	
    	logThrowable(t, thisJoinPointStaticPart, 
    			thisEnclosingJoinPointStaticPart);
    }
    
    before (Throwable t): handler(Error+) &&  args(t) && controladorScope() {
    	
    	logThrowable(t, thisJoinPointStaticPart, 
    			thisEnclosingJoinPointStaticPart);
    }
    
    before (Throwable t): handler(Exception+) && args(t) && baseDeDatosScope() {
    	
    	logThrowable(t, thisJoinPointStaticPart, 
    			thisEnclosingJoinPointStaticPart);
    }
    
    before (Throwable t): handler(Exception+) && args(t) && javaCCScope() {
    	
    	logThrowable(t, thisJoinPointStaticPart, 
    			thisEnclosingJoinPointStaticPart);
    }
    
    before (Throwable t): handler(Exception+) && args(t) && vistaScope() {
    	
    	logThrowable(t, thisJoinPointStaticPart, 
    			thisEnclosingJoinPointStaticPart);
    }
    
    /**
	 * <b><u>M&eacutetodo <code>logThrowable</code>.</u></b>
	 * <p>M&eacutetodo para loguear las excepciones, haciendo uso del ERROR_LOGGER de nuestro ColchitaLog4J.
	 * </p>
	 * @param throwable es la excepcion a ser logueada.
	 * @param location es informacion estatica sobre el punto de corte.
	 * @param enclosing es informacion estatica sobre el punto de corte.
	 */
     
    protected synchronized void logThrowable(Throwable throwable, StaticPart lugar,
            StaticPart cierre) {
             
            Signature signature = lugar.getSignature();
             
            String fuenteThrowable = signature.getDeclaringTypeName() + ":" + 
                (cierre.getSourceLocation().getLine());
             
            ColchitaLog4J.ERROR_LOGGER.error("(a) " + fuenteThrowable + " - " + throwable.toString(), throwable);
            
    }
    
}