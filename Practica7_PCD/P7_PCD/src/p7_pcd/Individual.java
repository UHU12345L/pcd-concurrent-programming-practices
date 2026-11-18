package p7_pcd;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author Laura
 */
public class Individual implements Callable<Integer> {

    private Pistas pi;
    private int id;
    private char sitio;

    public Individual(Pistas pi, int id) {
        this.pi = pi;
        this.id = id;
    }

    @Override
    public Integer call() {
        Random r = new Random(System.nanoTime());
        int segundos = 0;
        try {
            System.out.println("Individual " + id + " llega");
            sitio = pi.bajarIndividual(id);
            if (sitio == 'i') {
                System.out.println("Individual " + id + " esta bajando en pista INDIVIDUAL");
            } else {
                System.out.println("Individual " + id + " esta bajando en pista PAREJA");
            }
            segundos = r.nextInt(3, 6); //entre 3 y 5 segundos
            Thread.sleep(segundos * 1000); //sleep va en milisegundos
            pi.finIndividual(id, sitio);
            System.out.println("Individual " + id + " ha terminado de bajar");
        } catch (InterruptedException ex) {
        }
        return segundos;

    }
}
