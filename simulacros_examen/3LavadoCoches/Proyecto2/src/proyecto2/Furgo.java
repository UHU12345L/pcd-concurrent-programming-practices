package proyecto2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Furgo implements Runnable {

    int id;
    private Semaphore semFurgo;
    private Semaphore semTotal;

    public Furgo(int id, Semaphore semFurgo, Semaphore semTotal) {
        this.id = id;
        this.semFurgo = semFurgo;
        this.semTotal = semTotal;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());

        try {
            System.out.println("Furgo " + id + " llega");
            semFurgo.acquire();
            semTotal.acquire();
            System.out.println("Furgo " + id + " esta lavando, quedan "+semTotal.availablePermits()+ " puestos libres");
            Thread.sleep(r.nextInt(1000, 3001));

            semFurgo.release();
            semTotal.release();
            System.out.println("Furgo " + id + " se marcha, quedan "+semTotal.availablePermits()+ " puestos libres");
        } catch (InterruptedException ex) {
        }
    }
}
