package proyecto3;

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
            lava.SecaHosteleria(id); //sale y deja el punto libre
            System.out.println("Hosteleria " + id + " esta secando");
            Thread.sleep(r.nextInt(1000, 2001));
            lava.salir(id);
            System.out.println("Hosteleria " + id + " ha terminado");

        } catch (InterruptedException ex) {
        }
    }
}
