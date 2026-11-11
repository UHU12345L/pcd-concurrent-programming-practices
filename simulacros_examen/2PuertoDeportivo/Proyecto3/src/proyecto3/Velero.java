package proyecto3;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Velero extends Thread{
    private int id;
    private Semaphore semVeleros;
    private Semaphore semTotal;

    public Velero(Semaphore semVeleros, Semaphore semTotal,int id) {
        this.semVeleros = semVeleros;
        this.semTotal = semTotal;
        this.id = id;
    }
    @Override
    public void run() {
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta

        try {
            System.out.println("Velero " + id + " llega");
            semVeleros.acquire();
            semTotal.acquire();
            
            System.out.println("Velero " + id + " esta amarrado");
            Thread.sleep(r.nextInt(2000, 4001));
           semVeleros.release();
            semTotal.release();
            
            System.out.println("Velero " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
