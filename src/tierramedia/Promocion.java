package tierramedia;

import java.util.List;

public abstract class Promocion {
	protected String nombre;
	protected List<Atraccion> atracciones;

	public Promocion(String nombrePromocion, List<Atraccion> atraccionesIncluidas) {
		super();
		this.nombre = nombrePromocion;
		this.atracciones = atraccionesIncluidas;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Atraccion> getAtracciones() {
		return atracciones;
	}

	public abstract String ImprimirBonus();

	public abstract double tiempoPromocion();

	public abstract int tipoPromocion();

	public abstract double costoPromocion();

	public void getBonus() {
	}

	public Object getBonus1() {
		return null;
	}

}
