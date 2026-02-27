package personaje;

import java.util.ArrayList;
import java.util.Random;

import armas.Arma;

public class Netrunner extends personaje {
	
	private static final Random rd = new Random();
	public Netrunner(String nombre) {
        super(nombre, 80, 100, new Arma("Pistola Taser", 5, false));
        
        
        estaCerca = false; // El Netrunner siempre pelea a distancia

        hechizos.add(new HechizoDanoDirecto("Hack: Sobrecarga", 20, 30));
        hechizos.add(new HechizoDoT("Hack: Virus", 15, "Virus", 10));
    }

	
	 public void actuar(ArrayList<personaje> enemigos, ArrayList<personaje> aliados) {
	        personaje objetivo = null;
	        for (personaje p : enemigos) {
	            if (p.estaVivo()) { objetivo = p; break; }
	        }
	        if (objetivo == null) return;
	        
	        int indice = rand.nextBoolean() ? 0 : 1;
	        boolean ok = hechizos.get(indice).lanzar(this, objetivo);

	        if (!ok) {
	            intentarAtacarConArma(objetivo);
	        }
	
}}
