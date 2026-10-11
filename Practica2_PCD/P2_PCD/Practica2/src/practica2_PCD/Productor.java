package practica2_PCD;

import java.util.Random;

/**
 *  Hilo que mete 10 floats aleatorios en la cola.
 * @author Laura
 */
public class Productor extends Thread { //la clase es un hilo

    private ColaLenta lacola;

    public Productor(ColaLenta lacola) {
        this.lacola = lacola;
    }

    public void producir() {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            float num = r.nextFloat(0, 100);

            try {
                lacola.acola(num);
                System.out.println("Identificador del hilo: " + Thread.currentThread().threadId()
                        + " número introducido: " + num);

            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {// lo que ejecuta el hilo al hacer start()
        producir();
    }
}
