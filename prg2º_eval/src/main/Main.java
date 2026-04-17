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
            sc.nextLine(); // Limpiar el buffer

            if (opcion == 1) {
                System.out.print("Introduce tu nombre de jugador: ");
                String nombre = sc.nextLine();
                
                // 1. Creamos la partida en la BD
                int idPartida = GestorBD.crearNuevaPartida(nombre);
                
                // 2. Creamos el juego y guardamos a los personajes iniciales
                Juego miJuego = new Juego();
                miJuego.guardarPersonajesIniciales(idPartida);
                
                // 3. Arrancamos el combate pasándole el ID para que autoguarde
                try {
                    miJuego.iniciar(idPartida);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else if (opcion == 2) {
                System.out.println("\nPartidas disponibles para cargar:");
                GestorBD.listarPartidas();
                System.out.println("(Aún tenemos que programar la función de recuperar los datos, ¡pero el menú ya funciona!)");

            } else if (opcion == 3) {
                GestorBD.listarPartidas();
                System.out.print("Introduce el ID de la partida que quieres borrar: ");
                int idBorrar = sc.nextInt();
                GestorBD.borrarPartida(idBorrar);
                
            } else if (opcion == 4) {
                System.out.println("Saliendo del juego...");
            }
        }
        sc.close();
    }
}