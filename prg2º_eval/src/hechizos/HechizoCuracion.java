package hechizos;

import personaje.Personaje;

public class HechizoCuracion extends HechizosBase {

    private int cantidad;   // Cuánta vida cura

    public HechizoCuracion(String nombre, int coste, int cantidad) {
        super(nombre, coste);     // Delegamos nombre y coste al padre
        this.cantidad = cantidad;
    }

    
     //Solo definimos el EFECTO específico: curar al objetivo.
     
     
    @Override
    protected void aplicarEfecto(Personaje lanzador, Personaje objetivo) {
        System.out.println("    " + lanzador.getNombre()
                + " aplica [" + nombre + "] a " + objetivo.getNombre() + "!");
        objetivo.curar(cantidad);
    }

    @Override
    public boolean esOfensivo() { return false; }   // Es de apoyo, no daña
}