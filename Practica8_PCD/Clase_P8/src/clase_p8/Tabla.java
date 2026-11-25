package clase_p8;

import java.util.Random;

/**
 * Cliente que alquila tabla. Implementa Runnable.
 *
 * @author Laura
 */
public class Tabla implements Runnable {

    private Monitor mon;
    private int id;

    public Tabla(Monitor mon, int id) {
        this.mon = mon;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());
        try {
            mon.entraBotasT();
            System.out.println("Tabla " + id + " en BOTAS");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.entraTraje();   //primero consigo sitio en la siguiente zona...
            mon.saleBotas();    //...y despues dejo la anterior
            System.out.println("Tabla " + id + " en TRAJE");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.entraTabla();
            mon.saleTraje();
            System.out.println("Tabla " + id + " en TABLA");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.saleTabla();
            System.out.println("Tabla " + id + " termina");
        } catch (InterruptedException ex) {
        }
    }
}
