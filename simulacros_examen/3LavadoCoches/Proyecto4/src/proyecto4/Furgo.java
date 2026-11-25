package proyecto4;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Furgo implements Runnable {

    int id;
    private Tunel tu;

    public Furgo(int id, Tunel tu) {
        this.id = id;
        this.tu = tu;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());

        try {
            System.out.println("Furgo " + id + " llega");
            tu.entraFurgo(id);

            System.out.println("Furgo " + id + " esta lavando");
            Thread.sleep(r.nextInt(1000, 3001));

            tu.saleFurgo(id);
            System.out.println("Furgo " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
    }
}
