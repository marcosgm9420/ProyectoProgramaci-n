package personaje;
 
import armas.Arma;
import java.util.ArrayList;
import java.util.Random;
 
public class Mercenario extends Personaje {
 
    private static final Random rand = new Random();
 
    // Arma secundaria: a distancia, para cuando no logra acercarse
    private Arma armaSecundaria = new Arma("Pistola de Reserva", 8, false);
 
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
                    // Está lejos: saca la Pistola de Reserva
                    int daño = armaSecundaria.calcularDaño();
                    System.out.println("    " + nombre + " saca la ["
                            + armaSecundaria.getNombre() + "] y dispara a "
                            + p.getNombre() + " -> " + daño + " daño");
                    p.recibirDaño(daño);
                }
                return;
            }
        }
    }
}

