package ar.edu.ub.colchita.controlador;

import java.util.ArrayList;
import java.util.Iterator;

import ar.edu.ub.colchita.modelo.ImposibleEjecutarException;
import ar.edu.ub.colchita.modelo.RetazoModelo;
import ar.edu.ub.colchita.modelo.javacc.ASTCoser;
import ar.edu.ub.colchita.modelo.javacc.ASTGirar;
import ar.edu.ub.colchita.modelo.javacc.ASTPrincipal;
import ar.edu.ub.colchita.modelo.javacc.ASTRetazo;
import ar.edu.ub.colchita.modelo.javacc.ColchitaVisitor;
import ar.edu.ub.colchita.modelo.javacc.SimpleNode;
import ar.edu.ub.colchita.utilidades.NodoColchita;
import ar.edu.ub.colchita.utilidades.NodoCoser;
import ar.edu.ub.colchita.utilidades.NodoGirar;
import ar.edu.ub.colchita.utilidades.NodoRetazo;

/**
 * <b><big><u>Clase <code>Analizador</code>.</u></big></b>
 * <p>
 * Esta clase se encarga de generar el arbol sintactico segun un codigo fuente analizado por nuestro compilador. Tambien
 * crea un ArrayList donde se guardan los nodos para que puedan ser analizados y ejecutados.
 */

public class AnalizadorControlador implements ColchitaVisitor{
	
	ArrayList<NodoColchita> listaNodos;
	private RetazoModelo retazo1;
	private RetazoModelo retazo2;
	
	public AnalizadorControlador(RetazoModelo retazo1, RetazoModelo retazo2) {
		this.setRetazo1(retazo1);
		this.setRetazo2(retazo2);
		this.setListaNodos(new ArrayList<NodoColchita>());
	}
	
	private void setListaNodos(ArrayList<NodoColchita> listaNodos) {
		this.listaNodos = listaNodos;
	}

	public ArrayList<NodoColchita> getListaNodos() {
		return listaNodos;
	}

	public RetazoModelo getRetazo1() {
		return retazo1;
	}

	private void setRetazo1(RetazoModelo retazo1) {
		this.retazo1 = retazo1;
	}

	public RetazoModelo getRetazo2() {
		return retazo2;
	}

	private void setRetazo2(RetazoModelo retazo2) {
		this.retazo2 = retazo2;
	}
	
	/**
	 * <b><u>Metodo <code>selectorRetazo</code>.</u></b>
	 * <p>Segun el ASTRetazo visitado, devuelve el RetazoModelo correspondiente.
	 * @return RetazoModelo Devuelve el retazo correspondiente al nodo visitado.
	 */
	
	private RetazoModelo selectorRetazo(ASTRetazo retazoNodo) {
		return retazoNodo.jjtGetValue().toString().toLowerCase().equals("retazo1") ? new RetazoModelo(getRetazo1().getDimension()) :
			new RetazoModelo(getRetazo2().getDimension());
	}

	@Override
	public Object visit(ASTPrincipal node, Object data) {
		data =  node.childrenAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ASTRetazo node, Object data) {
		this.getListaNodos().add(new NodoRetazo(this.selectorRetazo(node)));
		return data;
	}

	@Override
	public Object visit(ASTGirar node, Object data) {
		this.getListaNodos().add(new NodoGirar(null));
		data = node.childrenAccept(this, data);
	    return data;
	}

	@Override
	public Object visit(ASTCoser node, Object data) {
		this.getListaNodos().add(new NodoCoser(null, null));
	    data = node.childrenAccept(this, data);
	    return data;
	}

	@Override
	public Object visit(SimpleNode node, Object data) {
		data = node.childrenAccept(this, data);
		return data;
	}
	
	/**
	 * <b><u>Metodo <code>getResultado</code>.</u></b>
	 * <p>Devuelve el resultado final del analisis.</p>
	 * @return NodoRetazo Devuelve el resultado final del analisis.
	 */
	
	public NodoRetazo getResultado() {
		return this.getListaNodos().size() == 1 ? (NodoRetazo)this.getListaNodos().get(0) : null;
	}
	
	/**
	 * <b><u>Metodo <code>analizarLista</code>.</u></b>
	 * <p>Analiza la lista. La manera en la que funciona este metodo es a partir del enlace de los nodos del arbol sintactico
	 * y su analisis, ejecutando los giros y costuras, para luego eliminar los nodos que ya han sido procesados.</p>
	 * @return NodoRetazo Devuelve el resultado final del analisis.
	 */
	
