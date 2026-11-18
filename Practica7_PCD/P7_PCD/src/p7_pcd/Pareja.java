package p7_pcd;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Pareja implements Runnable {

    private Pistas pi;
    private int id;

    public Pareja(Pistas pi, int id) {
        this.pi = pi;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime()); //nanoTime: cada cliente con una semilla distinta
        try {
            System.out.println("Pareja " + id + " llega");
            pi.bajarPareja(id);//entra (si no hay sitio, espera dentro)
            System.out.println("Pareja " + id + " esta bajando");
            Thread.sleep(r.nextInt(2000, 4001));
            pi.finPareja(id); //sale y deja el punto libre
            System.out.println("Pareja " + id + " ha terminado de bajar");
        } catch (InterruptedException ex) {
        }
    }
}
