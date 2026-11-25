package proyecto1;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Coche extends Thread{
    private int id;
    
    private Tunel tu;

    public Coche(int id, Tunel tu) {
        this.tu = tu;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime()); 

        try {
            System.out.println("Coche " + id + " llega");
            tu.entraCoche(id);

            System.out.println("Coche " + id + " esta lavando");
            Thread.sleep(r.nextInt(1000, 3001));

            tu.saleCoche(id);
            System.out.println("Coche " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
