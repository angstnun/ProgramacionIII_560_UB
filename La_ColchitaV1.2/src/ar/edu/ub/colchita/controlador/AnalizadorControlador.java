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
	
	public NodoRetazo getResultado() {
		return this.getListaNodos().size() == 1 ? (NodoRetazo)this.getListaNodos().get(0) : null;
	}
	
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
	
	private void coserNodos() throws ImposibleEjecutarException{
		
		ArrayList<NodoColchita> listaux = (ArrayList<NodoColchita>) this.getListaNodos().clone();
		
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
	
	private void removerNodosGirar() {
		Iterator<NodoColchita> iter = getListaNodos().iterator();

		while (iter.hasNext()) {
		    NodoColchita n = iter.next();

		    if (n.getClass().equals(NodoGirar.class))
		        iter.remove();
		}
	}
	
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