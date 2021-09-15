package tierramedia;
import java.util.ArrayList;
import java.util.List;


public class Usuario {
	// Ordenamos los atributos segun el orden del txt.-
		private String nombre;
		private String atraccionPreferida;
		private int presupuesto;
		
		private double tiempoDisponible;
		private List<Atraccion> historialAtracciones;
		private List<Promocion> historialPromociones;
		private List<Atraccion> todasLasAtracciones;
		
		//Constructor
		public Usuario(String nombre, String atraccionPreferida, int presupuesto, double tiempoDisponible) {
			this.nombre = nombre;
			this.atraccionPreferida = atraccionPreferida;
			this.presupuesto = presupuesto;
			this.tiempoDisponible = tiempoDisponible;
			this.historialAtracciones = new ArrayList<Atraccion>();
			this.historialPromociones = new ArrayList<Promocion>();
			this.todasLasAtracciones = new ArrayList<Atraccion>();
		}
		
		

		// Almacena la sugerencia del usuario en el ArrayList historialAtracciones
		public void seleccionSugerencia() {
			
		}

		//Getters y Setters
		public List<Atraccion> getHistorialAtracciones() {
			return historialAtracciones;
		}


		public String getNombre() {
			return nombre;
		}

		public String getAtraccionPreferida() {
			return atraccionPreferida;
		}

		public int getPresupuesto() {
			return presupuesto;
		}

		public double getTiempoDisponible() {
			return tiempoDisponible;
		}

		public void setPresupuesto(int presupuesto) {
			this.presupuesto = presupuesto;
		}

		public void setTiempoDisponible(double tiempoDisponible) {
			this.tiempoDisponible = tiempoDisponible;
		}

		public List<Promocion> getHistorialPromociones() {
			return historialPromociones;
		}

		public List<Atraccion> getTodasLasAtracciones() {
			return todasLasAtracciones;
		}

}
