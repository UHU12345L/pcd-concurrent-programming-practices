package practica4_PCD;

import java.util.Random;

/**
 * Tarea que saca 10 números de la cola.
 * @author Laura
 */
public class Consumidor extends Thread { //solo define la tarea, no es un hilo

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
        //aviso de que he terminado (cambia fin, hace notifyAll)
        System.out.println("Consumidor "+id+": her terminado, aviso a los productores");
        lacola.finalizar();
    }

    @Override
    public void run() {// lo que ejecuta el hilo al hacer start()
        consumir();
    }
}
