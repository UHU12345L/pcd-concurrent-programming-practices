package proyecto3;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Yate implements Runnable{
    private int id;
    private Semaphore semYates;
    private Semaphore semTotal;

    public Yate(Semaphore semYates, Semaphore semTotal,int id) {
        this.semYates = semYates;
        this.semTotal = semTotal;
        this.id = id;
    }
    @Override
    public void run() {
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta

        try {
            System.out.println("Yate " + id + " llega");
            semYates.acquire();
           semTotal.acquire(2);//yate ocupa dos amarres
            
            System.out.println("Yate " + id + " esta amarrado");
            Thread.sleep(r.nextInt(3000, 5001));
           
            semYates.release();
            semTotal.release(2);
            System.out.println("Yate " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
