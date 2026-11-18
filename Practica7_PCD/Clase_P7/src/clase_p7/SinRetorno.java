package clase_p7;

/**
 *
 * @author Laura
 */
public class SinRetorno implements Runnable {

    private final int id;

    public SinRetorno(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Soy la tarea" + id + " corriendo en el hilo " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Me dicen que aborte"); //aqui cojo la excepcion de shutdown now
            }
        }
    }
}
