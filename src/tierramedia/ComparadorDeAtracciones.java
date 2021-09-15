package tierramedia;

import java.util.Comparator;

public class ComparadorDeAtracciones implements Comparator<Atraccion> {
	
	@Override
	public int compare(Atraccion a1, Atraccion a2) {

		Integer costo1 = a2.getCosto();
		Integer costo2 = a1.getCosto();
		int resComp = costo1.compareTo(costo2);

		if (resComp != 0) {
			return resComp;
		}

		Double tiempo1 = a2.getTiempo();
		Double tiempo2 = a1.getTiempo();
		return tiempo1.compareTo(tiempo2);
	}
}
