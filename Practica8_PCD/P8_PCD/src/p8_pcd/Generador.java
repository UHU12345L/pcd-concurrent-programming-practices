package p8_pcd;

import java.util.Random;

/**
 * Contiene el main: crea la ventana, el canvas y el Equipamiento, lanza 20
 * clientes (60% esquis, 40% tabla) cada 1-2 segundos y espera a que terminen.
 *
 * @author Laura
 */
public class Generador {

    public static void main(String[] args) {
        int numHilos = 20;
        Ventana f = new Ventana();
        f.setSize(900, 500);
        f.setTitle("Practica 8 - Alquiler de equipamiento");
        MiCanvas cv = new MiCanvas();
        f.add(cv);   //meto el canvas en la ventana
        f.setVisible(true);

        Equipamiento eq = new Equipamiento(cv);
        Random r = new Random(System.currentTimeMillis());
        Thread[] hilos = new Thread[numHilos];

        for (int i = 0; i < numHilos; i++) {
            if (r.nextInt(100) < 60) { //60% esquis
                hilos[i] = new Esquis(eq, i); //Esquis hereda de Thread: ya es un hilo
            } else {
                hilos[i] = new Thread(new Tabla(eq, i)); //Tabla es Runnable: hay que meterla en un Thread
            }
            hilos[i].start();
            try {
                Thread.sleep(r.nextInt(1000, 2001));//entre 1 y 2 segundos hasta el siguiente
            } catch (InterruptedException ex) {
            }
        }

        for (int i = 0; i < numHilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
            }
        }

        System.out.println("Todos los clientes han terminado");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }
}