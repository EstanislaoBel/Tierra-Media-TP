package tierramedia;

import java.util.ArrayList;

public class Absoluta extends Promocion {
	private int descuentoTotal;
	private int costoFinal;

	public Absoluta(String nombrePromocion, ArrayList<Atraccion> atraccionesIncluidas, int costoFinal) {
		super(nombrePromocion, atraccionesIncluidas);
		this.costoFinal = costoFinal;
	}

	public int getCostoFinal() {
		return costoFinal;
	}

	private Atraccion[] atracciones;

	public double tiempoPromocion() {
		double horas = 0;
		for (Atraccion atraccion : this.atracciones) {
			horas = atraccion.getTiempo();
		}
		return horas;
	}

	@Override
	public void getBonus() {
		System.out.println(costoFinal);
	}

	@Override
	public Object getBonus1() {
		return costoFinal;
	}

	@Override
	public double[] costoPromocion() {
		double costos[] = new double[2];

		for (Atraccion atraccion : this.atracciones) {
			costos[0] += atraccion.getCosto();
		}
		costos[1] = this.getCostoFinal();
		return costos;
	}

	@Override
	public double valorPromocion() {
		return costoFinal - descuentoTotal;
	}

}
