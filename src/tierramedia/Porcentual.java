package tierramedia;

import java.util.List;

public class Porcentual extends Promocion {
	private double descuento;

	public Porcentual(String nombrePromocion, List<Atraccion> atraccionesIncluidas, double descuento) {
		super(nombrePromocion, atraccionesIncluidas);
		this.descuento = descuento;
	}

	public double getDescuento() {
		return descuento;
	}

	@Override
	public double costoPromocion() {
		double costo = 0;

		for (Atraccion atraccion : this.atracciones) {
			costo += atraccion.getCosto();
		}
		costo = costo - (costo * this.getDescuento());
		return costo;
	}

	@Override
	public String ImprimirBonus() {
		Double descuento = this.getDescuento() * 100;
		String descuentos = descuento.toString();
		descuentos = "obtiene un descuento del " + descuentos + " %";
		return descuentos;
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
	public int tipoPromocion() {
		return 2;
	}

}
