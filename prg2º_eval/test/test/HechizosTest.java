package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import personaje.Personaje;
import armas.Arma;
import hechizos.HechizoCuracion;
import hechizos.HechizoDañoDirecto;
import hechizos.HechizoHot;
import hechizos.HechizoDot;


/**
 * Tests para las 4 implementaciones de la interfaz Hechizo:
 *  - HechizoCuracion
 *  - HechizoDañoDirecto
 *  - HechizoDot
 *  - HechizoHot
 *
 * Casos cubiertos por cada hechizo:
 *  - lanzar() con energía suficiente → devuelve true y aplica el efecto
 *  - lanzar() sin energía → devuelve false y NO modifica al objetivo
 *  - lanzar() consume la energía correcta del lanzador
 *  - esOfensivo() devuelve el valor correcto según el tipo
 *  - getNombre() devuelve el nombre registrado
 */
@DisplayName("Tests de Hechizos")
class HechizosTest {

    private Personaje lanzador;
    private Personaje objetivo;

    @BeforeEach
    void setUp() {
        lanzador = new Personaje("Lanzador", 100, 50, new Arma("Rifle", 5, false));
        objetivo  = new Personaje("Objetivo",  80, 30, new Arma("Cuchillo", 5, true));
    }

    // HechizoCuracion

    @Test
    @DisplayName("Curación: lanzar con energía suficiente cura al objetivo")
    void curacion_conEnergia_cura() {
        objetivo.recibirDaño(30);   // objetivo queda a 50 HP
        HechizoCuracion h = new HechizoCuracion("Adrenalina", 20, 25);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertTrue(ok);
        assertEquals(75, objetivo.getVidaActual(),
                "El objetivo debe haber recibido 25 HP de curación");
    }

    @Test
    @DisplayName("Curación: lanzar sin energía devuelve false y no cura")
    void curacion_sinEnergia_noActua() {
        objetivo.recibirDaño(30);
        lanzador.setEnergia(5);     // energía insuficiente (coste = 20)
        HechizoCuracion h = new HechizoCuracion("Adrenalina", 20, 25);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertFalse(ok);
        assertEquals(50, objetivo.getVidaActual(),
                "Sin energía el objetivo no debe ser curado");
    }

    @Test
    @DisplayName("Curación: lanzar consume la energía correcta")
    void curacion_consumeEnergia() {
        HechizoCuracion h = new HechizoCuracion("Adrenalina", 20, 25);
        h.lanzar(lanzador, objetivo);
        assertEquals(30, lanzador.energia, "Deben quedar 30 de energía (50 - 20)");
    }

    @Test
    @DisplayName("Curación: esOfensivo devuelve false")
    void curacion_esOfensivo_false() {
        assertFalse(new HechizoCuracion("X", 10, 10).esOfensivo());
    }

    @Test
    @DisplayName("Curación: getNombre devuelve el nombre correcto")
    void curacion_getNombre() {
        assertEquals("Adrenalina", new HechizoCuracion("Adrenalina", 20, 25).getNombre());
    }

    // HechizoDañoDirecto

    @Test
    @DisplayName("DañoDirecto: lanzar con energía suficiente aplica daño inmediato")
    void danoDirecto_conEnergia_aplicaDano() {
        HechizoDañoDirecto h = new HechizoDañoDirecto("Sobrecarga", 20, 30);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertTrue(ok);
        assertEquals(50, objetivo.getVidaActual(),
                "El objetivo debe haber perdido exactamente 30 HP");
    }

    @Test
    @DisplayName("DañoDirecto: lanzar sin energía devuelve false y no aplica daño")
    void danoDirecto_sinEnergia_noActua() {
        lanzador.setEnergia(5);     // insuficiente
        HechizoDañoDirecto h = new HechizoDañoDirecto("Sobrecarga", 20, 30);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertFalse(ok);
        assertEquals(80, objetivo.getVidaActual());
    }

