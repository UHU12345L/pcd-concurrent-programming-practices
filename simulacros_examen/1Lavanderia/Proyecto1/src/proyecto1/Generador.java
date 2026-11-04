package proyecto1;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Hay 5 lavadoras, máx 2 hosteleria a la vez
        // Particulares: necesita 1 lavadora
        // Hosteleria: necesita 2 lavadoras, prioridad (comprobar si hay antes de meter particular)
        
        Lavanderia lava = new Lavanderia();
        int numHilos = 20;
        Random r = new Random(System.currentTimeMillis());
        Thread[] hilos = new Thread[numHilos];//guardo todos los hilos para hacer el join al final

        for (int i = 0; i < numHilos; i++) {
            if (r.nextInt(100) < 70) {
                hilos[i] = new Thread(new Particular(lava, i));
            } else {
                hilos[i] = new Hosteleria(lava, i); 
            }
            hilos[i].start();

            try {
                Thread.sleep(r.nextInt(1000, 2001));
            } catch (InterruptedException ex) {
            }
        }
        
         for (int i = 0; i < numHilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
}
