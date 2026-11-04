package proyecto3;

import java.util.Random;

/**
 *
 * @author Laura
 */
public class Lavanderia {

    //Hay 5 lavadoras, máx 2 hosteleria a la vez
    // Particulares: necesita 1 lavadora
    // Hosteleria: necesita 2 lavadoras, prioridad (comprobar si hay antes de meter particular)
    public int numLavadoras = 5;
    public int numParticulares = 0;
    public int numHosteleria = 0;
    public int esperandoHosteleria = 0;
    public int numSecadoras = 2;

    public synchronized void EntraParticular(int id) throws InterruptedException {
        while (numLavadoras == 0 || (esperandoHosteleria > 0 && numHosteleria < 4)) {
            wait();
        }
        numLavadoras--;
        numParticulares++;
        System.out.println("Particular " + id + " entra, quedan " + numLavadoras + " lavadoras");
    }

    public synchronized void SecaParticular(int id) throws InterruptedException {
        while(numSecadoras==0){
            wait();
        }      
        numSecadoras--;
        numLavadoras++;
        numParticulares--;
            
        System.out.println("Particular " + id + " empieza a secar, quedan "+numLavadoras+" lavadoras y "+numSecadoras+" secadoras");
        notifyAll();
    }

    public synchronized void EntraHosteleria(int id) throws InterruptedException {
        esperandoHosteleria++;
        while (numLavadoras < 2 || numHosteleria >= 4) {
            wait();
        }
        
        esperandoHosteleria--;
        numLavadoras = numLavadoras - 2;
        numHosteleria = numHosteleria + 2;
        System.out.println("Hosteleria " + id + " entra, quedan " + numLavadoras + " lavadoras");      
    }

    public synchronized void SecaHosteleria(int id) throws InterruptedException {
       while(numSecadoras==0){
            wait();
        }      
        numSecadoras--;
        numLavadoras = numLavadoras + 2;
        numHosteleria = numHosteleria - 2;
            
        System.out.println("Hosteleria " + id + " empieza a secar, quedan "+numLavadoras+" lavadoras y "+numSecadoras+" secadoras");
        notifyAll();
    }

    public synchronized void salir(int id) throws InterruptedException {
        numSecadoras++;
        System.out.println("Cliente "+id+" sale, quedan "+numSecadoras+" secadoras");
        notifyAll();
    }

}
