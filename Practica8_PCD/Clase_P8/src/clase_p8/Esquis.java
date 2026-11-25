package clase_p8;

import java.util.Random;

/**
 * Cliente que alquila esquis. Hereda de Thread.
 *
 * @author Laura
 */
public class Esquis extends Thread {

    private Monitor mon;
    private int id;

    public Esquis(Monitor mon, int id) {
        this.mon = mon;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());
        try {
            mon.entraBotasE();
            System.out.println("Esquis " + id + " en BOTAS");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.entraTraje();   //primero consigo sitio en la siguiente zona...
            mon.saleBotas();    //...y despues dejo la anterior
            System.out.println("Esquis " + id + " en TRAJE");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.entraEsquis();
            mon.saleTraje();
            System.out.println("Esquis " + id + " en ESQUIS");
            Thread.sleep(r.nextInt(2000, 3001));
            mon.saleEsquis();
            System.out.println("Esquis " + id + " termina");
        } catch (InterruptedException ex) {
        }
    }
}
