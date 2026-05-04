package test;
	 
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.DisplayName;
	import static org.junit.jupiter.api.Assertions.*;
	import armas.Arma;
	/**
	 * Tests para la clase Arma.
	 *
	 * Casos cubiertos:
	 *  - calcularDaño() con arma cuerpo a cuerpo (dañoBase + 5)
	 *  - calcularDaño() con arma a distancia (dañoBase sin bono)
	 *  - getNombre() devuelve el nombre correcto
	 *  - esCuerpoACuerpo refleja el valor dado en el constructor
	 *  - dañoBase 0 → resultado esperado (caso límite)
	 */
	@DisplayName("Tests de Arma")

	class ArmaTest {
	 
	    // calcular daño 
	 
	    @Test
	    @DisplayName("Arma CaC: calcularDaño devuelve dañoBase + 5")
	    void calcularDano_cuerpoACuerpo_sumaBono() {
	        Arma katana = new Arma("Katana", 15, true);
	        assertEquals(20, katana.calcularDaño(),
	                "Un arma CaC debe sumar 5 al dañoBase");
	    }
	 
	    @Test
	    @DisplayName("Arma distancia: calcularDaño devuelve exactamente dañoBase")
	    void calcularDano_distancia_sinBono() {
	        Arma pistola = new Arma("Pistola", 8, false);
	        assertEquals(8, pistola.calcularDaño(),
	                "Un arma a distancia no debe tener bono");
	    }
	 
	    @Test
	    @DisplayName("Caso límite: dañoBase 0 CaC devuelve 5")
	    void calcularDano_cuerpoACuerpo_danoBaseCero() {
	        Arma puños = new Arma("Puños", 0, true);
	        assertEquals(5, puños.calcularDaño(),
	                "Con dañoBase 0 CaC el resultado debe ser 5 (solo el bono)");
	    }
	 
	    @Test
	    @DisplayName("Caso límite: dañoBase 0 distancia devuelve 0")
	    void calcularDano_distancia_danoBaseCero() {
	        Arma laserRoto = new Arma("Laser Roto", 0, false);
	        assertEquals(0, laserRoto.calcularDaño(),
	                "Con dañoBase 0 a distancia el resultado debe ser 0");
	    }
	 
	 
	    @Test
	    @DisplayName("getNombre devuelve el nombre asignado en el constructor")
	    void getNombre_devuelveNombreCorrecto() {
	        Arma arma = new Arma("Rifle Biótico", 8, false);
	        assertEquals("Rifle Biótico", arma.getNombre());
	    }
	 
	 
	    @Test
	    @DisplayName("esCuerpoACuerpo es true cuando se indica true en constructor")
	    void esCuerpoACuerpo_true() {
	        Arma arma = new Arma("Cuchillo", 10, true);
	        assertTrue(arma.esCuerpoACuerpo);
	    }
	 
	    @Test
	    @DisplayName("esCuerpoACuerpo es false cuando se indica false en constructor")
	    void esCuerpoACuerpo_false() {
	        Arma arma = new Arma("Pistola", 8, false);
	        assertFalse(arma.esCuerpoACuerpo);
	    }
	}

