package tierramedia;

import java.util.List;

public class AxB extends Promocion {
	private Atraccion destinoExtra;

	public AxB(String nombrePromocion, List<Atraccion> atraccionesIncluidas, Atraccion extra) {
		super(nombrePromocion, atraccionesIncluidas);
		this.destinoExtra = extra;
	}

	public Atraccion getDestinoExtra() {
		return destinoExtra;
	}

	@Override
	public double costoPromocion() {
		double costo = 0;

		for (Atraccion atraccion : this.atracciones) {
			costo += atraccion.getCosto();
		}

		return costo;
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
		String destinoGratis = destinoExtra.getNombre();
		return destinoGratis;
	}

	@Override
	public int tipoPromocion() {
		return 3;
	}

}
