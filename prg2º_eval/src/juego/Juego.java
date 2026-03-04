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
		
	}

	public void iniciar() {
		
	}}
    
        