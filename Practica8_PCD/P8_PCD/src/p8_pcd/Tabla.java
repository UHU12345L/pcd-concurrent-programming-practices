package p8_pcd;

import java.util.Random;

/**
 * Cliente que alquila tabla: botas -> traje -> tabla. Implementa Runnable.
 *
 * @author Laura
 */
public class Tabla implements Runnable {

    private Equipamiento eq;
    private int id;

    public Tabla(Equipamiento eq, int id) {
        this.eq = eq;
        this.id = id;
    }

    @Override
    public void run() {
        Random r = new Random(System.nanoTime());  
        try {
            System.out.println("Tabla " + id + " llega");
            eq.entraBotasT(id);
            System.out.println("Tabla " + id + " recoge las BOTAS");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.entraTraje(id, 'T');//primero consigo sitio en la siguiente zona
            eq.saleBotas(id); //y despues dejo la anterior
            System.out.println("Tabla " + id + " recoge el TRAJE");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.entraTabla(id);
            eq.saleTraje(id);
            System.out.println("Tabla " + id + " recoge la TABLA");
            Thread.sleep(r.nextInt(2000, 3001));
            eq.saleTabla(id);
            System.out.println("Tabla " + id + " termina");
        } catch (InterruptedException ex) {
        }
    }
}