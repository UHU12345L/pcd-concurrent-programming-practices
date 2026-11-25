package p8_pcd;

import java.util.Random;

/**
 * Cliente que alquila esquis: botas -> traje -> esquis. Hereda de Thread.
 *
 * @author Laura
 */
public class Esquis extends Thread {

    private Equipamiento eq;
    private int id;

    public Esquis(Equipamiento eq, int id) {
        this.eq = eq;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());
        try {
            System.out.println("Esquis " + id + " llega");
            eq.entraBotasE(id);
            System.out.println("Esquis " + id + " recoge las BOTAS");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.entraTraje(id, 'E');//primero consigo sitio en la siguiente zona
            eq.saleBotas(id); //despues dejo la anterior
            System.out.println("Esquis " + id + " recoge el TRAJE");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.entraEsquis(id);
            eq.saleTraje(id);
            System.out.println("Esquis " + id + " recoge los ESQUIS");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.saleEsquis(id);
            System.out.println("Esquis " + id + " termina");
        } catch (InterruptedException ex) {
        }
    }
}
