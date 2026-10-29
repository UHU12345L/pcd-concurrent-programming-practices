package p5_pcd;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Tarjeta implements Runnable {
    //clase tarjeta runnable objetivo pagar (exclusion mutua sobre pagar)
    //en el run: r.pagarTarjeta(), "pagar", r.finTarjeta()
    //pagar es entrar y fin es salir
    //char sitio=r.pagaTarjeta()
    private PuntosVenta pv;
    private int id;
    private char sitio;

    public Tarjeta(PuntosVenta pv, int id) {
        this.pv = pv;
        this.id = id;
    }

    @Override
    public void run() {   // lo que ejecuta el hilo al hacer start()
        Random r = new Random(System.nanoTime());
        try {
            System.out.println("Tarjeta " + id + " llega");
            sitio = pv.pagarTarjeta(id);
            if (sitio == 't') {
                System.out.println("Tarjeta " + id + " esta pagando en punto de TARJETA");
            } else {
                System.out.println("Tarjeta " + id + " esta pagando en punto de EFECTIVO");
            }
            Thread.sleep(r.nextInt(2000, 4001));
            pv.finTarjeta(id, sitio);
            System.out.println("Tarjeta " + id + " ha terminado de pagar");
        } catch (InterruptedException ex) {
        }
    }

}
