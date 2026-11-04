package proyecto2;

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
    public int particularesDesistido = 0;

    public synchronized boolean EntraParticular(int id) throws InterruptedException {
        int vecesDespertado = 0;
        while ((numLavadoras == 0 || (esperandoHosteleria > 0 && numHosteleria < 4)) && vecesDespertado < 3) {
            vecesDespertado++;
            wait();
        }
        if (vecesDespertado == 3 && (numLavadoras == 0 || (esperandoHosteleria > 0 && numHosteleria < 4))) {
            particularesDesistido++;
            System.out.println("Particular " + id + " desiste, quedan " + numLavadoras + " lavadoras");

            return false;
        }
        numLavadoras--;
        System.out.println("Particular " + id + " entra, quedan " + numLavadoras + " lavadoras");

        numParticulares++;
        return true;
    }

    public synchronized void SaleParticular(int id) {
        numLavadoras++;
        System.out.println("Particular " + id + " sale, quedan " + numLavadoras + " lavadoras");

        numParticulares--;
        notifyAll();
    }

    public synchronized void EntraHosteleria(int id) throws InterruptedException {
        esperandoHosteleria++;
        while (numLavadoras < 2 || numHosteleria >= 4) {

            wait();
        }
        numLavadoras = numLavadoras - 2;
        System.out.println("Hosteleria " + id + " entra, quedan " + numLavadoras + " lavadoras");
        esperandoHosteleria--;
        numHosteleria = numHosteleria + 2;
    }

    public synchronized void SaleHosteleria(int id) {
        numLavadoras = numLavadoras + 2;
        System.out.println("Hosteleria " + id + " sale, quedan " + numLavadoras + " lavadoras");

        numHosteleria = numHosteleria - 2;
        notifyAll();
    }
}
