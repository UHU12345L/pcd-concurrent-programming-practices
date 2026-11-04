package p6_pcd;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Bajar implements Runnable {

    private Semaphore semBajada;
    private Semaphore semTotal;
    private int id;
    private MiCanvas cv;

    public Bajar(Semaphore semBajada, Semaphore semTotal, int id, MiCanvas cv) {
        this.semBajada = semBajada;
        this.semTotal = semTotal;
        this.id = id;
        this.cv=cv;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bajar " + id + " llega");
            cv.esperaBajar(id);
            semBajada.acquire();
            semTotal.acquire();
            cv.entraBajar(id);
            System.out.println("Bajar " + id + " esta bajando");
            Thread.sleep(2000);
            semBajada.release();
            semTotal.release();
            cv.saleBajar(id);
            System.out.println("Bajar " + id + " ha llegado abajo");
        } catch (InterruptedException ex) {
        }
    }
}
