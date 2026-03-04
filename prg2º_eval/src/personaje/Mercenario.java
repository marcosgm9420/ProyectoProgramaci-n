package personaje;

import armas.Arma;
import java.util.ArrayList;
import java.util.Random;

public class Mercenario extends Personaje {

    private static final Random rand = new Random();

    public Mercenario(String nombre) {
        super(nombre, 150, 50, new Arma("Katana Vibratoria", 15, true));
        // true = esCuerpoACuerpo → necesita estar cerca para usarla
    }

    
    public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {
        // Cada turno decide aleatoriamente si logra acercarse al enemigo
        estaCerca = rand.nextBoolean();

        if (estaCerca) {
            System.out.println("    " + nombre + " se lanza al cuerpo a cuerpo!");
        } else {
            System.out.println("    " + nombre + " no consigue acercarse al enemigo.");
        }

        // Busca al primer enemigo vivo
        for (Personaje p : enemigos) {
            if (p.estaVivo()) {
                // Intenta atacar con la Katana — el boolean decide si puede o no 
                boolean puedeUsarKatana = intentarAtacarConArma(p);

                if (!puedeUsarKatana) {
                    // Está lejos: da un puñetazo básico sin arma (menos daño)
                    System.out.println("    " + nombre
                            + " da un puñetazo a " + p.getNombre() + " -> 5 daño");
                    p.recibirDaño(5);
                }
                return;
            }
        }
    }
}

