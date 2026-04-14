package main;

import juego.Juego;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        linea('-', 60);
        System.out.println("2ªEvaluación");
        linea('>', 60);
        System.out.println("   El Mercenario usa Katana (cuerpo a cuerpo).");
        System.out.println("   Si no esta cerca del enemigo, no puede usarla.");
        linea('>', 60);
        System.out.print("   Pulsa ENTER ");
        scanner.nextLine();
        scanner.close();

        Juego juego = new Juego();
        
        juego.mostrarEquipos();
        Thread.sleep(15000);
        juego.iniciar();

        
    }
    


    private static void linea(char c, int n) {
        System.out.println(String.valueOf(c).repeat(n));
    }
}
