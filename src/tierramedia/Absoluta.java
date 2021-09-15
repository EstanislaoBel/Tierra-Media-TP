package tierramedia;

import java.util.List;

public class Absoluta extends Promocion {
	private int costoFinal;

	public Absoluta(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int costoFinal) {
		super(nombrePromocion, atraccionesIncluidas);
		this.costoFinal = costoFinal;
	}

	public int getCostoFinal() {
		return costoFinal;
	}

	@Override
	public double costoPromocion() {
		double costo = this.getCostoFinal();
		return costo;
	}

	@Override
	public int tipoPromocion() {
		return 1;
	}

	@Override
	public void getBonus() {
	}

	@Override
	public Object getBonus1() {
		return null;
	}

	public double descuentoMonedas() {

		double costoSinDescuento = 0;
		double descuentoMonedas = 0;

		for (Atraccion atraccion : this.atracciones) {
			costoSinDescuento += atraccion.getCosto();
		}

		descuentoMonedas = costoSinDescuento - this.costoPromocion();
		return descuentoMonedas;

	}

	@Override
	public double tiempoPromocion() {
		double horas = 0;

		for (Atraccion atraccion : this.atracciones) {
			horas += atraccion.getTiempo();
		}
		return horas;
	}

	@Override
	public String ImprimirBonus() {
		Double descuento = this.descuentoMonedas();
		String descuentos = descuento.toString();
		descuentos = "¡se ahorra " + descuentos + " monedas!";
		return descuentos;
	}

}
