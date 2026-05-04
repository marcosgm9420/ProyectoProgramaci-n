package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import personaje.Personaje;
import armas.Arma;
import estado.Estado;

/**
 * Tests para la clase Estado.
 *
 * Casos cubiertos:
 *  - haTerminado() devuelve false si quedan turnos
 *  - haTerminado() devuelve true cuando duracion llega a 0
 *  - aplicarEfecto() con DoT reduce la vida del objetivo
 *  - aplicarEfecto() con HoT aumenta la vida del objetivo
 *  - aplicarEfecto() descuenta 1 de duracion en cada llamada
 *  - HoT no supera vidaMax (la curación la limita Personaje.curar)
 *  - DoT no baja de 0 HP (la limita Personaje.recibirDaño)
 */
@DisplayName("Tests de Estado")
class EstadoTest {

    // Personaje auxiliar para los tests: 100 HP, 50 energía, arma distancia 
    private Personaje crearPersonaje(int vida) {
        // Subclase anónima mínima para poder instanciar Personaje directamente
        return new Personaje("Test", vida, 50, new Arma("Arma", 5, false));
    }

    @Test
    @DisplayName("haTerminado devuelve false con duracion > 0")
    void haTerminado_duracionPositiva_false() {
        Estado e = new Estado("Veneno", 10, 3, true);
        assertFalse(e.haTerminado());
    }

    @Test
    @DisplayName("haTerminado devuelve true con duracion = 0")
    void haTerminado_duracionCero_true() {
        Estado e = new Estado("Veneno", 10, 0, true);
        assertTrue(e.haTerminado());
    }

    @Test
    @DisplayName("haTerminado devuelve true con duracion negativa")
    void haTerminado_duracionNegativa_true() {
        Estado e = new Estado("Veneno", 10, -1, true);
        assertTrue(e.haTerminado());
    }

    @Test
    @DisplayName("DoT: aplicarEfecto reduce la vida del objetivo en 'potencia' HP")
    void aplicarEfecto_dot_reduceVida() {
        Personaje p = crearPersonaje(100);
        Estado veneno = new Estado("Veneno", 10, 3, true);
        veneno.aplicarEfecto(p);
        assertEquals(90, p.getVidaActual());
    }

    @Test
    @DisplayName("HoT: aplicarEfecto aumenta la vida del objetivo en 'potencia' HP")
    void aplicarEfecto_hot_aumentaVida() {
        Personaje p = crearPersonaje(100);
        p.recibirDaño(30); // dejarlo a 70 HP
        Estado regen = new Estado("Regen", 10, 3, false);
        regen.aplicarEfecto(p);
        assertEquals(80, p.getVidaActual());
    }

    @Test
    @DisplayName("aplicarEfecto descuenta 1 de duracion por llamada")
    void aplicarEfecto_decrementaDuracion() {
        Personaje p = crearPersonaje(100);
        Estado e = new Estado("Fuego", 5, 3, true);
        e.aplicarEfecto(p);
        assertEquals(2, e.duracion);
    }

    @Test
    @DisplayName("Caso límite DoT: la vida no baja de 0")
    void aplicarEfecto_dot_noDejaVidaNegativa() {
        Personaje p = crearPersonaje(100);
        p.recibirDaño(98); // queda con 2 HP
        Estado golpe = new Estado("Golpe", 50, 1, true); // daño mayor que la vida restante
        golpe.aplicarEfecto(p);
        assertEquals(0, p.getVidaActual(),
                "La vida no debe quedar negativa tras un DoT letal");
    }

    @Test
    @DisplayName("Caso límite HoT: la vida no supera vidaMax")
    void aplicarEfecto_hot_noSuperaVidaMax() {
        Personaje p = crearPersonaje(100);
        p.recibirDaño(5); // queda con 95 HP
        Estado regen = new Estado("Regen", 20, 1, false); // curaría 20 pero solo caben 5
        regen.aplicarEfecto(p);
        assertEquals(100, p.getVidaActual(),
                "La vida no debe superar vidaMax tras un HoT");
    }
}