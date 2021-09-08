package tierramedia;

import java.util.ArrayList;

public class AxB extends Promocion {
	private Atraccion destinoExtra;

	public AxB(String nombrePromocion, ArrayList<Atraccion> atraccionesIncluidas, Atraccion destinoExtra) {
		super(nombrePromocion, atraccionesIncluidas);

		this.destinoExtra = destinoExtra;
	}
	
	protected Atraccion getDestinoExtra() {
		return destinoExtra;
	}

	@Override
	public void getBonus() {
		System.out.println(destinoExtra.getNombre());

	}

	@Override
	public Object getBonus1() {
		return destinoExtra;
	}

	@Override
	public double[] costoPromocion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double tiempoPromocion() {
		// TODO Auto-generated method stub
		return 0;
	}

}
