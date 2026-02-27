package personaje;

import java.util.ArrayList;

public class Mercenario extends personaje {

	private static final Random rd = new Random();

	public Mercenario(String nombre) {
		super(nombre, 150,50, new Arma("Katana", 15 true));
		
	}

public void actuar (ArrayList<personaje>enemigos, ArrayList<personaje> aliados) {
	
    estaCerca = rand.nextBoolean();

   if (estaCerca) {
	   System.out.println("   " + nombre " se lanzaa cuerpo a cuepro!" );
   }else {
	   System.out.println("   " + nombre + "no consigue acercarse al enemigo ");
   }
   
   
   for (Personaje p : enemigos) {
       if (p.estaVivo()) {
           boolean puedeUsarKatana = intentarAtacarConArma(p);
   
           if (!puedeUsarKatana) {
               // Está lejos: da un puñetazo básico sin arma (menos daño)
               System.out.println("    " + nombre
                       + " da un puñetazo a " + p.getNombre() + " -> 5 daño");
               p.recibirDano(5);
           }
           return;
   
       		}
   		}
	}
}

