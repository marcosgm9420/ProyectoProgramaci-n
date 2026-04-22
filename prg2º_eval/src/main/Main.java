package main;
 
import java.util.Scanner;
import basededatos.GestorBD;
import juego.Juego;
 
public class Main {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
 
        while (opcion != 4) {
            System.out.println("\n=== MENÚ PRINCIPAL RPG ===");
            System.out.println("1. Nueva Partida");
            System.out.println("2. Cargar Partida");
            System.out.println("3. Borrar Partida");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
 
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer
 
            if (opcion == 1) {
                System.out.print("Introduce tu nombre de jugador: ");
                String nombre = sc.nextLine();
 
                // 1. Crear la partida en BD y obtener su ID
                int idPartida = GestorBD.crearNuevaPartida(nombre);
                System.out.println("[OK] Partida creada con ID: " + idPartida);
 
                // 2. Crear el juego con personajes por defecto y guardarlos en BD
                Juego miJuego = new Juego();
                miJuego.guardarPersonajesIniciales(idPartida);
 
                // 3. Arrancar el combate
                try {
                    miJuego.iniciar(idPartida);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
 
            } else if (opcion == 2) {
              
                System.out.println("\nPartidas disponibles:");
                GestorBD.listarPartidas();
                System.out.print("Introduce el ID de la partida que quieres cargar: ");
                int idCargar = sc.nextInt();
                sc.nextLine();
 
                // Crear el juego y sobrescribir sus equipos con los datos de la BD
                Juego miJuego = new Juego();
                miJuego.cargarDesdeBD(idCargar);
 
                // Continuar el combate desde el estado guardado
                try {
                    miJuego.iniciar(idCargar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
 
            } else if (opcion == 3) {
                GestorBD.listarPartidas();
                System.out.print("Introduce el ID de la partida que quieres borrar: ");
                int idBorrar = sc.nextInt();
                sc.nextLine();
                GestorBD.borrarPartida(idBorrar);
 
            } else if (opcion == 4) {
                System.out.println("Saliendo del juego...");
 
            } else {
                System.out.println("Opción no válida. Elige entre 1 y 4.");
            }
        }
        sc.close();
    }
}