package p5_pcd;

import java.util.Random;

/**
 *
 * @author Laura
 */
//IMPORTANTE USAR EL REMOVE DE OBJ PORQUE EL DE INT BORRA ESA POSICION EN LUGAR DE BUSCAR EL NUMERO Y BORRAR ESE
//hacer class elemento con id y tipo en el contructor (para lo del object paara remove)
//sobrecargo public boolean equals: para q dos elementos sean iguales tiene q ser iguales los numeros (no el tipo)
//tengo 4 puntos venta: los 4 con tarjeta, solo dos de ellos con efectivo
//en punto con efectivo siempre prioridad los de efectivo
//importante poner y mostrar numero con el hilo que sea
//canvas quiero: cuatro cajas: pago tarjeta, pago efectivo, cola tarjeta, cola efectivo
//en pago solo caben 2 en cada caja
//efectivo con thread tarjeta con runnable
//crear random de 100
//generador de hilos: for hasta num hilos quiero, dentro pongo si rand(100)<70 tarjeta else efectivo
//hago sleep(rand(1000, 2000)), esperar a que se acabe: for 1 a 50 h(i).join()
//variables de estado para controlar si hay sitio, si hay alguien esperando... para protocolo entrada, pagar, protocolo salida
//meter variables en la class PuntosVenta
//pVentasLibres=4 o pVentasOcupados=0. Elijo pVentasLibres
//pVentasLibresTarjeta=2, pVentasLibresEfectivo=2
//tarjetaLibre=4, efectivoLibre=2
//metodo synchorized char pagaTarjeta: se espera while(pVentasLibresEfectivo=0 OR espera>0) {wait} por las prioridades
//if(pVentasLibresTarjeta>0)pVentasLibresTarjeta-- return 't' else pVentasLibresEfectivo-- return 'e'
//metodo synchorized finTarjeta(char lugar) necesita saber que es lo q ha ocupado: if(sitio=='t')pVentasLibresTarjeta++ else pVentasLibresEfectivo++, notifyAll()
//metodo synchorized pagarEfectivo: se espera while(pVentasLibresEfectivo=0) {espera++;wait;espera--} pVentasLibresEfectivo--
//metodo synchorized finEfectivo: pVentasLibresEfectivo++, notifyAll()
//clase tarjeta runnable objetivo pagar (exclusion mutua sobre pagar)
//en el run: r.pagarTarjeta(), "pagar", r.finTarjeta()
//pagar es entrar y fin es salir
//char sitio=r.pagaTarjeta()
//clase efectivo thread
//en el run: r.pagarEfectivo(), "pagar", r.finEfectivo()
//pagar es entrar y fin es salir
//Frame:
//main: crea recurso: crea canvas: MiCanvas cv=new MiCanvas, puntoVenta pv=new PuntoVenta(cv)
//for 1..50 if(rand(100)<70) new Tarjeta() else new Efectivo()
//canvas: clase MiCanvas
//escenarios tienen siempre: las colas, los recursos (pagarTarjeta, pagarEfectivo). debajo de todo esto un cuadrado donde pinto
//los numeros q pinto los tengo guardados en ArrayList ColaT, ColaE, PagaT, PagaE.
//esperaTarjeta(int id){ ColaT.add(id); repaint()}
//finEsperaTarjeta(int id){colaT.remove(id) repaint()}
//entraPagarTarjeta(int id, char sitio){if(sitio=='t') pagaT.add(id) else pagaE.add(id) repaint()}
//finPagarT(int id, char sitio){if(lugar='t'){pagaT.remove(id)}else{pagaE.remove(id) repaint()} 
//Tarjeta(7) hace cv.esperaTarjeta(7): while(...){wait();} cv.finEsperaTarjeta; cv.entraPagarTarjeta(id, sitio); cv.finPagarT(id,sitio)
//PARA ColaT, PagaT, ColaE, PagaE: for int i=1 to colaT.size(){drawstring(" "+ColaT(id)+" ", 100+i*5,100)}
//DIBUJITOS
//Image tipoAimg; tipoAimg=toolkit.getDefaultToolKit().getImage(getClass().getResource("poner enlace en la misma carpeta del proyecto"));
//MediaTracker dibu=new MediaTracker(this); dibu.addImage(tipoAimg,0) dibu.waitForAll
/**
 * Contiene el main: crea los puntos de venta y lanza 50 clientes (30% efectivo,
 * 70% tarjeta) cada 1-2 segundos. Espera a que terminen todos.
 *
 * @author Laura
 */
public class Generador {

    public static void main(String[] args) {
        int numHilos = 50;
        //PuntosVenta pv = new PuntosVenta(); //un objeto compartido por todos los clientes
        Ventana f = new Ventana();
        f.setSize(800, 500);
        f.setTitle("Practica 5 - Puntos de venta");

        MiCanvas cv = new MiCanvas();
        f.add(cv);   //meto el canvas en la ventana
        f.setVisible(true);

        PuntosVenta pv = new PuntosVenta(cv);

        Random r = new Random(System.currentTimeMillis());
        Thread[] hilos = new Thread[numHilos];//guardo todos los hilos para hacer el join al final

        for (int i = 0; i < numHilos; i++) {
            if (r.nextInt(100) < 70) {
                hilos[i] = new Thread(new Tarjeta(pv, i)); //Tarjeta es Runnable: hay que meterla en un Thread
            } else {
                hilos[i] = new Efectivo(pv, i); //Efectivo hereda de Thread: ya es un hilo
            }
            hilos[i].start();

            try {
                Thread.sleep(r.nextInt(1000, 2001));
            } catch (InterruptedException ex) {
            }
        }

        //espero a que terminen todos los clientes
        for (int i = 0; i < numHilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
            }
        }

        System.out.println("Todos los clientes han terminado");
        try {
            Thread.sleep(3000);   //espero 3 segundos para que se vea el estado final
        } catch (InterruptedException ex) {
        }
        System.exit(0);           //cierro el programa
    }
}
