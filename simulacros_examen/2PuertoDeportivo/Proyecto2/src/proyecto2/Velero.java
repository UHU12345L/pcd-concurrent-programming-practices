package proyecto2;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Velero extends Thread{
    private int id;
    private Puerto pu;


    public Velero(Puerto pu, int id) {
       this.pu=pu;
        this.id = id;
    }
    @Override
    public void run() {
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta

        try {
            System.out.println("Velero " + id + " llega");
            pu.entraVelero(id);
            
            
            System.out.println("Velero " + id + " esta amarrado");
            Thread.sleep(r.nextInt(2000, 4001));
           
            pu.saleVelero(id);
            System.out.println("Velero " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
