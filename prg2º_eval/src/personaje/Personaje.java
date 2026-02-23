package personaje;

import java.util.ArrayList;

import armas.Arma;
import estado.Estado;
import hechizos.Hechizo;
import personaje.Personaje;

public class Personaje {
	protected String nombre;
	protected int vidaActual;
	protected int vidaMax;
	public int energia;
	protected Arma arma;
    protected ArrayList<Estado>  estados;
    protected ArrayList<Hechizo> hechizo;
    // BOOLEAN DE PROXIMIDAD:
    // Cada turno se decide si el personaje está cerca o lejos del enemigo.
    // Solo los que están cerca pueden usar armas de cuerpo a cuerpo (Katana).
    protected boolean estaCerca;

    public Personaje(String nombre, int vida, int energia, Arma arma) {
        this.nombre     = nombre;
        this.vidaMax    = vida;
        this.vidaActual = vida;
        this.energia    = energia;
        this.arma       = arma;
        this.estados    = new ArrayList<>();
        this.hechizo   = new ArrayList<>();
        this.estaCerca  = false;
    }

    // Ataque con arma 
    // Comprueba el boolean antes de usar el arma
    public boolean intentarAtacarConArma(Personaje objetivo) {
        if (arma.esCuerpoACuerpo && !estaCerca) {
            // El arma es cuerpo a cuerpo pero el personaje está LEJOS
            System.out.println("    " + nombre + " está demasiado lejos para usar ["
                    + arma.getNombre() + "]!");
            return false; // No puede atacar con esta arma
        }
        // Puede usar el arma (está cerca O el arma es a distancia)
        int dano = arma.calcularDano();
        System.out.println("    " + nombre + " ataca con ["
                + arma.getNombre() + "] a " + objetivo.getNombre()
                + " -> " + dano + " daño");
        objetivo.recibirDano(dano);
        return true;
    }

    //Daño y curación 

    public void recibirDano(int cantidad) {
        vidaActual -= cantidad;
        if (vidaActual < 0) vidaActual = 0;
        System.out.println("      " + nombre + " HP: " + vidaActual + "/" + vidaMax);
    }

    public void curar(int cantidad) {
        vidaActual += cantidad;
        if (vidaActual > vidaMax) vidaActual = vidaMax;
        System.out.println("      " + nombre + " HP: " + vidaActual + "/" + vidaMax);
    }

    //Estados (DoT / HoT) 
    public void agregarEstado(Estado nuevo) {
        for (Estado e : estados) {
            if (e.nombre.equals(nuevo.nombre)) {
                e.duracion = nuevo.duracion;
                System.out.println("      Efecto '" + nuevo.nombre + "' renovado en " + nombre);
                return;
            }
        }
        estados.add(nuevo);
    }

    public void procesarEstados() {
        for (int i = estados.size() - 1; i >= 0; i--) {
            Estado e = estados.get(i);
            e.aplicarEfecto(this);
            if (e.haTerminado()) {
                System.out.println("      Efecto '" + e.nombre + "' terminó en " + nombre);
                estados.remove(i);
            }
        }
    }

    // Método que cada subclase sobreescribe 
    public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {
       
    }

   

    public boolean estaVivo()      { return vidaActual > 0; }
    public String  getNombre()     { return nombre; }
    public int     getVidaActual() { return vidaActual; }
    public int     getVidaMax()    { return vidaMax; }

    public String getEstado() {
        String efecto = estados.isEmpty() ? ""
                : " [" + estados.get(0).nombre + "]";
        String distancia = estaCerca ? "(cerca)" : "(lejos)";
        return String.format("%-20s HP: %3d/%-3d  EN: %3d  %s%s",
                nombre, vidaActual, vidaMax, energia, distancia, efecto);
    }
}
    
    