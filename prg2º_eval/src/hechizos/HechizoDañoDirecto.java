package hechizos;

import personaje.Personaje;

public class HechizoDañoDirecto extends HechizosBase {

    private int daño;   // Daño fijo que aplica

    public HechizoDañoDirecto(String nombre, int coste, int daño) {
        super(nombre, coste);
        this.daño = daño;
    }

    
    // Efecto específico: aplicar daño directo al objetivo.
     
    @Override
    protected void aplicarEfecto(Personaje lanzador, Personaje objetivo) {
        System.out.println("    " + lanzador.getNombre()
                + " lanza [" + nombre + "] sobre " + objetivo.getNombre()
                + " -> " + daño + " daño directo!");
        objetivo.recibirDaño(daño);
    }

    @Override
    public boolean esOfensivo() { return true; }   // Sí daña al enemigo
}