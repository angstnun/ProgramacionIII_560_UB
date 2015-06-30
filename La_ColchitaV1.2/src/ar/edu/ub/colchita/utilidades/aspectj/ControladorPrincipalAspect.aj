package ar.edu.ub.colchita.utilidades.aspectj;

import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;

import ar.edu.ub.colchita.utilidades.log4j.ColchitaLog4J;
import ar.edu.ub.colchita.controlador.*;

public aspect ControladorPrincipalAspect {

	private pointcut cargaPropiedades() : call(void ControladorPrincipal.cargarPropiedades());
	private pointcut mostrarVista() : call(void ControladorPrincipal.mostrarProgramaPrincipalView());
	private pointcut modeloScope(): within(ar.edu.ub.colchita.modelo..*);
	private pointcut controladorScope(): within(ar.edu.ub.colchita.controlador..*);
	private pointcut javaCCScope(): within(ar.edu.ub.colchita.modelo.javacc..*);
	private pointcut baseDeDatosScope(): within(ar.edu.ub.colchita.db..*);
	private pointcut vistaScope(): within(ar.edu.ub.colchita.vista..*);
	private pointcut derpScope(): within(ar.edu.ub.colchita.utilidades..*);
	
	after(): cargaPropiedades() {
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
     
    protected synchronized void logThrowable(Throwable t, StaticPart location,
            StaticPart enclosing) {
             
            Signature signature = location.getSignature();
             
            String fuenteThrowable = signature.getDeclaringTypeName() + ":" + 
                (enclosing.getSourceLocation().getLine());
             
            ColchitaLog4J.ERROR_LOGGER.error("(a) " + fuenteThrowable + " - " + t.toString(), t);
            
    }
    
}