package practica1_PCD;

import java.util.Random;

/**
 * Probar las clases y sus métodos cola de 4 elementos, insertando o extrayendo
 * (aleatoriamente) 10 números. Para ello, se programará un bucle con 10
 * iteraciones. En cada iteración, se generará un numero aleatorio entre 0 y 1.
 * Si el resultado es 0, se extraerá el primer número de la cola. Si el
 * resultado es 1 se insertará en la cola el número de la iteración del bucle
 *
 * @author Laura
 */
public class UsaCola {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Cola probar = new Cola(4);
        Random r = new Random(System.currentTimeMillis()); //o nanotime para hilos

        for (int i = 0; i < 10; i++) {

            int a = r.nextInt(2);
            if (a == 0) {
                try {
                    probar.desacola();
                } catch (Exception ex) {
                    System.out.println("Salta la excepcion " + ex.getMessage());

                }

            } else {
                try {
                    probar.acola(i);
                } catch (Exception ex) {
                    System.out.println("Salta la excepcion " + ex.getMessage());

                }

            }
        }

    }

}
