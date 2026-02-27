package hechizos;

	import personaje.Personaje;

	public interface Hechizo {

	    // Lanza el hechizo sobre el objetivo
	    boolean lanzar(Personaje lanzador, Personaje objetivo);

	    // Para saber si el hechizo daña o cura (la IA lo usa para elegir objetivo)
	    boolean esOfensivo();

	    // Para mostrar el nombre en pantalla
	    String getNombre();
	}

