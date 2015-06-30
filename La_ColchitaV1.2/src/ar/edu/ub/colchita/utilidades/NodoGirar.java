package ar.edu.ub.colchita.utilidades;

public class NodoGirar extends NodoColchita{
    
    public NodoGirar(NodoColchita n) { 
    	this.left = n;
    }
    
    public String toString() {
    	return "NodoGirar";
    }

}
