package practica4_PCD;

import java.util.Random;

/**
 * Hilo que saca 15 numeros de la cola, uno cada 1-3 segundos.
 * @author Laura
 */
public class Consumidor extends Thread { //la clase es un hilo

    private ColaLenta lacola;

    public Consumidor(ColaLenta lacola) {
        this.lacola = lacola;
    }

    public void consumir() {
        Random r=new Random(System.nanoTime());
        long id=Thread.currentThread().threadId();
        try {
        for (int i = 0; i < 15; i++) {
                Thread.sleep(r.nextInt(1000,3001));
                Object sacado=lacola.desacola();
                System.out.println("Consumidor: " + id
                        + " extraigo: " + sacado +"("+(i+1) + " de 15)");
        }
            } catch (Exception ex) {
                System.out.println("Consumidor: " +id+" termina "+ ex.getMessage());
            }
        System.out.println("Consumidor "+id+": he terminado");
    }

    @Override
    public void run() {// lo que ejecuta el hilo al hacer start()
        consumir();
    }
}
