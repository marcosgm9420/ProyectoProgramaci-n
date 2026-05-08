package hechizos;

import personaje.Personaje;
import estado.Estado;

/**
 * Aplica un Estado que va dañando al objetivo durante varios turnos.
 */
public class HechizoDot extends HechizosBase {

    private String nombreEstado;  // Nombre del efecto que se pega (ej. "Virus")
    private int    potencia;      // Daño por turno

    public HechizoDot(String nombre, int coste, String nombreEstado, int potencia) {
        super(nombre, coste);
        this.nombreEstado = nombreEstado;
        this.potencia     = potencia;
    }

    @Override
    protected void aplicarEfecto(Personaje lanzador, Personaje objetivo) {
        System.out.println("    " + lanzador.getNombre()
                + " infecta con [" + nombre + "] a " + objetivo.getNombre() + "!");
        // true = es daño (DoT). Dura 3 turnos.
        objetivo.agregarEstado(new Estado(nombreEstado, potencia, 3, true));
    }

    @Override
    public boolean esOfensivo() { return true; }
}