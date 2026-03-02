package hechizos;

import personaje.Personaje;
import estado.Estado;

public class HechizoDot implements Hechizo {

    private String nombre;
    private int    coste;
    private String nombreEstado;
    private int    potencia;

    public HechizoDot(String nombre, int coste, String nombreEstado, int potencia) {
        this.nombre       = nombre;
        this.coste        = coste;
        this.nombreEstado = nombreEstado;
        this.potencia     = potencia;
    }

    
    public boolean lanzar(Personaje lanzador, Personaje objetivo) {
        if (lanzador.energia < coste) {
            System.out.println("    Sin energía para " + nombre);
            return false;
        }
        lanzador.energia -= coste;
        System.out.println("    " + lanzador.getNombre()
                + " infecta con [" + nombre + "] a " + objetivo.getNombre() + "!");
        objetivo.agregarEstado(new Estado(nombreEstado, potencia, 3, true));
        return true;
    }

     public boolean esOfensivo() { return true; }
     public String  getNombre()  { return nombre; }
}

