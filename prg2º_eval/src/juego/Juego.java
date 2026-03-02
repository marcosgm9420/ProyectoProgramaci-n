package juego;

import personaje.Personaje;
import personaje.Mercenario;
import personaje.Netrunner;
import personaje.Doctor;
import java.util.ArrayList;

public class Juego {

    private ArrayList<Personaje> tuEquipo;
    private ArrayList<Personaje> enemigos;

    public Juego() {
        tuEquipo = new ArrayList<>();
        tuEquipo.add(new Mercenario("Rex"));
        tuEquipo.add(new Netrunner("Zara"));
        tuEquipo.add(new Doctor("Patch"));

        enemigos = new ArrayList<>();
        enemigos.add(new Mercenario("Security-Droid"));
        enemigos.add(new Netrunner("Black-Hat AI"));
        enemigos.add(new Doctor("Drone-Reparador"));
    }

    public void mostrarEquipos() {
        System.out.println("\n   TU ESCUADRON:");
        for (Personaje p : tuEquipo)
            System.out.println("   + " + p.getEstado());
        System.out.println("\n   ENEMIGOS:");
        for (Personaje p : enemigos)
            System.out.println("   - " + p.getEstado());
    }{} 

    public void iniciar() throws InterruptedException {
        int ronda = 1;

        while (true) {
            System.out.println("\n");
            linea('=', 60);
            System.out.println("   RONDA " + ronda);
            linea('=', 60);

            System.out.println("   TU EQUIPO:");
            for (Personaje p : tuEquipo)
                System.out.println("   " + (p.estaVivo() ? "  " : "X ") + p.getEstado());
            System.out.println("   ENEMIGOS:");
            for (Personaje p : enemigos)
                System.out.println("   " + (p.estaVivo() ? "  " : "X ") + p.getEstado());
            

	private void linea(char c, int i) {		
	}}