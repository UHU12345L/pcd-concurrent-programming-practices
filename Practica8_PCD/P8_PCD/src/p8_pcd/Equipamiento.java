package p8_pcd;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Laura
 */
public class Equipamiento {

    private ReentrantLock lock = new ReentrantLock(); //el cerrojo hace lo que antes hacia synchronized
    //una cola de espera (Condition) por cada cosa que se puede esperar
    private Condition colaBotasE = lock.newCondition();
    private Condition colaBotasT = lock.newCondition();
    private Condition colaTraje = lock.newCondition();
    private Condition colaEsquis = lock.newCondition();
    private Condition colaTabla = lock.newCondition();

    private int libreBotas = 2;
    private int libreTraje = 2;
    private int libreEsquis = 2;
    private int libreTabla = 2;
    private int esperaBotasE = 0; //cuantos de esquis hay en la cola de botas
    private int esperaBotasT = 0; //cuantos de tabla hay en la cola de botas

    private MiCanvas cv;

    public Equipamiento(MiCanvas cv) {
        this.cv = cv;
    }

    //despierta a uno de la cola de botas que tenga mas esperando (si empatan, esquis)
    private void despiertaBotas() {
        if (esperaBotasE > 0 && esperaBotasE >= esperaBotasT) {
            colaBotasE.signal();   //signal: despierta a uno de esa cola
        } else if (esperaBotasT > 0) {
            colaBotasT.signal();
        }
    }

    public void entraBotasE(int id) throws InterruptedException {
        lock.lock(); //cojo el cerrojo
        try {
            esperaBotasE++;
            cv.esperaBotas(id, 'E');
            //espero si no hay sitio o si los de tabla tienen preferencia (hay mas esperando)
            while (libreBotas == 0 || esperaBotasT > esperaBotasE) {
                if (libreBotas > 0) {
                    despiertaBotas(); //hay sitio pero no me toca: aviso al que tiene preferencia
                }
                colaBotasE.await();
            }
            esperaBotasE--;
            libreBotas--;
            cv.entraBotas(id, 'E');
            if (libreBotas > 0) {
                despiertaBotas(); //si queda otro sitio, aviso al siguiente
            }
        } finally {
            lock.unlock();
        }
    }

    public void entraBotasT(int id) throws InterruptedException {
        lock.lock();
        try {
            esperaBotasT++;
            cv.esperaBotas(id, 'T');
            //espero si no hay sitio o si los de esquis tienen preferencia (mas esperando, o empate)
            while (libreBotas == 0 || esperaBotasE >= esperaBotasT) {
                if (libreBotas > 0) {
                    despiertaBotas();
                }
                colaBotasT.await();
            }
            esperaBotasT--;
            libreBotas--;
            cv.entraBotas(id, 'T');
            if (libreBotas > 0) {
                despiertaBotas();
            }
        } finally {
            lock.unlock();
        }
    }

    public void saleBotas(int id) {
        lock.lock();
        try {
            libreBotas++;
            cv.saleBotas(id);
            despiertaBotas();
        } finally {
            lock.unlock();
        }
    }

    public void entraTraje(int id, char tipo) throws InterruptedException {
        lock.lock();
        try {
            cv.esperaTraje(id);
            while (libreTraje == 0) {
                colaTraje.await();
            }
            libreTraje--;
            cv.entraTraje(id, tipo);

        } finally {
            lock.unlock();
        }
    }

    public void saleTraje(int id) {
        lock.lock();
        try {
            libreTraje++;
            cv.saleTraje(id);
            colaTraje.signal();
        } finally {
            lock.unlock();
        }
    }

    public void entraEsquis(int id) throws InterruptedException {
        lock.lock();
        try {
            cv.esperaSiguiente(id);
            while (libreEsquis == 0) {
                colaEsquis.await();
            }
            libreEsquis--;
            cv.entraEsquis(id);
        } finally {
            lock.unlock();
        }
    }

    public void saleEsquis(int id) {
        lock.lock();
        try {
            libreEsquis++;
            cv.saleEsquis(id);
            colaEsquis.signal();
        } finally {
            lock.unlock();
        }
    }

    public void entraTabla(int id) throws InterruptedException {
        lock.lock();
        try {
            cv.esperaSiguiente(id);
            while (libreTabla == 0) {
                colaTabla.await();
            }
            libreTabla--;
            cv.entraTabla(id);
        } finally {
            lock.unlock();
        }
    }

    public void saleTabla(int id) {
        lock.lock();
        try {
            libreTabla++;
            cv.saleTabla(id);
            colaTabla.signal();
        } finally {
            lock.unlock();
        }
    }
}
