package tierramedia;

public class Atraccion {

	private String nombre;
	private int costo;
	private double tiempo;
	private int cupo;
	private String tipoDeAtraccion;

	public Atraccion(String nombre, int costo, double tiempo, int cupo, String tipoDeAtraccion) {
		super();
		this.nombre = nombre;
		this.costo = costo;
		this.tiempo = tiempo;
		this.cupo = cupo;
		this.tipoDeAtraccion = tipoDeAtraccion;
	}

	public String getNombre() {
		return nombre;
	}

	public double getTiempo() {
		return tiempo;
	}

	public int getCosto() {
		return costo;
	}

	public String getTipoDeAtraccion() {
		return tipoDeAtraccion;
	}

	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

}
