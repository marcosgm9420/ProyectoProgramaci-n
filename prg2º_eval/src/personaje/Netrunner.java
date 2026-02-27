package personaje;

import java.util.ArrayList;
import java.util.Random;

import armas.Arma;
import hechizos.HechizoDanoDirecto;
import hechizos.HechizoDoT;

public class Netrunner extends Personaje {
	
	private static final Random rd = new Random();
	public Netrunner(String nombre) {
        super(nombre, 80, 100, new Arma("Pistola Taser", 5, false));
        // false = NO es cuerpo a cuerpo → siempre puede usarla

        
        estaCerca = false; // El Netrunner siempre pelea a distancia

        hechizo.add(new HechizoDanoDirecto());
        hechizo.add(new HechizoDoT());
    }

	
	 public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {
	        Personaje objetivo = null;
	        for (Personaje p : enemigos) {
	            if (p.estaVivo()) { objetivo = p; break; }
	        }
	        if (objetivo == null) return;
	        
	        int indice = rand.nextBoolean() ? 0 : 1;
	        boolean ok = Hechizo.get(indice).lanzar(this, objetivo);

	        if (!ok) {
	            intentarAtacarConArma(objetivo);
	        }
	
}}
