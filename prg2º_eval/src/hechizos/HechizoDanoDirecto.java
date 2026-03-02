package hechizos;

import personaje.Personaje;

public class HechizoDanoDirecto implements Hechizo {

	    private String nombre;
	    private int    coste;
	    private int    dano;

	    public HechizoDanoDirecto(String nombre, int coste, int dano) {
	        this.nombre = nombre;
	        this.coste  = coste;
	        this.dano   = dano;
	    }

	    
	    public boolean lanzar(Personaje lanzador, Personaje objetivo) {
	        if (lanzador.energia < coste) {
	            System.out.println("    Sin energía para " + nombre);
	            return false;
	        }
	        lanzador.energia -= coste;
	        System.out.println("    " + lanzador.getNombre()
	                + " lanza [" + nombre + "] sobre " + objetivo.getNombre()
	                + " -> " + dano + " daño directo!");
	        objetivo.recibirDano(dano);
	        return true;
	    }

	     public boolean esOfensivo() { return true; }
	     public String  getNombre()  { return nombre; }
	}


