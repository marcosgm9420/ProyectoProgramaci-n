package armas;

public class Arma {
	private String  nombre;
    private int     dañoBase;
    public  boolean esCuerpoACuerpo; // true = necesita estar cerca (Katana)
                                     // false = funciona a distancia (Pistola, Rifle)
    public Arma(String nombre, int danoBase, boolean esCuerpoACuerpo) {
        this.nombre           = nombre;
        this.dañoBase         = danoBase;
        this.esCuerpoACuerpo  = esCuerpoACuerpo;
}
    
 // Calcula el daño según el tipo de arma
    public int calcularDaño() {
        if (esCuerpoACuerpo) {
            return dañoBase + 5; // Bono por el cuerpo a cuerpo
        } else 
       {
            return dañoBase;     // Daño a distancia es siempre el mismo no hay bonus.
        }
    }

    public String getNombre() {
        return nombre;
    }
}
