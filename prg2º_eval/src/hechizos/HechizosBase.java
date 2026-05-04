package hechizos;

import hechizos.Hechizo;
import personaje.Personaje;

/**
 * Clase ABSTRACTA que reúne el comportamiento común de TODOS los hechizos.
 *
 *   - Guardar el nombre y el coste
 *   - Comprobar si el lanzador tiene energía suficiente
 *   - Restar la energía gastada
 *   - Implementar getNombre()
 */
public abstract class HechizosBase implements Hechizo {

    // Atributos comunes a todos los hechizos 
    protected String nombre;   // Nombre que se muestra en pantalla
    protected int    coste;    // Energía que gasta al lanzarse

    // Constructor común: cualquier hechizo necesita al menos nombre y coste
    public HechizosBase(String nombre, int coste) {
        this.nombre = nombre;
        this.coste  = coste;
    }

    /**
     * el esqueleto que siguen TODOS
     * los hechizos:
     *¿Tiene energía el lanzador?  si no, se aborta.
     * Se cobra el coste de energía.
     * Se delega en aplicarEfecto() para que cada subclase haga lo suyo.
     * Las subclases NO sobreescriben este método. Solo rellenan aplicarEfecto().
     *      */
    @Override
    public boolean lanzar(Personaje lanzador, Personaje objetivo) {
        // 1. Comprobar energía
        if (lanzador.energia < coste) {
            System.out.println("    Sin energía para " + nombre);
            return false;
        }

        // 2. Cobrar el coste de energía
        lanzador.energia -= coste;

        // 3. Cada subclase aplica su efecto específico
        aplicarEfecto(lanzador, objetivo);
        return true;
    }

    /**
     * Método ABSTRACTO. Cada hechizo concreto decide qué efecto produce:
     * curar, dañar, infectar con un DoT, regenerar con un HoT
     * Aquí queda explicado quien lo lanza y quien lo recibe 
     */
    protected abstract void aplicarEfecto(Personaje lanzador, Personaje objetivo);

    // El nombre se gestiona aquí una sola vez para todos los hechizos
    @Override
    public String getNombre() {
        return nombre;
    }

    // esOfensivo() sigue siendo abstracto: cada hechizo dice si daña o cura
    @Override
    public abstract boolean esOfensivo();
}

