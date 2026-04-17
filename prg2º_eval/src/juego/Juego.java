package juego;

import personaje.Personaje;
import personaje.Mercenario;
import personaje.Netrunner;
import personaje.Doctor;
import basededatos.GestorBD;
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
        enemigos.add(new Netrunner("Black-Hat IA"));
        enemigos.add(new Doctor("Drone-Reparador"));
    }

    // Guarda los personajes en la BD al crear la partida
    public void guardarPersonajesIniciales(int idPartida) {
        for (Personaje p : tuEquipo) {
            GestorBD.guardarPersonaje(idPartida, p.getNombre(), p.getClass().getSimpleName(), p.getVidaActual(), p.getVidaMax(), p.energia, true);
        }
        for (Personaje p : enemigos) {
            GestorBD.guardarPersonaje(idPartida, p.getNombre(), p.getClass().getSimpleName(), p.getVidaActual(), p.getVidaMax(), p.energia, false);
        }
    }

    // Sobrescribe los equipos iniciales con los de la base de datos
    public void cargarDesdeBD(int idPartida) {
        this.tuEquipo = GestorBD.cargarPersonajesPorBando(idPartida, true);
        this.enemigos = GestorBD.cargarPersonajesPorBando(idPartida, false);
    }

    public void mostrarEquipos() {
        System.out.println("\n   TU ESCUADRON:");
        for (Personaje p : tuEquipo) System.out.println("   + " + p.getEstado());
        System.out.println("\n   ENEMIGOS:");
        for (Personaje p : enemigos) System.out.println("   - " + p.getEstado());
    }

    // Bucle principal de combate
    public void iniciar(int idPartida) throws InterruptedException {
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

            // ── Turno de tu equipo ───────────────────────────────
            System.out.println("\n  --- Turno de tu equipo ---");
            for (Personaje heroe : tuEquipo) {
                if (!heroe.estaVivo()) continue;
                System.out.println("\n  >> " + heroe.getNombre());
                heroe.procesarEstados();
                if (heroe.estaVivo()) heroe.actuar(enemigos, tuEquipo);
                Thread.sleep(600);
                if (todosDerrota(enemigos)) break;
            }

            if (todosDerrota(enemigos)) {
                linea('=', 60);
                System.out.println("   MISION CUMPLIDA. Enemigos neutralizados!");
                linea('=', 60);
                break;
            }

            // ── Turno enemigo ────────────────────────────────────
            System.out.println("\n  --- Turno enemigo ---");
            for (Personaje enemigo : enemigos) {
                if (!enemigo.estaVivo()) continue;
                System.out.println("\n  >> " + enemigo.getNombre());
                enemigo.procesarEstados();
                if (enemigo.estaVivo()) enemigo.actuar(tuEquipo, enemigos);
                Thread.sleep(600);
                if (todosDerrota(tuEquipo)) break;
            }

            if (todosDerrota(tuEquipo)) {
                linea('=', 60);
                System.out.println("   DERROTA. Tu escuadron ha caido.");
                linea('=', 60);
                break;
            }

            // ── AUTOGUARDADO ─────────────────────────────────────
            System.out.println("\n   [Guardando progreso de la ronda " + ronda + " automáticamente en MySQL...]");
            for (Personaje p : tuEquipo) {
                GestorBD.actualizarPersonaje(idPartida, p.getNombre(), p.getVidaActual(), p.energia);
            }
            for (Personaje p : enemigos) {
                GestorBD.actualizarPersonaje(idPartida, p.getNombre(), p.getVidaActual(), p.energia);
            }

            ronda++;
            Thread.sleep(800);
        }
    }

    private boolean todosDerrota(ArrayList<Personaje> equipo) {
        for (Personaje p : equipo) {
            if (p.estaVivo()) return false;
        }
        return true;
    }

    private void linea(char c, int n) {
        System.out.println(String.valueOf(c).repeat(n));
    }
}