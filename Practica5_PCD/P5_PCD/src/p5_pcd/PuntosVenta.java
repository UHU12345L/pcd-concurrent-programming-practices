package p5_pcd;

/**
 *
 * @author Laura
 */
public class PuntosVenta {
    //variables de estado para controlar si hay sitio, si hay alguien esperando... para protocolo entrada, pagar, protocolo salida
    //meter variables en la class PuntosVenta
    //pVentasLibres=4 o pVentasOcupados=0. Elijo pVentasLibres
    //pVentasLibresTarjeta=2, pVentasLibresEfectivo=2
    //tarjetaLibre=4, efectivoLibre=2
    //metodo synchronized char pagaTarjeta: se espera while(pVentasLibresEfectivo=0 OR espera>0) {wait} por las prioridades
    //if(pVentasLibresTarjeta>0)pVentasLibresTarjeta-- return 't' else pVentasLibresEfectivo-- return 'e'
    //metodo synchronized finTarjeta(char lugar) necesita saber que es lo q ha ocupado: if(sitio=='t')pVentasLibresTarjeta++ else pVentasLibresEfectivo++, notifyAll()
    //metodo synchronized pagarEfectivo: se espera while(pVentasLibresEfectivo=0) {espera++;wait;espera--} pVentasLibresEfectivo--
    //metodo synchronized finEfectivo: pVentasLibresEfectivo++, notifyAll()

    private int pVentasLibresTarjeta = 2;
    private int pVentasLibresEfectivo = 2;
    private int esperaEfectivo = 0;
    private MiCanvas cv;

    public PuntosVenta(MiCanvas cv) {
        this.cv = cv;
    }

    synchronized char pagarTarjeta(int id) throws InterruptedException {
        cv.esperaTarjeta(id);
        //pago en efectivo prioridad sobre tarjeta
        while (pVentasLibresTarjeta == 0 && (pVentasLibresEfectivo == 0 || esperaEfectivo > 0)) {
            wait();
        }
        cv.finEsperaTarjeta(id);

        char sitio;

        if (pVentasLibresTarjeta > 0) {
            pVentasLibresTarjeta--;
            sitio = 't';
        } else {
            pVentasLibresEfectivo--;
            sitio = 'e';
        }
        cv.entraPagarTarjeta(id, sitio);
        return sitio;
    }

    synchronized void finTarjeta(int id, char sitio) {
        if (sitio == 't') {
            pVentasLibresTarjeta++;
        } else {
            pVentasLibresEfectivo++;
        }
        cv.finPagarT(id, sitio);
        notifyAll();
    }

    synchronized void pagarEfectivo(int id) throws InterruptedException {
        cv.esperaEfectivo(id);
        while (pVentasLibresEfectivo == 0) {
            esperaEfectivo++;
            wait();
            esperaEfectivo--;
        }
        pVentasLibresEfectivo--;
        cv.finEsperaEfectivo(id);
        cv.entraPagarEfectivo(id);
    }

    synchronized void finEfectivo(int id) {
        pVentasLibresEfectivo++;
        cv.finPagarE(id);
        notifyAll();
    }
}
