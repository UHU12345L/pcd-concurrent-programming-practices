package p7_pcd;

/**
 *
 * @author Laura
 */
public class Pistas {

    private int pIndividual = 4;
    private int pPareja = 2;
    private int esperaPareja= 0;
    private MiCanvas cv;

    public Pistas(MiCanvas cv) {
        this.cv = cv;
    }

    synchronized char bajarIndividual(int id) throws InterruptedException {
        cv.esperaIndividual(id);
        while (pIndividual == 0 && (pPareja == 0 || esperaPareja > 0)) {
            wait();
        }
        cv.finEsperaIndividual(id);

        char sitio;

        if (pIndividual> 0) {
            pIndividual--;
            sitio = 'i';
        } else {
            pPareja--;
            sitio = 'p';
        }
        cv.entraBajarIndividual(id, sitio);
        return sitio;
    }

    synchronized void finIndividual(int id, char sitio) {
        if (sitio == 'i') {
            pIndividual++;
        } else {
            pPareja++;
        }
        cv.finBajarI(id, sitio);
        notifyAll();
    }

    synchronized void bajarPareja(int id) throws InterruptedException {
        cv.esperaPareja(id);
        while (pPareja == 0) {
            esperaPareja++;
            wait();
            esperaPareja--;
        }
        pPareja--;
        cv.finEsperaPareja(id);
        cv.entraBajarPareja(id);
    }

    synchronized void finPareja(int id) {
        pPareja++;
        cv.finBajarP(id);
        notifyAll();
    }
}
