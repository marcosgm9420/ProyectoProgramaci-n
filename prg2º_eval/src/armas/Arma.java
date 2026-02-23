package armas;

public class Arma {
	private String  nombre;
    private int     danoBase;
    public  boolean esCuerpoACuerpo; // true = necesita estar cerca (Katana)
                                     // false = funciona a distancia (Pistola, Rifle)
    public Arma(String nombre, int danoBase, boolean esCuerpoACuerpo) {
        this.nombre           = nombre;
        this.danoBase         = danoBase;
        this.esCuerpoACuerpo  = esCuerpoACuerpo;
}
 // Calcula el daño según el tipo de arma
    public int calcularDano() {
        if (esCuerpoACuerpo) {
            return danoBase + 5; // Bono por el cuerpo a cuerpo
        } else {
            return danoBase;     // Daño a distancia es siempre el mismo no hay bonus.
        }
    }

    public String getNombre() {
        return nombre;
    }
}
