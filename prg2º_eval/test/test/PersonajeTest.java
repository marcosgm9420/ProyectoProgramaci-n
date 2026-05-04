package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import armas.Arma;
import estado.Estado;
import personaje.Personaje;

/**
 * Tests para la clase Personaje.
 *
 * Casos cubiertos:
 *  - estaVivo() según la vida actual
 *  - recibirDaño() descuenta HP correctamente y no baja de 0
 *  - curar() suma HP correctamente y no supera vidaMax
 *  - agregarEstado() apila estados nuevos
 *  - agregarEstado() renueva (no duplica) un estado ya activo
 *  - intentarAtacarConArma() con arma CaC estando lejos → false, sin daño
 *  - intentarAtacarConArma() con arma CaC estando cerca → true, aplica daño
 *  - intentarAtacarConArma() con arma a distancia siempre ataca → true
 *  - setVidaActual() respeta los límites [0, vidaMax]
 */
@DisplayName("Tests de Personaje")
class PersonajeTest {

    private Personaje atacante;
    private Personaje objetivo;

    @BeforeEach
    void setUp() {
        // Arma CaC para atacante, arma distancia para objetivo
        atacante = new Personaje("Atacante", 100, 50, new Arma("Katana", 15, true));
        objetivo  = new Personaje("Objetivo",  80, 30, new Arma("Pistola", 5, false));
    }

    // ── estaVivo ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("estaVivo devuelve true con vida > 0")
    void estaVivo_conVida_true() {
        assertTrue(atacante.estaVivo());
    }

    @Test
    @DisplayName("estaVivo devuelve false cuando vida llega a 0")
    void estaVivo_sinVida_false() {
        atacante.recibirDaño(100);
        assertFalse(atacante.estaVivo());
    }

    // recibirDaño 

    @Test
    @DisplayName("recibirDaño descuenta exactamente la cantidad recibida")
    void recibirDano_restaCorrectamente() {
        objetivo.recibirDaño(20);
        assertEquals(60, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("recibirDaño no deja la vida por debajo de 0 (caso límite)")
    void recibirDano_noDejaVidaNegativa() {
        objetivo.recibirDaño(9999);
        assertEquals(0, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("recibirDaño con 0 no modifica la vida")
    void recibirDano_cero_noModifica() {
        objetivo.recibirDaño(0);
        assertEquals(80, objetivo.getVidaActual());
    }

    //curar 

    @Test
    @DisplayName("curar suma HP correctamente")
    void curar_sumaCorrectamente() {
        objetivo.recibirDaño(30);   // queda a 50 HP
        objetivo.curar(10);
        assertEquals(60, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("curar no supera vidaMax (caso límite)")
    void curar_noSuperaVidaMax() {
        objetivo.recibirDaño(5);    // queda a 75 HP
        objetivo.curar(100);        // intenta curar más de lo que falta
        assertEquals(80, objetivo.getVidaActual(),
                "La vida no debe superar el máximo al curar");
    }

    @Test
    @DisplayName("curar con 0 no modifica la vida")
    void curar_cero_noModifica() {
        objetivo.recibirDaño(20);
        int vidaAntes = objetivo.getVidaActual();
        objetivo.curar(0);
        assertEquals(vidaAntes, objetivo.getVidaActual());
    }

    //  agregarEstado 

    @Test
    @DisplayName("agregarEstado añade un estado nuevo a la lista")
    void agregarEstado_estadoNuevo_seAnade() {
        Estado veneno = new Estado("Veneno", 10, 3, true);
        atacante.agregarEstado(veneno);
        // Verificamos que el estado se muestra en getEstado()
        assertTrue(atacante.getEstado().contains("Veneno"));
    }

    @Test
    @DisplayName("agregarEstado renueva la duración si el estado ya existe (no duplica)")
    void agregarEstado_mismoNombre_renovaDuracion() {
        Estado v1 = new Estado("Veneno", 10, 1, true);
        Estado v2 = new Estado("Veneno", 10, 3, true);
        atacante.agregarEstado(v1);
        atacante.agregarEstado(v2); // debe renovar, no añadir otro
        // Solo debería haber un estado con duracion = 3
        assertEquals(3, v1.duracion,
                "La duración del estado existente debe renovarse");
    }


    @Test
    @DisplayName("Arma CaC + lejos → devuelve false y no aplica daño")
    void intentarAtacar_cacLejos_noAtaca() {
        // estaCerca = false por defecto en el constructor de Personaje
        boolean resultado = atacante.intentarAtacarConArma(objetivo);
        assertFalse(resultado, "No debe poder atacar con arma CaC estando lejos");
        assertEquals(80, objetivo.getVidaActual(), "El objetivo no debe recibir daño");
    }

    @Test
    @DisplayName("Arma CaC + cerca → devuelve true y aplica daño")
    void intentarAtacar_cacCerca_atacaCorrectamente() {
        // Forzamos estaCerca a true via setVidaActual no funciona, necesitamos acceso
        // Usamos Mercenario que tiene lógica de cerca, o accedemos al campo protegido
        // Como estaCerca es protected, creamos subclase anónima inline:
        Personaje guerrero = new Personaje("Guerrero", 100, 50, new Arma("Hacha", 10, true)) {
            { this.estaCerca = true; }
        };
        int vidaAntes = objetivo.getVidaActual();
        boolean resultado = guerrero.intentarAtacarConArma(objetivo);
        assertTrue(resultado);
        assertTrue(objetivo.getVidaActual() < vidaAntes,
                "El objetivo debe haber recibido daño");
    }

    @Test
    @DisplayName("Arma a distancia siempre puede atacar (lejos o cerca)")
    void intentarAtacar_distancia_siempreAtaca() {
        Personaje tirador = new Personaje("Tirador", 100, 50, new Arma("Pistola", 8, false));
        // estaCerca = false por defecto → aun así debe poder atacar
        boolean resultado = tirador.intentarAtacarConArma(objetivo);
        assertTrue(resultado, "Un arma a distancia siempre debe poder atacar");
        assertEquals(72, objetivo.getVidaActual(), "Debe hacer exactamente 8 de daño");
    }


    @Test
    @DisplayName("setVidaActual no permite valores negativos")
    void setVidaActual_negativo_quedarEnCero() {
        objetivo.setVidaActual(-50);
        assertEquals(0, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("setVidaActual no permite superar vidaMax")
    void setVidaActual_superaMax_quedarEnMax() {
        objetivo.setVidaActual(9999);
        assertEquals(80, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("setVidaActual asigna un valor válido correctamente")
    void setVidaActual_valorValido_seAsigna() {
        objetivo.setVidaActual(50);
        assertEquals(50, objetivo.getVidaActual());
    }
}