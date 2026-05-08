package hechizos;

import personaje.Personaje;
import estado.Estado;

/**
 * Aplica un Estado que va curando al objetivo durante varios turnos.
 */
public class HechizoHot extends HechizosBase {

    private String nombreEstado;  // Nombre del efecto que se pega (ej. "Nanobots")
    private int    potencia;      // Curación por turno

    public HechizoHot(String nombre, int coste, String nombreEstado, int potencia) {
        super(nombre, coste);
        this.nombreEstado = nombreEstado;
        this.potencia     = potencia;
    }

    @Override
    protected void aplicarEfecto(Personaje lanzador, Personaje objetivo) {
        System.out.println("    " + lanzador.getNombre()
                + " activa [" + nombre + "] en " + objetivo.getNombre() + "!");
        // false = NO es daño (es HoT, cura). Dura 3 turnos.
        objetivo.agregarEstado(new Estado(nombreEstado, potencia, 3, false));
    }

    @Override
    public boolean esOfensivo() { return false; }
   
}