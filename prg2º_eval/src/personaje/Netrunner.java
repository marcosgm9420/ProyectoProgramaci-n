package personaje;
 
import armas.Arma;
import hechizos.HechizoDañoDirecto;
import hechizos.HechizoDot;
import java.util.ArrayList;
import java.util.Random;
 
public class Netrunner extends Personaje {
 
    private static final Random rand = new Random();
 
    // Arma secundaria: cuerpo a cuerpo, por si alguna vez está cerca
    private Arma armaSecundaria = new Arma("Cuchillo Eléctrico", 10, true);
 
    public Netrunner(String nombre) {
        super(nombre, 80, 100, new Arma("Pistola Taser", 5, false));
        // false = NO es cuerpo a cuerpo → siempre puede usarla
 
        estaCerca = false; // El Netrunner siempre pelea a distancia
 
        hechizo.add(new HechizoDañoDirecto("Hack: Sobrecarga", 20, 30));
        hechizo.add(new HechizoDot("Hack: Virus", 15, "Virus", 10));
    }
 
    
    public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {
        Personaje objetivo = null;
        for (Personaje p : enemigos) {
            if (p.estaVivo()) { objetivo = p; break; }
        }
        if (objetivo == null) return;
 
        // Elige entre sus dos hechizos aleatoriamente
        int indice = rand.nextBoolean() ? 0 : 1;
        boolean ok = lanzarHechizo(hechizo.get(indice), objetivo); 
        if (!ok) {
            // Sin energía: usa la pistola (siempre puede, está a distancia)
            intentarAtacarConArma(objetivo);
        }
    }


	public Arma getArmaSecundaria() {
		return armaSecundaria;
	}


	public void setArmaSecundaria(Arma armaSecundaria) {
		this.armaSecundaria = armaSecundaria;
	}
}