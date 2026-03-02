package hechizos;

public class HechizoDoT extends Hechizo {
	package personajes;

	import armas.Arma;
	import hechizos.HechizoCuracion;
	import hechizos.HechizoHoT;
	import hechizos.HechizoDoT;

	import java.util.ArrayList;
	import java.util.Random;

	public class Doctor extends Personaje {

	    private static final Random rand = new Random();

	    public BioDoc(String nombre) {
	        super(nombre, 100, 80, new Arma("Rifle Biótico", 8, false));
	        // false = NO es cuerpo a cuerpo → siempre puede usarlo

	        estaCerca = false; // El BioDoc siempre se mantiene a distancia

	        hechizos.add(new HechizoCuracion("Adrenalina", 20, 25));
	        hechizos.add(new HechizoHoT("Nanobots", 15, "Nanobots", 10));
	        hechizos.add(new HechizoDoT("Descarga EMP", 10, "Virus", 10));
	    }

	    @Override
	    public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {
	        // Busca al aliado con menos vida
	        Personaje masHerido = null;
	        for (Personaje a : aliados) {
	            if (a.estaVivo() && a.getVidaActual() < a.getVidaMax()) {
	                if (masHerido == null || a.getVidaActual() < masHerido.getVidaActual()) {
	                    masHerido = a;
	                }
	            }
	        }
}
