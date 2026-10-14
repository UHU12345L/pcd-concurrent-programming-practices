package practica3_PCD;

/**
 * Tarea que saca 10 números de la cola.
 * @author Laura
 */
public class Consumidor extends Thread { //solo define la tarea, no es un hilo

    private ColaLenta lacola;

    public Consumidor(ColaLenta lacola) {
        this.lacola = lacola;
    }

    public void consumir() {
        for (int i = 0; i < 10; i++) {
            try {
                Object sacado=lacola.desacola();
                System.out.println("Identificador del hilo: " + Thread.currentThread().threadId()
                        + " número extraido: " + sacado);

            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public void run() {// lo que ejecuta el hilo al hacer start()
        consumir();
    }
}
