package figuras;

public class rectangulo {
	private double ancho;
	private double alto;

	public rectangulo(double ancho, double alto) {
		this.ancho=ancho;
		this.alto=alto;
	}

	@Override
	public String toString() {
		return "rectangulo [ancho=" + ancho + ", alto=" + alto + "]";
	}
}
