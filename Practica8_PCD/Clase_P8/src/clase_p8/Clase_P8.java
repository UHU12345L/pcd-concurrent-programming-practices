package clase_p8;

/**
 *
 * @author Laura
 */
public class Clase_P8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Monitor mon = new Monitor();
        Thread[] hilos = new Thread[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {   //uno de cada tipo, alternando
                hilos[i] = new Esquis(mon, i);
            } else {
                hilos[i] = new Thread(new Tabla(mon, i));   //Tabla es Runnable: va dentro de un Thread
            }
            hilos[i].start();
        }
        for (int i = 0; i < 10; i++) {
            hilos[i].join();
        }
        System.out.println("Fin");
    }
}
