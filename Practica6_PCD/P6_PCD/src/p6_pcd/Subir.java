package p6_pcd;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Laura
 */
public class Subir extends Thread {
    private MiCanvas cv;
    private Semaphore semSubida;
    private Semaphore semTotal;
    private int id;

    public Subir(Semaphore semSubida, Semaphore semTotal, int id, MiCanvas cv) {
        this.semSubida = semSubida;
        this.semTotal = semTotal;
        this.id = id;
        this.cv=cv;
    }

    @Override
    public void run() {
        try {
            System.out.println("Subir " + id + " llega");
            cv.esperaSubir(id);
            semSubida.acquire();
            semTotal.acquire();
            cv.entraSubir(id);
            System.out.println("Subir " + id + " esta subiendo");
            sleep(2000);
            semSubida.release();
            semTotal.release();
            cv.saleSubir(id);
            System.out.println("Subir " + id + " ha llegado arriba");
        } catch (InterruptedException ex) {
        }
    }
}
