package hechizos;

import personaje.Personaje;
import estado.Estado;

public class HechizoHot implements Hechizo {

	    private String nombre;
	    private int    coste;
	    private String nombreEstado;
	    private int    potencia;


	    public HechizoHot(String nombre, int coste, String nombreEstado, int potencia) {
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
	                + " activa [" + nombre + "] en " + objetivo.getNombre() + "!");
	        pause(1500);
	        objetivo.agregarEstado(new Estado(nombreEstado, potencia, 3, false));
	        return true;
	    }

	    private void pause(int i) {			
		}

		 public boolean esOfensivo() { return false; }
	     public String  getNombre()  { return nombre; }
	}