    @Test
    @DisplayName("DañoDirecto: lanzar consume la energía correcta")
    void danoDirecto_consumeEnergia() {
        HechizoDañoDirecto h = new HechizoDañoDirecto("Sobrecarga", 20, 30);
        h.lanzar(lanzador, objetivo);
        assertEquals(30, lanzador.energia);
    }

    @Test
    @DisplayName("DañoDirecto: esOfensivo devuelve true")
    void danoDirecto_esOfensivo_true() {
        assertTrue(new HechizoDañoDirecto("X", 10, 10).esOfensivo());
    }

    @Test
    @DisplayName("DañoDirecto: getNombre devuelve el nombre correcto")
    void danoDirecto_getNombre() {
        assertEquals("Sobrecarga", new HechizoDañoDirecto("Sobrecarga", 20, 30).getNombre());
    }

    // HechizoDot

    @Test
    @DisplayName("DoT: lanzar con energía agrega un estado de daño al objetivo")
    void dot_conEnergia_agregaEstado() {
        HechizoDot h = new HechizoDot("Virus", 10, "Virus", 10);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertTrue(ok);
        // El estado debe aparecer en la descripción del personaje
        assertTrue(objetivo.getEstado().contains("Virus"),
                "El estado DoT debe haberse aplicado al objetivo");
    }

    @Test
    @DisplayName("DoT: lanzar sin energía devuelve false y no aplica estado")
    void dot_sinEnergia_noActua() {
        lanzador.setEnergia(5);
        HechizoDot h = new HechizoDot("Virus", 10, "Virus", 10);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertFalse(ok);
        assertFalse(objetivo.getEstado().contains("Virus"),
                "Sin energía no debe aplicarse el estado");
    }

    @Test
    @DisplayName("DoT: lanzar consume la energía correcta")
    void dot_consumeEnergia() {
        HechizoDot h = new HechizoDot("Virus", 10, "Virus", 10);
        h.lanzar(lanzador, objetivo);
        assertEquals(40, lanzador.energia);
    }

    @Test
    @DisplayName("DoT: esOfensivo devuelve true")
    void dot_esOfensivo_true() {
        assertTrue(new HechizoDot("X", 10, "X", 5).esOfensivo());
    }

    @Test
    @DisplayName("DoT: getNombre devuelve el nombre correcto")
    void dot_getNombre() {
        assertEquals("Virus", new HechizoDot("Virus", 10, "V", 5).getNombre());
    }

    // HechizoHot

    @Test
    @DisplayName("HoT: lanzar con energía agrega un estado de curación al objetivo")
    void hot_conEnergia_agregaEstado() {
        HechizoHot h = new HechizoHot("Nanobots", 15, "Nanobots", 10);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertTrue(ok);
        assertTrue(objetivo.getEstado().contains("Nanobots"),
                "El estado HoT debe haberse aplicado al objetivo");
    }

    @Test
    @DisplayName("HoT: lanzar sin energía devuelve false y no aplica estado")
    void hot_sinEnergia_noActua() {
        lanzador.setEnergia(5);
        HechizoHot h = new HechizoHot("Nanobots", 15, "Nanobots", 10);
        boolean ok = h.lanzar(lanzador, objetivo);
        assertFalse(ok);
        assertFalse(objetivo.getEstado().contains("Nanobots"));
    }

    @Test
    @DisplayName("HoT: lanzar consume la energía correcta")
    void hot_consumeEnergia() {
        HechizoHot h = new HechizoHot("Nanobots", 15, "Nanobots", 10);
        h.lanzar(lanzador, objetivo);
        assertEquals(35, lanzador.energia);
    }

    @Test
    @DisplayName("HoT: esOfensivo devuelve false")
    void hot_esOfensivo_false() {
        assertFalse(new HechizoHot("X", 10, "X", 5).esOfensivo());
    }

    @Test
    @DisplayName("HoT: getNombre devuelve el nombre correcto")
    void hot_getNombre() {
        assertEquals("Nanobots", new HechizoHot("Nanobots", 15, "N", 10).getNombre());
    }
}