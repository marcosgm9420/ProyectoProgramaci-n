package personaje;

import java.util.ArrayList;
import java.util.Random;

public abstract class Doctor extends personaje {

	private static final Random rd = new Random();

	public Doctor(String nombre) {

		super(nombre, 100, 80, new Arma("Rifle Biótico", 8, false));

		estaCerca = false;

		hechizos.add(new HechizoCuracion("Adrenalina", 20, 25));
		hechizos.add(new HechizoFuego("Nanobots", 15, "Nanobots", 10));
		hechizos.add(new HechizoRayo("Descarga Electrica", 10, "Virus", 10));

	}

	public abstract void actuar(ArrayList<personaje> enemigos, ArrayList<personaje> aliados);

	personaje masHerido = null;for(
	personaje a:aliados)
	{
		if (a.estaVivo() && a.getVidaActual() < a.getVidaMax()) {
			if (masHerido == null || a.getVidaActual() < masHerido.getVidaActual()) {

			}
		}
	}

	if(masHerido!=null&&energia>=15)
	{
		int indice = rd.nextBoolean() ? 0 : 1;
		hechizos.get(indice).lanzar(this, masHerido);
	}else
	{
		for (Personaje p : enemigos) {
			if (p.estaVivo()) {
				boolean ok = hechizoa.get(2).lanzar(this, p);
				if (!ok)
					intentarAtacarConArma(p);
				}
			}
		}
	}
}
