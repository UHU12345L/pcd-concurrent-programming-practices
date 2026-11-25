package clase_p8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ejemplo de clase corregido: alquiler de equipamiento con ReentrantLock y Conditions.
 * Botas -> traje -> esquis o tabla. En cada zona caben 2.
 * En botas pasa antes el tipo del que haya mas esperando (si empatan, esquis).
 * El cliente no deja una zona hasta tener sitio en la siguiente.
 *
 * @author Laura
 */
public class Monitor {

    private ReentrantLock lock = new ReentrantLock();   //el cerrojo: hace lo que antes hacia synchronized
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
    private int esperaBotasE = 0;   //cuantos de esquis hay en la cola de botas
    private int esperaBotasT = 0;   //cuantos de tabla hay en la cola de botas

    //despierta a uno de la cola de botas que tenga preferencia (mas esperando; si empatan, esquis)
    private void despiertaBotas() {
        if (esperaBotasE > 0 && esperaBotasE >= esperaBotasT) {
            colaBotasE.signal();   //signal: despierta a uno de esa cola
        } else if (esperaBotasT > 0) {
            colaBotasT.signal();
        }
    }

    public void entraBotasE() throws InterruptedException {
        lock.lock();   //cojo el cerrojo
        try {
            esperaBotasE++;
            //espero si no hay sitio o si los de tabla tienen preferencia (hay mas esperando)
            while (libreBotas == 0 || esperaBotasT > esperaBotasE) {
                if (libreBotas > 0) {
                    despiertaBotas();   //hay sitio pero no me toca: aviso al que tiene preferencia
                }
                colaBotasE.await();   //await: como wait(), suelta el cerrojo y se duerme en esta cola
            }
            esperaBotasE--;
            libreBotas--;
            if (libreBotas > 0) {
                despiertaBotas();   //si queda otro sitio, aviso al siguiente
            }
        } finally {
            lock.unlock();   //finally: se ejecuta siempre, asi el cerrojo se suelta aunque salte una excepcion
        }
    }

    public void entraBotasT() throws InterruptedException {
        lock.lock();
        try {
            esperaBotasT++;
            //espero si no hay sitio o si los de esquis tienen preferencia (mas esperando, o empate)
            while (libreBotas == 0 || esperaBotasE >= esperaBotasT) {
                if (libreBotas > 0) {
                    despiertaBotas();
                }
                colaBotasT.await();
            }
            esperaBotasT--;
            libreBotas--;
            if (libreBotas > 0) {
                despiertaBotas();
            }
        } finally {
            lock.unlock();
        }
    }

    public void saleBotas() {
        lock.lock();
        try {
            libreBotas++;
            despiertaBotas();
        } finally {
            lock.unlock();
        }
    }

    public void entraTraje() throws InterruptedException {
        lock.lock();
        try {
            while (libreTraje == 0) {
                colaTraje.await();
            }
            libreTraje--;
        } finally {
            lock.unlock();
        }
    }

    public void saleTraje() {
        lock.lock();
        try {
            libreTraje++;
            colaTraje.signal();
        } finally {
            lock.unlock();
        }
    }

    public void entraEsquis() throws InterruptedException {
        lock.lock();
        try {
            while (libreEsquis == 0) {
                colaEsquis.await();
            }
            libreEsquis--;
        } finally {
            lock.unlock();
        }
    }

    public void saleEsquis() {
        lock.lock();
        try {
            libreEsquis++;
            colaEsquis.signal();
        } finally {
            lock.unlock();
        }
    }

    public void entraTabla() throws InterruptedException {
        lock.lock();
        try {
            while (libreTabla == 0) {
                colaTabla.await();
            }
            libreTabla--;
        } finally {
            lock.unlock();
        }
    }

    public void saleTabla() {
        lock.lock();
        try {
            libreTabla++;
            colaTabla.signal();
        } finally {
            lock.unlock();
        }
    }
}
