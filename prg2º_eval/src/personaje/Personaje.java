package personaje;
 
import java.util.ArrayList;
import armas.Arma;
import estado.Estado;
import hechizos.Hechizo;
 
public class Personaje {
    protected String nombre;
    protected int vidaActual;
    protected int vidaMax;
    public int energia;
    protected Arma arma;
    protected ArrayList<Estado>  estados;
    protected ArrayList<Hechizo> hechizo;
    protected boolean estaCerca;
 
    public Personaje(String nombre, int vida, int energia, Arma arma) {
        this.nombre     = nombre;
        this.vidaMax    = vida;
        this.vidaActual = vida;
        this.energia    = energia;
        this.arma       = arma;
        this.estados    = new ArrayList<>();
        this.hechizo    = new ArrayList<>();
        this.estaCerca  = false;
    }
 
    public boolean intentarAtacarConArma(Personaje objetivo) {
        if (arma.esCuerpoACuerpo && !estaCerca) {
            System.out.println("    " + nombre + " está demasiado lejos para usar ["
                    + arma.getNombre() + "]!");
            return false;
        }
    
        int daño = arma.calcularDaño();
        System.out.println("    " + nombre + " ataca con ["
                + arma.getNombre() + "] a " + objetivo.getNombre()
                + " -> " + daño + " daño");
        objetivo.recibirDaño(daño);
        return true;
    }
    /**
     * Lanza un hechizo desde ESTE personaje (this = lanzador) hacia un objetivo.
     *
     * Es el equivalente, para hechizos, de intentarAtacarConArma():
     *   - intentarAtacarConArma(objetivo)  -> ataque físico
     *   - lanzarHechizo(h, objetivo)       -> ataque/curación con magia
     *
     * Así queda claro QUIÉN LANZA y QUIÉN RECIBE el hechizo, en lugar
     * de tener que escribir cada vez "hechizo.get(i).lanzar(this, p)".
     */
    public boolean lanzarHechizo(Hechizo h, Personaje objetivo) {
        // this -> el personaje que lanza el hechizo
        // objetivo -> el personaje que recibe el efecto
        return h.lanzar(this, objetivo);
    }
 
    public void recibirDaño(int cantidad) {
        vidaActual -= cantidad;
        if (vidaActual < 0) vidaActual = 0;
        System.out.println("      " + nombre + " HP: " + vidaActual + "/" + vidaMax);
    }
 
    public void curar(int cantidad) {
        vidaActual += cantidad;
        if (vidaActual > vidaMax) vidaActual = vidaMax;
        System.out.println("      " + nombre + " HP: " + vidaActual + "/" + vidaMax);
    }
 
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
 
    public void actuar(ArrayList<Personaje> enemigos, ArrayList<Personaje> aliados) {}
 
    //  GETTERS 
    public boolean estaVivo()      { return vidaActual > 0; }
    public String  getNombre()     { return nombre; }
    public int     getVidaActual() { return vidaActual; }
    public int     getVidaMax()    { return vidaMax; }
 
    //  SETTERS (necesarios para reconstruir personajes desde la BD) 
    public void setVidaActual(int vidaActual) {
        this.vidaActual = Math.max(0, Math.min(vidaActual, vidaMax));
    }
 
    public void setEnergia(int energia) {
        this.energia = energia;
    }
 
    public String getEstado() {
        String efecto = estados.isEmpty() ? ""
                : " [" + estados.get(0).nombre + "]";
        String distancia = estaCerca ? "(cerca)" : "(lejos)";
        return String.format("%-20s HP: %3d/%-3d  EN: %3d  %s%s",
                nombre, vidaActual, vidaMax, energia, distancia, efecto);
    }
}
 