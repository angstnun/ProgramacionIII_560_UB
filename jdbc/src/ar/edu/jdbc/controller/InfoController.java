package ar.edu.jdbc.controller;

public class InfoController {
	
	private int code;
	private String extcode;
	private String message;
	private String desc;
	private Throwable th;
	
	public final static int OK = 0;
	public final static int SESION = 1;
	public final static int PARAM = 2;
	public final static int METODO = 3;
	public final static int ERROR = 4;
	public final static int SISTEMA = 5;
	public final static int AUT = 6;
	
	public InfoController() {
		code = OK;
		extcode = null;
		message = "Accion ejecutada con exito";
		desc = "";
		th = null;
	}
	
	public InfoController(String ec) {
		code = OK;
		extcode = ec;
		message = "Error en la ejecucion";
		desc = "";
		th = null;
	}
	
	public InfoController(int pCode, String pMessage, String pDesc, Throwable t) {
		code = pCode;
		message = pMessage;
		desc = pDesc;
		th = t;
		extcode = null;
	}
	
	public boolean isOk() {
		return (code == OK);
	}
	
	public int getCode() {
		return (code);
	}
	
	public String getMessage() {
		return (message);
	}
	
	public String getDesc() {
		return (desc);
	}
	
	public String getExtCode() {
		return (extcode);
	}
	
	public Throwable getThrowable() {
		return (th);
	}
	
	public String getFullMessage() {
		StackTraceElement[] s;
		String thmsg;
		int i;
		
		if (th != null) {
			thmsg = "\n\t" + th.toString() + "  at  \n";
			s = th.getStackTrace();
			
			for (i = 0; i < s.length; i++) {
				thmsg += "\t" + s[i].getClassName() + "::" + s[i].getMethodName();
				if (!s[i].isNativeMethod())
					thmsg += "  " + s[i].getFileName() + ":" + s[i].getLineNumber() + "\n";
			}
		} else
			thmsg = "";
		
		return (message + ": " + desc + " [" + thmsg + "]");
	}
	
}