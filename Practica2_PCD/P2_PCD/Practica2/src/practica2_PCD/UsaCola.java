package practica2_PCD;

import java.util.Random;

/**
 * Comprueba que ColaLenta funciona bien con un solo hilo (el main). Lanza un
 * Productor, espera a que termine, y luego lanza dos Consumidores a la vez
 * sobre la misma cola. Al no haber exclusión mutua, los consumidores deben dar
 * resultados incoherentes.
 *
 * @author Laura
 */
public class UsaCola {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        //PROBAR COLA LENTA
        ColaLenta probar = new ColaLenta(4);
        Random r = new Random(System.currentTimeMillis()); //o nanotime para hilos

        for (int i = 0; i < 10; i++) {

            int a = r.nextInt(2);
            if (a == 0) {
                try {
                    Object x = probar.desacola();
                    System.out.println("Prueba: extraigo " + x);
                } catch (Exception ex) {
                    System.out.println("Salta la excepcion " + ex.getMessage());

                }

            } else {
                try {
                    probar.acola(i);
                    System.out.println("Prueba: inserto " + i);
                } catch (Exception ex) {
                    System.out.println("Salta la excepcion " + ex.getMessage());

                }

            }
        }

        //PROBAR PRODUCTOR Y CONSUMIDORES
        ColaLenta cola = new ColaLenta(10);
        
        // Productor hereda de Thread, ya es un hilo, se arranca directamente
        Productor h1 = new Productor(cola);
        h1.start(); //crea el hilo y este ejecuta run() llama a producir()
        h1.join(); // main se para aquí hasta que el productor termine

        // Consumidor implementa Runnable, no es un hilo, solo es la tarea.
        // Hay que meterlo en un Thread, que al arrancar ejecuta el run() del Consumidor

        Consumidor c1 = new Consumidor(cola);   
        Thread h2 = new Thread(c1); 
        Consumidor c2 = new Consumidor(cola);
        Thread h3 = new Thread(c2);

        h2.start();
        h3.start();
        h2.join();
        h3.join();
       
    }

}
