package personaje;

import armas.Arma;
import hechizos.HechizoCuracion;
import hechizos.HechizoHot;
import hechizos.HechizoDot;
import java.util.ArrayList;
import java.util.Random;

public class Doctor extends Personaje {

    private static final Random rand = new Random();

    public Doctor(String nombre) {
        super(nombre, 100, 80, new Arma("Rifle Biótico", 8, false));
        // false = NO es cuerpo a cuerpo → siempre puede usarlo

        estaCerca = false; // El BioDoc siempre se mantiene a distancia

        hechizo.add(new HechizoCuracion("Adrenalina", 20, 25));
        hechizo.add(new HechizoHot("Nanobots", 15, "Nanobots", 10));
        hechizo.add(new HechizoDot("Descarga EMP", 10, "Virus", 10));
    }

    
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

        if (masHerido != null && energia >= 15) {
            int indice = rand.nextBoolean() ? 0 : 1;
            hechizo.get(indice).lanzar(this, masHerido);
        } else {
            for (Personaje p : enemigos) {
                if (p.estaVivo()) {
                    boolean ok = hechizo.get(2).lanzar(this, p);
                    if (!ok) intentarAtacarConArma(p); // Rifle, siempre se puede usarlo
                    return;
                }}
        }}
}
