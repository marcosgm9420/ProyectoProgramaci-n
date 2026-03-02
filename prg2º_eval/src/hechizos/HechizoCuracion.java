package hechizos;
import personaje.Personaje;


public class HechizoCuracion implements Hechizo{
	
	    private String nombre;
	    private int    coste;
	    private int    cantidad;

	    public HechizoCuracion(String nombre, int coste, int cantidad) {
	        this.nombre   = nombre;
	        this.coste    = coste;
	        this.cantidad = cantidad;
	    }

	    public boolean lanzar(Personaje lanzador, Personaje objetivo) {
	        if (lanzador.energia < coste) {
	            System.out.println("    Sin energía para " + nombre);
	            return false;
	        }
	        lanzador.energia -= coste;
	        System.out.println("    " + lanzador.getNombre()
	                + " aplica [" + nombre + "] a " + objetivo.getNombre() + "!");
	        objetivo.curar(cantidad);
	        return true;
	    }

	    public boolean esOfensivo() { return false; }
	    public String  getNombre()  { return nombre; 
	
	    
}}
