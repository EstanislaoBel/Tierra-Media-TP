package tierramedia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurismoTierraMediaPruebas {

	Atraccion prueba;
	List<Atraccion> listaAtracciones;
	List<Usuario> listaUsuarios;
	List<Promocion> listaPromociones;

	@Before
	public void setUp() {
		listaUsuarios = TurismoTierraMedia.leerUsuarios();
		listaAtracciones = TurismoTierraMedia.leerAtracciones();
		listaPromociones = TurismoTierraMedia.leerPromociones();
	}

	@Test
	public void LeerAtraccionesTest() {
		assertNotNull(listaAtracciones);
	}

	@Test
	public void LeerPromocionesTest() {
		assertNotNull(listaAtracciones);
	}
	
	@Test
	public void LeerUsuariosTest() {
		assertNotNull(listaUsuarios);
	}

	@Test
	public void costoMinimoAtraccionTest() {// el costo minimo de las atracciones es 3.
		assertEquals(3, TurismoTierraMedia.costoMin(), 0);
	}

	@Test
	public void tiempoMinimoAtraccionTest() {// el tiempo minimo de las atracciones es 1.
		assertEquals(1, TurismoTierraMedia.tiempoMin(), 0);
	}

	@Test
	public void buscadorAtraccionTest() {// busca la atraccion y de paso pregunta su tiempo para saber que esta trayendo
											// el objeto
		prueba = TurismoTierraMedia.buscadorAtraccion("La Comarca");
		Assert.assertEquals("La Comarca", prueba.getNombre());
		Assert.assertEquals(6.5, prueba.getTiempo(), 0);

	}

	@Test
	public void OrdenarAtraccionesPorCostoHorasTest() {
		List<Atraccion> listaCopia;
		listaCopia = new ArrayList<>(listaAtracciones);
		TurismoTierraMedia.ordenarAtraccionesCostoHoras(listaCopia);
		Assert.assertNotEquals(listaAtracciones, listaCopia);

	}

	@Test
	public void actualizarCupoAtraccionTest() {// recibe una lista de atraccion y le resta 1 a su cupo
		List<Atraccion> listaCopia;
		listaCopia = new ArrayList<>(listaAtracciones);
		TurismoTierraMedia.actualizarCupoAtraccion(listaCopia, 2);
		Assert.assertNotEquals(150, listaCopia.get(2).getCupo());

	}

}
