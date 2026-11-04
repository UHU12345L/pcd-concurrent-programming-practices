package proyecto3;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Particular implements Runnable{
   private Lavanderia lava;
    private int id;

    public Particular(Lavanderia lava, int id) {
        this.lava = lava;
        this.id = id;
    }

    @Override
    public void run() {   // lo que ejecuta el hilo al hacer start()
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta
        try {
            System.out.println("Cliente particular " + id + " llega");
            lava.EntraParticular(id);//entra (si no hay sitio, espera dentro)
            System.out.println("Cliente particular " + id + " esta limpiando");
            Thread.sleep(r.nextInt(2000, 4001));
            lava.SecaParticular(id); //sale y deja el punto libre
            Thread.sleep(r.nextInt(1000, 2001));
            System.out.println("Cliente particular " + id + " ha terminado de limpiar");
            lava.salir(id);
        } catch (InterruptedException ex) {
        }
    }
}