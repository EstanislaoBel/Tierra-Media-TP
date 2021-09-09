package tierramedia;

import java.util.ArrayList;

public class Porcentual extends Promocion {
	private double descuento;

	public Porcentual(String nombrePromocion, ArrayList<Atraccion> atraccionesIncluidas, double descuento) {
		super(nombrePromocion, atraccionesIncluidas);
		this.descuento = descuento;
	}

	protected double getDescuento() {
		return descuento;
	}

	@Override
	public void getBonus() {
		System.out.println(descuento);

	}

	@Override
	public Object getBonus1() {
		return (double) descuento;
	}

	@Override
	public double[] costoPromocion() {
		double costos[] = new double[2];

		for (Atraccion atraccion : this.atracciones) {
			costos[0] += atraccion.getCosto();
		}
		costos[1] = costos[0] * this.getDescuento();
		return costos;
	}

	@Override
	public double tiempoPromocion() {
		double horas = 0;
		for (Atraccion atraccion : this.atracciones) {
			horas = atraccion.getTiempo();
		}
		return horas;
	}

	@Override
	public double valorPromocion() {
		return 0;
	}

}
