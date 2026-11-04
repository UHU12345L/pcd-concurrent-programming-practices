package proyecto4;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Hosteleria extends Thread{
   private Lavanderia lava;
    private int id;

    public Hosteleria(Lavanderia lava, int id) {
        this.lava = lava;
        this.id = id;
    }

    @Override
    public void run() {   // lo que ejecuta el hilo al hacer start()
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta
        try {
            System.out.println("Hosteleria " + id + " llega");
            lava.EntraHosteleria(id);//entra (si no hay sitio, espera dentro)
            System.out.println("Hosteleria " + id + " esta limpiando");
            Thread.sleep(r.nextInt(3000, 5001));
            lava.SaleHosteleria(id); //sale y deja sus 2 lavadoras libres
            System.out.println("Hosteleria " + id + " ha terminado de limpiar");
        } catch (InterruptedException ex) {
        }
    }
}
