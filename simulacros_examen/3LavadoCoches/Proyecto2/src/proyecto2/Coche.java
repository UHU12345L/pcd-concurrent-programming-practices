package proyecto2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Coche extends Thread{
    private int id;
    private Semaphore semCoche;
    private Semaphore semTotal;

    public Coche(int id, Semaphore semCoche, Semaphore semTotal) {
        this.id = id;
        this.semCoche=semCoche;
        this.semTotal=semTotal;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime()); 

        try {
            System.out.println("Coche " + id + " llega, quedan "+semTotal.availablePermits()+ " puestos libres");
            semCoche.acquire();
            semTotal.acquire();

            System.out.println("Coche " + id + " esta lavando");
            Thread.sleep(r.nextInt(1000, 3001));

            semCoche.release();
            semTotal.release();
            System.out.println("Coche " + id + " se marcha, quedan "+semTotal.availablePermits()+ " puestos libres");
        } catch (InterruptedException ex) {
        }
    }
}
