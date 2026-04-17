package hechizos;

import personaje.Personaje;


public class HechizoDañoDirecto implements Hechizo {

	    private String nombre;
	    private int    coste;
	    private int    daño;

	    public HechizoDañoDirecto(String nombre, int coste, int daño) {
	        this.nombre = nombre;
	        this.coste  = coste;
	        this.daño   = daño;
	    }

	    
	    public boolean lanzar(Personaje lanzador, Personaje objetivo) {
	        if (lanzador.energia < coste) {
	            System.out.println("    Sin energía para " + nombre);
	            return false;
	        }
	        lanzador.energia -= coste;
	        System.out.println("    " + lanzador.getNombre()
	                + " lanza [" + nombre + "] sobre " + objetivo.getNombre()
	                + " -> " + daño + " daño directo!");
	        objetivo.recibirDaño(daño);
	        return true;
	    }

	     public boolean esOfensivo() { return true; }
	     public String  getNombre()  { return nombre; }
	}


