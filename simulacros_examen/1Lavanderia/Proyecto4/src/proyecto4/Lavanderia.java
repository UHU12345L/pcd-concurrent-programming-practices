package proyecto4;

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
    private MiCanvas cv;   //canvas donde se dibujan las colas y las lavadoras

    public Lavanderia(MiCanvas cv) {
        this.cv = cv;
    }

    public synchronized void EntraParticular(int id) throws InterruptedException {
        cv.mete(cv.colaParticular, id, 'P');   //llega: aparece en la cola
        while (numLavadoras == 0 || (esperandoHosteleria > 0 && numHosteleria < 4)) {
            wait();
        }
        cv.saca(cv.colaParticular, id);        //entra: sale de la cola...
        cv.mete(cv.lavadoras, id, 'P');        //...y aparece en una lavadora
        numLavadoras--;
        numParticulares++;
        System.out.println("Particular " + id + " entra, quedan " + numLavadoras + " lavadoras");
    }

    public synchronized void SaleParticular(int id) {
        cv.saca(cv.lavadoras, id);             //se va: desaparece de la lavadora
        numLavadoras++;
        numParticulares--;
        System.out.println("Particular " + id + " sale, quedan " + numLavadoras + " lavadoras");
        notifyAll();
    }

    public synchronized void EntraHosteleria(int id) throws InterruptedException {
        cv.mete(cv.colaHosteleria, id, 'H');   //llega: aparece en la cola de hosteleria
        esperandoHosteleria++;
        while (numLavadoras < 2 || numHosteleria >= 4) {
            wait();
        }
        esperandoHosteleria--;
        cv.saca(cv.colaHosteleria, id);
        cv.mete(cv.lavadoras, id, 'H');        //ocupa 2 lavadoras: se mete dos veces
        cv.mete(cv.lavadoras, id, 'H');
        numLavadoras = numLavadoras - 2;
        numHosteleria = numHosteleria + 2;
        System.out.println("Hosteleria " + id + " entra, quedan " + numLavadoras + " lavadoras");
    }

    public synchronized void SaleHosteleria(int id) {
        cv.saca(cv.lavadoras, id);             //cada saca borra uno: dos veces
        cv.saca(cv.lavadoras, id);
        numLavadoras = numLavadoras + 2;
        numHosteleria = numHosteleria - 2;
        System.out.println("Hosteleria " + id + " sale, quedan " + numLavadoras + " lavadoras");
        notifyAll();
    }

}
