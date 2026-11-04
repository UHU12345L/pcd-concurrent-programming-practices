package proyecto1;

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
            System.out.println("Cliente hosteleria " + id + " llega");
            lava.EntraHosteleria(id);//entra (si no hay sitio, espera dentro)
            System.out.println("Cliente hosteleria " + id + " esta limpiando");
            Thread.sleep(r.nextInt(3000, 5001));
            lava.SaleHosteleria(id); //sale y deja el punto libre
            System.out.println("Cliente hosteleria " + id + " ha terminado de limpiar");
        } catch (InterruptedException ex) {
        }
    }
}
