package tierramedia;

import java.util.ArrayList;

public abstract class Promocion {
	protected String nombre;
	protected ArrayList<Atraccion> atracciones;
	
	public Promocion(String nombreDePromocion, ArrayList<Atraccion> atracciones) {
		super();
		this.nombre = nombreDePromocion;
		this.atracciones = atracciones;
	}
	
	protected String getNombre() {
		return nombre;
	}
	
	//metodo para acceder a distintos tipos de bonus de las promos...
	public abstract void getBonus();
	
	public abstract Object getBonus1();
	
	protected ArrayList<Atraccion> getAtracciones(){
		return atracciones;
	}
	
	public abstract double[] costoPromocion();
	
	public abstract double tiempoPromocion();

}
