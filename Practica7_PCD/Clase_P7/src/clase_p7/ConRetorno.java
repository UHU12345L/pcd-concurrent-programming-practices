package clase_p7;

import java.util.concurrent.Callable;

/**
 *
 * @author Laura
 */
public class ConRetorno implements Callable<Integer> {
    //cambio Runnable por Callable
    //para devolver necesito Callable, quiero q devuelva Integer
    private final int id;

    public ConRetorno(int id) {
        this.id = id;
    }

    //runnable: run, callable: call
    @Override
    public Integer call() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Soy la tarea" + id + " corriendo en el hilo " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Me dicen que aborte"); //aqui cojo la excepcion de shutdown now
            }
        }
        
        return id*1000;
    }
}
