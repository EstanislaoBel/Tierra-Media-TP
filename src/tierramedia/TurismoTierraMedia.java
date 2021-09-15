package tierramedia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TurismoTierraMedia {

	// Declaracion de variables
	private static List<Atraccion> listaAtracciones;
	private static List<Usuario> listaUsuarios;
	private static List<Promocion> listaPromociones;

	// --------Metodos de Lectura de Archivos

	// Genera lista de usuarios
	public static List<Usuario> leerUsuarios() {
		listaUsuarios = new ArrayList<>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader("Archivos/Usuarios.txt");
			br = new BufferedReader(fr);

			String linea = br.readLine();
			while ((linea != null)) {
				String[] valores = linea.split(",");
				Usuario nuevoUsuario = new Usuario(valores[0], valores[1], (int) Double.parseDouble(valores[2]),
						Double.parseDouble(valores[3]));
				listaUsuarios.add(nuevoUsuario);
				linea = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return listaUsuarios;
	}

	// Genera lista de atracciones
	public static List<Atraccion> leerAtracciones() {
		listaAtracciones = new ArrayList<>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader("Archivos/Atracciones.txt");
			br = new BufferedReader(fr);

			String linea = br.readLine();
			while ((linea != null)) {
				String[] valores = linea.split(",");
				Atraccion nuevaAtraccion = new Atraccion(valores[0], (int) Double.parseDouble(valores[1]),
						Double.parseDouble(valores[2]), (int) Double.parseDouble(valores[3]), valores[4]);
				listaAtracciones.add(nuevaAtraccion);
				linea = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return listaAtracciones;
	}

	// Genera lista promociones
	public static List<Promocion> leerPromociones() {

		listaPromociones = new ArrayList<>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader("Archivos/Promociones.txt");
			br = new BufferedReader(fr);

			String linea = br.readLine();
			while ((linea != null)) {
				String[] valores = linea.split(",");

				if (!esNumerico(valores[valores.length - 1].trim())) {
					AxB nuevaPromocion = new AxB(valores[0], analizarPromocion(valores),
							buscadorAtraccion(valores[valores.length - 1]));
					listaPromociones.add(nuevaPromocion);
				} else {
					try {
						Absoluta nuevaPromocion = new Absoluta(valores[0], analizarPromocion(valores),
								(int) Integer.parseInt(valores[valores.length - 1].trim()));
						listaPromociones.add(nuevaPromocion);
					} catch (NumberFormatException e) {
						Porcentual nuevaPromocion = new Porcentual(valores[0], analizarPromocion(valores),
								(double) Double.parseDouble(valores[valores.length - 1].trim()));
						listaPromociones.add(nuevaPromocion);
					}
				}
				linea = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return listaPromociones;
	}

	// -------Metodos de Consola

	// Consola de lista de usuarios
	public static void consola() {
		leerAtracciones();
		leerPromociones();

		int opcion = 1;
		while (opcion != 99999) {
			if (listaUsuarios.isEmpty()) {
				System.out.println("--------- SE HAN PROCESADO TODOS LOS USUARIOS ---------");
				System.exit(0);
			}

			System.out.println("\nBienvenido a la Tierra Media!\nQue personaje te gustaria ser??");
			mostrarUsuarios();

			System.out.println("\nIngrese el numero correspondiente a su personaje");

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
				opcion = (int) Double.parseDouble(entrada);
			} else {
				opcion = listaUsuarios.size() + 1;
			}

			try {
				System.out.println("\nHas elegido ser: " + listaUsuarios.get(opcion - 1).getNombre());

				sugerir(listaUsuarios.get(opcion - 1));
				opcion = 99999;
			} catch (IndexOutOfBoundsException ex) {
				System.out
						.println("El valor ingresado solamente puede ser un entero entre 1 y " + listaUsuarios.size());
			}
		}
	}

	// Consola de lista de sugerencias
	public static void sugerir(Usuario usuario) {
		listaPromociones = actualizarListaPromociones(usuario);
		listaAtracciones = actualizarListaAtracciones(usuario);
		List<Promocion> promocionesPreferidas = filtrarPromocionesPreferidas(usuario);
		int cant_promos = promocionesPreferidas.size();
		List<Promocion> promocionesNoPreferidas = filtrarPromocionesNoPreferidas(usuario);
		int cant_promosN = promocionesNoPreferidas.size();
		List<Atraccion> atraccionesPreferidas = filtrarAtraccionesPreferidas(usuario);
		int cant_atrac = atraccionesPreferidas.size();
		List<Atraccion> atraccionesNoPreferidas = filtrarAtraccionesNoPreferidas(usuario);
		int cant_atracN = atraccionesNoPreferidas.size();

		int opcion = 1;

		while (opcion != 99999) {
			if (cant_promos != 0
					&& (usuario.getPresupuesto() >= costoMinP() || usuario.getTiempoDisponible() >= tiempoMinP())) {
				sugerirPromocionesPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			} else if (cant_atrac != 0
					&& (usuario.getPresupuesto() >= costoMin() || usuario.getTiempoDisponible() >= tiempoMin())) {
				sugerirAtraccionesPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			} else if (cant_promosN != 0
					&& (usuario.getPresupuesto() >= costoMinP() || usuario.getTiempoDisponible() >= tiempoMinP())) {
				sugerirPromocionesNoPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			} else if (cant_atracN != 0
					&& (usuario.getPresupuesto() >= costoMin() || usuario.getTiempoDisponible() >= tiempoMin())) {
				sugerirAtraccionesNoPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			} else {
				borrarUsuario(usuario, opcion);
			}
		}
	}

	// Metodo para cuando no puedes realizar compras
	public static void borrarUsuario(Usuario usuario, int opcion) {
		System.out.println("No puedes realizar compras!");
		generarArchivoUsuario(usuario);
		opcion = 99999;
		mostrarItinerario(usuario);
		listaUsuarios.remove(usuario);
		consola();
	}

	// -------Metodos de Salida de Usuario

	// Mostrar Itinerario de compras
	public static void mostrarItinerario(Usuario usuario) {
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------");
		if (usuario.getHistorialPromociones().isEmpty() && usuario.getHistorialAtracciones().isEmpty()) {
			System.out.println("Usted no pudo comprar nada!!!!");
		} else {
			System.out.println(
					"=================================================================================================================================");
			System.out.println("\nA continuacion le detallamos el itinerario:");
			double suma_costos = 0;
			double suma_horas = 0;
			if (!(usuario.getHistorialPromociones().isEmpty())) {
				List<Promocion> promocionesCompradas = usuario.getHistorialPromociones();
				System.out.println("\nPromociones Compradas:");
				for (Promocion promo : promocionesCompradas) {
					suma_costos += promo.costoPromocion();
					suma_horas += promo.tiempoPromocion();
					List<Atraccion> atraccionesPromo = promo.getAtracciones();
					System.out.println(
							"---------------------------------------------------------------------------------------------------------------------------------");
					System.out.println("\nPromocion: " + promo.getNombre() + ", precio: " + promo.costoPromocion()
							+ " monedas, duracion: " + promo.tiempoPromocion() + " hs., bonus: "
							+ promo.ImprimirBonus());
					System.out.println("\nAtracciones Incluidas:");
					for (Atraccion atraccion : atraccionesPromo) {
						System.out.println("\n* " + atraccion.getNombre());
					}
				}

				System.out.println(
						"_________________________________________________________________________________________________________________________________");
			}

			if (!(usuario.getHistorialAtracciones().isEmpty())) {
				List<Atraccion> atraccionesCompradas = usuario.getHistorialAtracciones();
				System.out.println("\nAtracciones Compradas:");

				for (Atraccion atraccion : atraccionesCompradas) {
					suma_costos += atraccion.getCosto();
					suma_horas += atraccion.getTiempo();
					System.out.println(
							"---------------------------------------------------------------------------------------------------------------------------------");
					System.out.println("\nAtraccion: " + atraccion.getNombre() + ", precio: " + atraccion.getCosto()
							+ " monedas, duracion: " + atraccion.getTiempo() + " hs.");
				}
			}
			System.out.println(
					"_________________________________________________________________________________________________________________________________");
			System.out.println("\nCosto total: " + suma_costos + " monedas, tiempo necesario: " + suma_horas + " hs.");
			System.out.println(
					"=================================================================================================================================");
		}
	}

	// Metodo que genera el archivo final por usuario
	public static void generarArchivoUsuario(Usuario unUsuario) {
		try {
			String ruta = "./Archivos/Archivos Generados/" + unUsuario.getNombre() + ".txt";

			String contenido = "";

			contenido = "Nombre: " + unUsuario.getNombre();
			contenido += "\n==================================================================================";
			contenido += "\nDetalles de su compra: ";
			contenido += "\n==================================================================================";
			if (!(unUsuario.getHistorialPromociones().isEmpty())) {
				double costoTotal = 0;
				double tiempoTotal = 0;
				contenido += "\nPromociones Compradas:";
				for (Promocion promo : unUsuario.getHistorialPromociones()) {
					List<Atraccion> atraccionesPromo = promo.getAtracciones();
					contenido += "\n----------------------------------------------------------------------------------";
					contenido += "\nPromocion: " + promo.getNombre() + ", precio: " + promo.costoPromocion()
							+ " monedas, duracion: " + promo.tiempoPromocion() + " hs., bonus: "
							+ promo.ImprimirBonus();
					contenido += "\nAtracciones Incluidas:";
					costoTotal += promo.costoPromocion();
					tiempoTotal += promo.tiempoPromocion();
					for (Atraccion atraccion : atraccionesPromo) {
						contenido += "\n* " + atraccion.getNombre();
					}
				}
				contenido += "\nCosto total de las promociones: " + costoTotal;
				contenido += "\nTiempo total de las promociones: " + tiempoTotal;
				contenido += "\n----------------------------------------------------------------------------------";
			}
            
			if (!(unUsuario.getHistorialAtracciones().isEmpty())) {
				double costoTotal = 0;
				double tiempoTotal = 0;

				contenido += "\nAtracciones Compradas:";
				for (Atraccion atraccion : unUsuario.getHistorialAtracciones()) {
					contenido += "\n----------------------------------------------------------------------------------";
					contenido += "\nAtraccion: " + atraccion.getNombre() + ", precio: " + atraccion.getCosto()
							+ " monedas, duracion: " + atraccion.getTiempo() + " hs.";
					costoTotal += atraccion.getCosto();
					tiempoTotal += atraccion.getTiempo();
				}
				contenido += "\nCosto total de las atracciones: " + costoTotal;
				contenido += "\nTiempo total de las atracciones: " + tiempoTotal;
				contenido += "\n----------------------------------------------------------------------------------";
				contenido +="\nSi encuentra un error comuniquese con la Secretaría de Turismo de la Tierra Media";
				contenido +="\nGracias por su tiempo!";
			}

			File file = new File(ruta);

			// Se comprueba si ya existe el archivo
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(contenido);

			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Archivo generado exitosamente!");
	}

	// ------Metodos Para Sugerir Promociones y Atracciones

	// Sugerir Promociones Preferidas
	public static void sugerirPromocionesPreferidas(Usuario usuario, List<Promocion> promocionesPreferidas,
			List<Atraccion> atraccionesPreferidas, List<Promocion> promocionesNoPreferidas,
			List<Atraccion> atraccionesNoPreferidas, int opcion) {

		while (!promocionesPreferidas.isEmpty()) {
			if (usuario.getPresupuesto() < costoMinP() || usuario.getTiempoDisponible() < tiempoMinP()) {
				if (usuario.getPresupuesto() < costoMin() || usuario.getTiempoDisponible() < tiempoMin()) {
					borrarUsuario(usuario, opcion);
				}
			}
			System.out.println("\nPromociones recomendadas para usted:");
			mostrarPromociones(promocionesPreferidas);
			System.out.println(
					"_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y "
					+ usuario.getTiempoDisponible() + " hs disponibles.");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una promocion del listado ingrese su numero");
			System.out.println("\nSino presione la tecla N");

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.toUpperCase().compareTo("N") == 0) {
				sugerirAtraccionesPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					System.out.println(
							"\nHas elegido la promocion: " + promocionesPreferidas.get(opcion - 1).getNombre());
					
					actualizarUsuarioPromocion(usuario, promocionesPreferidas, opcion - 1);
					actualizarHistorialPromociones(usuario, promocionesPreferidas, opcion - 1);
					
					actualizarCupoAtraccionPromo(promocionesPreferidas, opcion - 1);

					listaPromociones = actualizarListaPromociones(usuario);
					listaAtracciones = actualizarListaAtracciones(usuario);
					promocionesPreferidas = filtrarPromocionesPreferidas(usuario);
					atraccionesPreferidas = filtrarAtraccionesPreferidas(usuario);
					promocionesNoPreferidas = filtrarPromocionesNoPreferidas(usuario);
					atraccionesNoPreferidas = filtrarAtraccionesNoPreferidas(usuario);
				} else {
					opcion = promocionesPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + promocionesPreferidas.size());
			}
		}
		sugerir(usuario);
	}

	// Sugerir atracciones preferidas
	public static void sugerirAtraccionesPreferidas(Usuario usuario, List<Promocion> promocionesPreferidas,
			List<Atraccion> atraccionesPreferidas, List<Promocion> promocionesNoPreferidas,
			List<Atraccion> atraccionesNoPreferidas, int opcion) {

		while (!atraccionesPreferidas.isEmpty()) {
			if (usuario.getPresupuesto() < costoMin() || usuario.getTiempoDisponible() < tiempoMin()) {
				borrarUsuario(usuario, opcion);
			}
			System.out.println("\nAtracciones recomendadas para usted:");
			
			ordenarAtraccionesCostoHoras(atraccionesPreferidas);
			mostrarAtracciones(atraccionesPreferidas);
			System.out.println(
					"_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y "
					+ usuario.getTiempoDisponible() + " hs disponibles.");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una atraccion del listado ingrese su numero");
			System.out.println("\nSino presione la tecla N");

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.toUpperCase().compareTo("N") == 0) {
				sugerirPromocionesNoPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					System.out.println(
							"\nHas elegido la atraccion: " + atraccionesPreferidas.get(opcion - 1).getNombre());

					actualizarUsuarioAtraccion(usuario, atraccionesPreferidas, opcion - 1);
					actualizarHistorialAtracciones(usuario, atraccionesPreferidas, opcion - 1);
					
					actualizarCupoAtraccion(atraccionesPreferidas, opcion - 1);

					listaAtracciones = actualizarListaAtracciones(usuario);
					;
					promocionesPreferidas = filtrarPromocionesPreferidas(usuario);
					atraccionesPreferidas = filtrarAtraccionesPreferidas(usuario);
					promocionesNoPreferidas = filtrarPromocionesNoPreferidas(usuario);
					atraccionesNoPreferidas = filtrarAtraccionesNoPreferidas(usuario);

				} else {
					opcion = atraccionesPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + atraccionesPreferidas.size());
			}
		}
		sugerir(usuario);
	}

	// Sugerir promociones no preferidas
	public static void sugerirPromocionesNoPreferidas(Usuario usuario, List<Promocion> promocionesPreferidas,
			List<Atraccion> atraccionesPreferidas, List<Promocion> promocionesNoPreferidas,
			List<Atraccion> atraccionesNoPreferidas, int opcion) {

		while (!promocionesNoPreferidas.isEmpty()) {
			if (usuario.getPresupuesto() < costoMinP() || usuario.getTiempoDisponible() < tiempoMinP()) {
				if (usuario.getPresupuesto() < costoMin() || usuario.getTiempoDisponible() < tiempoMin()) {
					borrarUsuario(usuario, opcion);
				}
			}
			System.out.println("\nOtras promociones recomendadas para usted:");
			mostrarPromociones(promocionesNoPreferidas);
			System.out.println(
					"_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y "
					+ usuario.getTiempoDisponible() + " hs disponibles.");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una promocion del listado ingrese su numero");
			System.out.println("\nSino presione la tecla N");

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.toUpperCase().compareTo("N") == 0) {
				sugerirAtraccionesNoPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					System.out.println(
							"\nHas elegido la promocion: " + promocionesNoPreferidas.get(opcion - 1).getNombre());

					actualizarUsuarioPromocion(usuario, promocionesNoPreferidas, opcion - 1);
					actualizarHistorialPromociones(usuario, promocionesNoPreferidas, opcion - 1);
					
					actualizarCupoAtraccionPromo(promocionesNoPreferidas, opcion - 1);

					listaPromociones = actualizarListaPromociones(usuario);
					listaAtracciones = actualizarListaAtracciones(usuario);
					promocionesPreferidas = filtrarPromocionesPreferidas(usuario);
					atraccionesPreferidas = filtrarAtraccionesPreferidas(usuario);
					promocionesNoPreferidas = filtrarPromocionesNoPreferidas(usuario);
					atraccionesNoPreferidas = filtrarAtraccionesNoPreferidas(usuario);
				} else {
					opcion = promocionesNoPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + promocionesNoPreferidas.size());
			}
		}
		sugerir(usuario);
	}

	// Sugerir atracciones no preferidas
	public static void sugerirAtraccionesNoPreferidas(Usuario usuario, List<Promocion> promocionesPreferidas,
			List<Atraccion> atraccionesPreferidas, List<Promocion> promocionesNoPreferidas,
			List<Atraccion> atraccionesNoPreferidas, int opcion) {

		while (!atraccionesNoPreferidas.isEmpty()) {
			if (usuario.getPresupuesto() < costoMin() || usuario.getTiempoDisponible() < tiempoMin()) {
				borrarUsuario(usuario, opcion);
			}

			System.out.println("\nOtras atracciones recomendadas para usted:");

			ordenarAtraccionesCostoHoras(atraccionesNoPreferidas);
			mostrarAtracciones(atraccionesNoPreferidas);
			System.out.println(
					"_________________________________________________________________________________________________________________");
			System.out.println("\nUsted tiene: " + usuario.getPresupuesto() + " monedas y "
					+ usuario.getTiempoDisponible() + " hs disponibles.");
			System.out.println(
					"_________________________________________________________________________________________________________________\n");
			System.out.println("\nSi desea comprar una atraccion del listado ingrese su numero");
			System.out.println("\nSino presione la tecla N");

			Scanner sc = new Scanner(new InputStreamReader(System.in));
			String entrada = sc.next();
			if (entrada.toUpperCase().compareTo("N") == 0) {
				sugerirPromocionesPreferidas(usuario, promocionesPreferidas, atraccionesPreferidas,
						promocionesNoPreferidas, atraccionesNoPreferidas, opcion);
			}
			try {
				if (entrada.matches("-?\\d+(\\.,0\\d+)?")) {
					opcion = (int) Double.parseDouble(entrada);
					System.out.println(
							"\nHas elegido la atraccion: " + atraccionesNoPreferidas.get(opcion - 1).getNombre());

					actualizarUsuarioAtraccion(usuario, atraccionesNoPreferidas, opcion - 1);
					actualizarHistorialAtracciones(usuario, atraccionesNoPreferidas, opcion - 1);
					
					actualizarCupoAtraccion(atraccionesNoPreferidas, opcion - 1);

					listaAtracciones = actualizarListaAtracciones(usuario);
					promocionesPreferidas = filtrarPromocionesPreferidas(usuario);
					atraccionesPreferidas = filtrarAtraccionesPreferidas(usuario);
					promocionesNoPreferidas = filtrarPromocionesNoPreferidas(usuario);
					atraccionesNoPreferidas = filtrarAtraccionesNoPreferidas(usuario);

				} else {
					opcion = atraccionesNoPreferidas.size() + 1;
				}
			} catch (IndexOutOfBoundsException ex) {
				System.out.println(
						"El valor ingresado solamente puede ser un entero entre 1 y " + atraccionesNoPreferidas.size());
			}
		}
		sugerir(usuario);
	}

	// -------Metodos de Actualizacion de listas usadas en las sugerencias

	// Actualizar lista de promociones sobre la cual filtrar, armando una lista
	// nueva que ignora las promociones y atracciones que ya compró
	public static List<Promocion> actualizarListaPromociones(Usuario usuario) {
		List<Promocion> promocionesActualizada = new ArrayList<Promocion>();
		List<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();

		for (Promocion promocion : listaPromociones) {
			if (!(usuario.getHistorialPromociones().contains(promocion))) {
				atraccionesPromo = promocion.getAtracciones();
				int cont = 0;
				
				for (Atraccion ap : atraccionesPromo) {
					if ((usuario.getTodasLasAtracciones().contains(ap))) {
						cont++;
					}
				}
				
				if (promocion.tipoPromocion() == 3) {
					for (Atraccion a : usuario.getTodasLasAtracciones()) {
						if (tipoAtraccionGratis(promocion).compareTo(a.getTipoDeAtraccion()) == 0) {
							cont++;
						}
					}
				}
				if (cont == 0) {
					promocionesActualizada.add(promocion);
				}
			}
		}
		return promocionesActualizada;
	}

	// Actualizar lista de atracciones sobre la cual filtrar, armando una lista
	// nueva que ignora las atracciones que ya compro
	public static List<Atraccion> actualizarListaAtracciones(Usuario usuario) {
		List<Atraccion> atraccionesActualizada = new ArrayList<Atraccion>();

		for (Atraccion atraccion : listaAtracciones) {
			if (!(usuario.getTodasLasAtracciones().contains(atraccion))) {
				atraccionesActualizada.add(atraccion);
			}
		}
		return atraccionesActualizada;
	}

	// ------Metodos de Actualizacion de listas de usuario
	
	// Actualizar historial de Atracciones
	public static void actualizarHistorialAtracciones(Usuario usuario, List<Atraccion> atracciones, int opcion) {
		usuario.getHistorialAtracciones().add(atracciones.get(opcion));
		usuario.getTodasLasAtracciones().add(atracciones.get(opcion));
	}

	// Actualizar historial de Promociones
	public static void actualizarHistorialPromociones(Usuario usuario, List<Promocion> promociones, int opcion) {
		List<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
		usuario.getHistorialPromociones().add(promociones.get(opcion));
		for (Promocion promo : usuario.getHistorialPromociones()) {
			atraccionesPromo = promo.getAtracciones();
			for (Atraccion atracc : atraccionesPromo) {
				usuario.getTodasLasAtracciones().add(atracc);
				if (promo.tipoPromocion() == 3) {
					String gratis = promo.ImprimirBonus();
					for (Atraccion atraccL : listaAtracciones) {
						if (gratis.compareTo(atraccL.getNombre()) == 0) {
							usuario.getTodasLasAtracciones().add(atraccL);
						}
					}
				}
			}
		}
	}

	// Actualizar presupuesto y tiempo del usuario cuando elige promocion
	public static void actualizarUsuarioPromocion(Usuario usuario, List<Promocion> promociones, int opcion) {
		usuario.setPresupuesto((int) (usuario.getPresupuesto() - promociones.get(opcion).costoPromocion()));
		usuario.setTiempoDisponible(usuario.getTiempoDisponible() - promociones.get(opcion).tiempoPromocion());
	}

	// Actualizar presupuesto y tiempo del usuario cuando elige atraccion
	public static void actualizarUsuarioAtraccion(Usuario usuario, List<Atraccion> atracciones, int opcion) {
		usuario.setPresupuesto((int) (usuario.getPresupuesto() - atracciones.get(opcion).getCosto()));
		usuario.setTiempoDisponible(usuario.getTiempoDisponible() - atracciones.get(opcion).getTiempo());
	}

	// -------Metodos de Actualizacion de listas: Atracciones y Promociones 

	// Actualizar del cupo de atraccion que esta en una promocion
	public static void actualizarCupoAtraccionPromo(List<Promocion> promociones, int opcion) {
		List<Atraccion> atraccionesPromo = new ArrayList<Atraccion>();
		for (Atraccion a : listaAtracciones) {
			atraccionesPromo = promociones.get(opcion).getAtracciones();
			for (Atraccion atracc : atraccionesPromo) {
				if ((a.getNombre()).compareTo(atracc.getNombre()) == 0) {
					a.setCupo(a.getCupo() - 1);
				}
			}
		}
		
		for (Promocion p : promociones) {
			if (p.tipoPromocion() == 3) {
				String gratis = p.ImprimirBonus();
				for (Atraccion atraccion : listaAtracciones) {
					if (gratis.compareTo(atraccion.getNombre()) == 0) {
						atraccion.setCupo(atraccion.getCupo() - 1);
					}
				}
			}
		}

	}

	// Actualizar cupo de la atraccion
	public static void actualizarCupoAtraccion(List<Atraccion> atracciones, int i) {
		atracciones.get(i).setCupo(atracciones.get(i).getCupo() - 1);
	}

	// -------Metodos de Filtro segun preferencias del usuario, tiempo y presupuesto

	// Metodo que genera una lista con promociones segun sus preferencias...
	public static List<Promocion> filtrarPromocionesPreferidas(Usuario usuario) {
		List<Promocion> promocionesRecomendadas = new ArrayList<Promocion>();
		List<Atraccion> atraccionesP = new ArrayList<Atraccion>();

		for (Promocion promocion : listaPromociones) {
			atraccionesP = promocion.getAtracciones();
			int c = 0;
			
			if (promocion.tipoPromocion() == 3) {
				tipoAtraccionGratis(promocion);
				for (Atraccion atraccionP : atraccionesP) {
					if (tipoAtraccionGratis(promocion).compareTo(usuario.getAtraccionPreferida()) == 0) {
						if (atraccionP.getCupo() > 0 && usuario.getPresupuesto() >= promocion.costoPromocion()
								&& usuario.getTiempoDisponible() >= promocion.tiempoPromocion()) {
							c++;
						}
					}
				}
			} else {
				for (Atraccion atraccionP : atraccionesP) {
					if ((atraccionP.getTipoDeAtraccion()).compareTo(usuario.getAtraccionPreferida()) == 0) {
						if (atraccionP.getCupo() > 0 && usuario.getPresupuesto() >= promocion.costoPromocion()
								&& usuario.getTiempoDisponible() >= promocion.tiempoPromocion()) {
							c++;
						}
					}
				}
			}
			if (c > 0) {
				promocionesRecomendadas.add(promocion);
			}
		}
		return promocionesRecomendadas;
	}

	// Metodo que genera una lista con promociones que NO cumplen sus
	// preferencias...
	public static List<Promocion> filtrarPromocionesNoPreferidas(Usuario usuario) {
		List<Promocion> promocionesNoRecomendadas = new ArrayList<Promocion>();
		List<Atraccion> atraccionesP = new ArrayList<Atraccion>();

		for (Promocion promocion : listaPromociones) {
			atraccionesP = promocion.getAtracciones();
			int c = 0;
			
			if (promocion.tipoPromocion() == 3) {
				tipoAtraccionGratis(promocion);
				for (Atraccion atraccionP : atraccionesP) {
					if (tipoAtraccionGratis(promocion).compareTo(usuario.getAtraccionPreferida()) != 0) {
						if (atraccionP.getCupo() > 0 && usuario.getPresupuesto() >= promocion.costoPromocion()
								&& usuario.getTiempoDisponible() >= promocion.tiempoPromocion()) {
							c++;
						}
					}
				}
			} else {
				for (Atraccion atraccionP : atraccionesP) {
					if ((atraccionP.getTipoDeAtraccion()).compareTo(usuario.getAtraccionPreferida()) != 0) {
						if (atraccionP.getCupo() > 0 && usuario.getPresupuesto() >= promocion.costoPromocion()
								&& usuario.getTiempoDisponible() >= promocion.tiempoPromocion()) {
							c++;
						}
					}
				}
			}
			if (c == atraccionesP.size()) {
				promocionesNoRecomendadas.add(promocion);
			}
		}
		return promocionesNoRecomendadas;
	}

	// Metodo que genera una lista con atracciones segun sus preferencias
	public static List<Atraccion> filtrarAtraccionesPreferidas(Usuario usuario) {
		List<Atraccion> atraccionesRecomendadas = new ArrayList<Atraccion>();

		for (Atraccion atraccion : listaAtracciones) {
			if ((usuario.getAtraccionPreferida()).compareTo(atraccion.getTipoDeAtraccion()) == 0) {
				if (atraccion.getCupo() > 0 && usuario.getPresupuesto() >= atraccion.getCosto()
						&& usuario.getTiempoDisponible() >= atraccion.getTiempo()) {
					atraccionesRecomendadas.add(atraccion);
				}
			}
		}
		return atraccionesRecomendadas;
	}

	// Metodo que genera una lista con atracciones que no cumplen sus preferencias
	public static List<Atraccion> filtrarAtraccionesNoPreferidas(Usuario usuario) {
		List<Atraccion> atraccionesNoRecomendadas = new ArrayList<Atraccion>();

		for (Atraccion atraccion : listaAtracciones) {
			if ((usuario.getAtraccionPreferida()).compareTo(atraccion.getTipoDeAtraccion()) != 0) {
				if (atraccion.getCupo() > 0 && usuario.getPresupuesto() >= atraccion.getCosto()
						&& usuario.getTiempoDisponible() >= atraccion.getTiempo()) {
					atraccionesNoRecomendadas.add(atraccion);
				}
			}
		}
		return atraccionesNoRecomendadas;
	}

	// ------Metodos que muestran listados por pantalla
	
	// Mostrar Promociones
	public static void mostrarPromociones(List<Promocion> promocionesMostrar) {
		List<Promocion> promociones = promocionesMostrar;
		int cant = 0;
		for (Promocion promocion : promociones) {
			cant++;
			System.out.print(cant + " - " + promocion.getNombre() + " - Destinos: ");
			for (Atraccion atraccion : promocion.getAtracciones()) {
				System.out.print(atraccion.getNombre() + ", ");
			}
			System.out.println(promocion.ImprimirBonus() + ". Precio de la promo: " + promocion.costoPromocion()
					+ " monedas, duracion: " + promocion.tiempoPromocion() + " hs.");
		}
	}

	// Mostrar Atracciones
	public static void mostrarAtracciones(List<Atraccion> atraccionesMostrar) {
		List<Atraccion> atracciones = atraccionesMostrar;
		int cant = 0;
		for (Atraccion atraccion : atracciones) {
			cant++;
			System.out.println(cant + " - " + atraccion.getNombre() + ": su costo es de " + atraccion.getCosto()
					+ " monedas, debe disponer de " + atraccion.getTiempo() + " hs. y su cupo es de "
					+ atraccion.getCupo() + " personas.");
		}
	}

	// Mostrar lista de usuarios
	public static void mostrarUsuarios() {
		int p = 0;

		Iterator<Usuario> itUsuarios = listaUsuarios.iterator();

		while (itUsuarios.hasNext()) {
			Usuario usuario = itUsuarios.next();
			p += 1;
			System.out.println(p + " - " + usuario.getNombre() + "------>   Atraccion preferida:"
					+ usuario.getAtraccionPreferida() + " - Presupuesto: " + usuario.getPresupuesto() + " monedas"
					+ " - Tiempo disponible: " + usuario.getTiempoDisponible() + " hs.");
		}
	}

	// -------Otros Metodos--------

	// Metodo que devuelve el tipo de una atraccion gratis
	public static String tipoAtraccionGratis(Promocion promocion) {
		String gratis = promocion.ImprimirBonus();
		String tipo_gratis = "";
		for (Atraccion atraccion : listaAtracciones) {
			if (gratis.trim().compareTo((atraccion.getNombre()).trim()) == 0) {
				tipo_gratis = atraccion.getTipoDeAtraccion();
			}
		}
		return tipo_gratis;
	}

	// Metodo que ordena las atracciones por costo y horas, respectivamente
	public static void ordenarAtraccionesCostoHoras(List<Atraccion> listaAtracciones) {
		Collections.sort(listaAtracciones, new ComparadorDeAtracciones());
	}

	// Metodo para calcular costo minimo de una promocion
	public static int costoMin() {
		int cMin = 99999;

		for (Atraccion atraccion : listaAtracciones) {
			if (atraccion.getCosto() < cMin) {
				cMin = atraccion.getCosto();
			}
		}
		return cMin;
	}

	// Metodo para calcular tiempo minimo que dura una atraccion
	public static double tiempoMin() {
		double tMin = 99999;

		for (Atraccion atraccion : listaAtracciones) {
			if (atraccion.getTiempo() < tMin) {
				tMin = atraccion.getTiempo();
			}
		}
		return tMin;
	}

	// Metodo para calcular costo minimo de una promocion
	public static int costoMinP() {
		int cMin = 99999;

		for (Promocion promocion : listaPromociones) {
			if (promocion.costoPromocion() < cMin) {
				cMin = (int) promocion.costoPromocion();
			}
		}
		return cMin;
	}

	// Metodo para calcular tiempo minimo que dura una promocion
	public static double tiempoMinP() {
		double tMin = 99999;

		for (Promocion promocion : listaPromociones) {
			if (promocion.tiempoPromocion() < tMin) {
				tMin = promocion.tiempoPromocion();
			}
		}
		return tMin;
	}

	// Metodo para verificar si la variable es numerica
	public static boolean esNumerico(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	// Metodo que recibe la linea de lectura y devuelve un arraylist de las
	// atracciones para el
	// llamado del constructor
	public static List<Atraccion> analizarPromocion(String[] lectura) {
		List<Atraccion> atracciones = new ArrayList<>();
		for (int x = 1; x <= (lectura.length - 2); x++) {
			atracciones.add(buscadorAtraccion(lectura[x]));
		}
		return atracciones;
	}

	// Metodo que recibe el nombre de una atraccion, busca en el arraylist de
	// atracciones y devuelve una atraccion
	public static Atraccion buscadorAtraccion(String str) {
		Atraccion resultado = null;
		for (Atraccion atraccion : listaAtracciones) {
			if (atraccion.getNombre().equals(str.trim())) {
				resultado = atraccion;
			}
		}
		return resultado;
	}

	public static void main(String[] args) {
		leerUsuarios();
		consola();
	}
}
