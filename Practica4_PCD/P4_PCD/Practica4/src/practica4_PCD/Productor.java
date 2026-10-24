package practica4_PCD;

import java.util.Random;

/**
 * Hilo que mete 10 floats aleatorios en la cola.
 *
 * @author Laura
 */
public class Productor extends Thread { //la clase es un hilo

    private ColaLenta lacola; //cola compartida con el consumidor

    public Productor(ColaLenta lacola) {
        this.lacola = lacola;
    }

    public void producir() {
        Random r = new Random(System.nanoTime());
        long id=Thread.currentThread().threadId();
        try {
            //acolar 15 elementos cada 1-3s
            for (int i = 0; i < 15; i++) {
                Thread.sleep(r.nextInt(1000, 3001));   //espera entre 1 y 3 segundos (el 3001 no entra)
                float num = r.nextFloat(0, 100);
                System.out.println("Productor "+id+" intento acolar "+num);
                lacola.acola(num);
            }
            System.out.println("Productor "+id+" he acolado mis 15 elementos");
        } catch (Exception ex) {
            System.out.println("Productor: " +id+" termina "+ ex.getMessage());
        }
    }

    @Override
    public void run() {// lo que ejecuta el hilo al hacer start()
        producir();
    }
}
