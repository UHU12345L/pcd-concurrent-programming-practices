package proyecto4;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

        Frame f = new Frame("Lavanderia");   //ventana
        MiCanvas cv = new MiCanvas(550, 350);
        f.add(cv);                           //meto el canvas dentro de la ventana
        f.pack();                            //ajusta la ventana al tamaño del canvas
        f.addWindowListener(new WindowAdapter() {      //lo mismo que genera NetBeans en un Frame:
            @Override
            public void windowClosing(WindowEvent e) {  //al pulsar la X de la ventana...
                System.exit(0);                         //...termina el programa
            }
        });
        f.setVisible(true);                  //muestra la ventana

        Lavanderia lava = new Lavanderia(cv);   //la lavanderia recibe el canvas para dibujar
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

        try {
            Thread.sleep(3000);   //dejo la ventana 3 s para ver el final
        } catch (InterruptedException ex) {
        }
        System.exit(0);           //la ventana mantiene vivo el programa: hay que cerrarlo
    }
    
}
