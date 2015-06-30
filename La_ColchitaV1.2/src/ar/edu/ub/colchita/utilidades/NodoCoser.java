package ar.edu.ub.colchita.utilidades;

public class NodoCoser extends NodoColchita{
	
	public int giros = 0;
    
    public NodoCoser(NodoColchita n, NodoColchita j) { 
    	this.left = n;
    	this.right = j;
    }
    
    public String toString() {
    	return "NodoCoser";
    }

}
