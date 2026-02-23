package estado;

import personaje.Personaje;

public class Estado {
	public String  nombre;
    public int     potencia;  // Cuánto daña o cura por turno
    public int     duracion;  // Turnos que le quedan
    public boolean esDano;    // true = DoT / false = HoT

    public Estado(String nombre, int potencia, int duracion, boolean esDano) {
        this.nombre   = nombre;
        this.potencia = potencia;
        this.duracion = duracion;
        this.esDano   = esDano;
    }

    // Aplica el efecto al personaje y descuenta un turno
    public void aplicarEfecto(Personaje objetivo) {
        if (esDano) {
            System.out.println("    [" + nombre + "] daña a "
                    + objetivo.getNombre() + " por " + potencia + " HP");
            objetivo.recibirDano(potencia);
        } else {
            System.out.println("    [" + nombre + "] cura a "
                    + objetivo.getNombre() + " por " + potencia + " HP");
            objetivo.curar(potencia);
        }
        duracion--;
    }

    public boolean haTerminado() {
        return duracion <= 0;
    }
}
	


