package p5_pcd;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Efectivo extends Thread {
    //clase efectivo thread
    //en el run: r.pagarEfectivo(), "pagar", r.finEfectivo()
    //pagar es entrar y fin es salir
    private PuntosVenta pv;
    private int id;

    public Efectivo(PuntosVenta pv, int id) {
        this.pv = pv;
        this.id = id;
    }

    @Override
    public void run() {   // lo que ejecuta el hilo al hacer start()
        Random r = new Random(System.nanoTime());   //nanoTime: cada cliente con una semilla distinta
        try {
            System.out.println("Efectivo " + id + " llega");
            pv.pagarEfectivo(id);//entra (si no hay sitio, espera dentro)
            System.out.println("Efectivo " + id + " esta pagando");
            Thread.sleep(r.nextInt(3000, 5001));
            pv.finEfectivo(id); //sale y deja el punto libre
            System.out.println("Efectivo " + id + " ha terminado de pagar");
        } catch (InterruptedException ex) {
        }
    }
}
