package proyecto1;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Yate implements Runnable{
    private int id;
    private Puerto pu;
   

    public Yate(Puerto pu,int id) {
       this.pu=pu;
        this.id = id;
    }
    @Override
    public void run() {
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta

        try {
            System.out.println("Yate " + id + " llega");
            pu.entraYate(id);
           
            
            System.out.println("Yate " + id + " esta amarrado");
            Thread.sleep(r.nextInt(3000, 5001));
           
            pu.saleYate(id);
            System.out.println("Yate " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