	public NodoRetazo analizarLista() {
		if(this.getListaNodos().size() > 1) {
			this.enlazarNodos();
			this.establecerGiros();
			this.removerNodosGirar();
			try {
				this.coserNodos();
				return this.getResultado();
			} catch (ImposibleEjecutarException e) {
				this.getListaNodos().clear();
				InformadorControlador.mostrarMensaje(e.getMessage(), "Error al ejecutar", 2);
				return null;
			}						
		}
		return this.getResultado();
	}
	
	/**
	 * <b><u>Metodo <code>enlazarNodos</code>.</u></b>
	 * <p>Relaciona los nodos Girar y Coser con sus respectivos nodos hijos.
	 * </p>
	 */
	
	private void enlazarNodos() {
		if(getListaNodos().size()>0){
			for(int i=0;i<getListaNodos().size();i++){
				if(getListaNodos().get(i) instanceof NodoCoser){
					((NodoCoser)getListaNodos().get(i)).left = getListaNodos().get(i+1);
					((NodoCoser)getListaNodos().get(i)).right = getListaNodos().get(i+2);
				}
				if(getListaNodos().get(i) instanceof NodoGirar){
					((NodoGirar)getListaNodos().get(i)).left = getListaNodos().get(i+1);
				}
			}		
		}
	}
	
	/**
	 * <b><u>Metodo <code>coserNodos</code>.</u></b>
	 * <p>Realiza las costuras y guarda el resultado en el nodo hijo izquierdo del nodoCoser actual, luego elimina
	 * al nodo hijo derecho y al padre.
	 */
	
	private void coserNodos() throws ImposibleEjecutarException{
		
		Object clone = this.getListaNodos().clone();
		ArrayList<NodoColchita> listaux = (ArrayList<NodoColchita>) clone;
		
		this.enlazarNodos();
		for (NodoColchita n : listaux){
			if(n instanceof NodoCoser){
				if(n.left instanceof NodoRetazo && n.right instanceof NodoRetazo) {
					Boolean b = EjecutorControlador.coserRetazo((NodoRetazo)n.left, (NodoRetazo)n.right);
					if(!b)throw new ImposibleEjecutarException();
					if(((NodoCoser)n).giros > 0)
						EjecutorControlador.girarRetazo((NodoRetazo)n.left, ((NodoCoser)n).giros);
					this.getListaNodos().remove(n.right);
					this.getListaNodos().remove(n);			
				}
			}
		}
		if(this.getListaNodos().size() > 1)
			this.coserNodos();
	}
	
	/**
	 * <b><u>Metodo <code>establecerGiros</code>.</u></b>
	 * <p>
	 * Establece y ejecuta todos los nodosGiros del arbol sintactico.
	 * 
	 */
	
	private void establecerGiros() {
		
		Integer giros = 0;
		
		if(getListaNodos().size() > 0) {
			for(NodoColchita n : this.getListaNodos()) {
				if(n.getClass().equals(NodoCoser.class)){
					if(giros>0){
						((NodoCoser)n).giros = giros;
						giros = 0;
					}
				}
				if(n.getClass().equals(NodoGirar.class)){
					giros++;
				}
				if(n.getClass().equals(NodoRetazo.class)){
					if(giros>0){
						EjecutorControlador.girarRetazo((NodoRetazo)n, giros);
						giros = 0;
					}
				}
			}
		}
	}
	
	/**
	 * <b><u>Metodo <code>removerNodosGirar</code>.</u></b>
	 * <p>Remueve todos los nodosGiros del arbol sintactico.
	 */
	
	private void removerNodosGirar() {
		Iterator<NodoColchita> iter = getListaNodos().iterator();

		while (iter.hasNext()) {
		    NodoColchita n = iter.next();

		    if (n.getClass().equals(NodoGirar.class))
		        iter.remove();
		}
	}
	
	/**
	 * <b><u>Metodo <code>imprimirListaNodos</code>.</u></b>
	 * <p>Imprime en consola el contenido del atributo listaNodos.
	 */
	
	public void imprimirListaNodos() {
		if(getListaNodos().size() > 0){
			Iterator<NodoColchita> iter = getListaNodos().iterator();
			while(iter.hasNext()){
				NodoColchita n = iter.next();
				System.out.println(n.toString());
			}
		}
	}
}