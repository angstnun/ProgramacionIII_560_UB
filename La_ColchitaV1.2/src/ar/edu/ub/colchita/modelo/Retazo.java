package ar.edu.ub.colchita.modelo;

public class Retazo implements Girable,Cosible {
	
	private int ancho;
	private int alto;
	
	public Retazo(int ancho,int alto) {
		this.setAncho(ancho);
		this.setAlto(alto);
	}
	
	public Retazo(int anchoalto) {
		this(anchoalto, anchoalto);
	}
	
	public int getAncho() {
		return this.ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return this.alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	
	public void girar() {
		int aux=this.getAncho();
		this.setAncho(this.getAlto());
		this.setAlto(aux);
	}
	
	public Retazo coser(Retazo retazo)throws ImposibleCoserException{
		if(!this.equals(retazo)) {
			throw new ImposibleCoserException();
		}
		return new Retazo(this.getAncho()+retazo.getAncho(),this.getAlto());
	}
	
	public String toString() {
		return "Ancho: "+this.getAncho()+", "+"Alto: "+this.getAlto();
	}
	
	public boolean equals(Object objeto) {
		if(objeto==null) {
			return false;
		}
		if(!(objeto instanceof Retazo)) {
			return false;
		}
		if(((Retazo)objeto).getAlto()!=this.getAlto()) {
			return false;
		}
		return true;
	}
}
