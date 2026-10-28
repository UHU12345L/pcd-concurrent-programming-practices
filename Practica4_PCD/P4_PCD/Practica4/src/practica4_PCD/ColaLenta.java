/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica4_PCD;

/**
 * ColaLenta circular (por eso modulos) Head: apunta a primer elemento Tail:
 * apunta a la siguiente posicion libre numelementos: distinguir si cola esta llena
 * porque head==tail pasa con sola vacia/llena Si un productor intenta acolar
 * con la cola llena, espera a que haya hueco. Si un consumidor intenta
 * desacolar con la cola vacía, espera a que haya elementos. Cada hilo espera
 * como mucho 3 veces, después desiste y lanza la excepción. Cuando el
 * consumidor termina, el main despierta (notifyAll) a los productores que
 * esperan hueco para que gasten sus intentos, desistan y terminen.
 *
 * @author Laura
 */
public class ColaLenta implements ICola {

    private int head;
    private int tail;
    private int capacidad;
    private int numelementos;
    private Object[] datos;
    private CanvasCola canvas;

    /**
     * crea cola vacia con la capacidad indicada
     *
     * @param capacidad nº máx elementos cola
     * @param elcanvas canvas donde se dibuja la cola
     */
    public ColaLenta(int capacidad, CanvasCola elcanvas) {
        this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.capacidad = capacidad;
        this.numelementos = 0;
        this.canvas = elcanvas;
    }

    @Override
    public int getNum() {
        return this.numelementos;
    }

    //NUNCA PONER UN ELSE DESPUES DE UN WAIT
    //acola y desacola hacer un sleep(100) entre cada instruccion
    //hacer más evidente la interacción de la concurrencia
    @Override
    public synchronized void acola(Object elemento) throws Exception {
        int intentos = 0;
        //esperar a que haya hueco como muchos 3 veces
        //siempre while no if
        //poner tres intentos para cada elemento para coger el sitio
        //si no lo coge no cojo nadie mas
        while (colallena() && intentos < 3) {
            System.out.println("Hilo: " + Thread.currentThread().threadId() + ": cola llena, espero hueco");
            canvas.avisa("La cola está llena");
            //throw new Exception("Maxima capacidad alcanzada");

            wait();
            //tengo que tener en cuenta cuando ya no van a haber mas huecos nunca mas y hay alguien esperando hueco
            intentos++;
            //incrementar contador de intentos
        }

        //ha gastado los 3 intentos o ha terminado
        if (!colallena()) {
            //no esta llena: hay hueco
            Thread.sleep(100);
            datos[tail] = elemento;
            Thread.sleep(100);
            numelementos++;
            Thread.sleep(100);
            tail = (tail + 1) % capacidad; //para que avance tail
            canvas.representa(datos, head, tail, numelementos);
            System.out.println("Hilo: " + Thread.currentThread().threadId() + ": acola " + elemento + " total: " + numelementos);
            notifyAll();

        } else {
            System.out.println("Hilo: " + Thread.currentThread().threadId() + ": desisto");
            canvas.avisa("La cola está llena");
            throw new Exception("Cola llena");
        }

    }

    @Override
    public synchronized Object desacola() throws Exception {
        int intentos = 0;
        Object devolver = null; //poder devolverlo, dentro del if no
        while (colavacia() && intentos < 3) {
            System.out.println("Hilo: " + Thread.currentThread().threadId() + ": cola vacia, espero elemento");
            canvas.avisa("La cola está vacia");
            wait();
            intentos++;
        }

        if (!colavacia()) {
            //ha salido del while porq hay elementos, desacola
            Thread.sleep(100);
            devolver = this.datos[this.head];
            Thread.sleep(100);
            datos[head] = null;
            Thread.sleep(100);
            numelementos--;
            Thread.sleep(100);
            head = (head + 1) % capacidad;
            Thread.sleep(100);
            canvas.representa(datos, head, tail, numelementos);
            System.out.println("Hilo: " + Thread.currentThread().threadId() + ": desacola " + devolver + " total: " + numelementos);
            notifyAll();
        }else{
             System.out.println("Hilo: " + Thread.currentThread().threadId() + ": desisto");
        canvas.avisa("La cola está vacia");
        throw new Exception("Cola vacia");
        }
       

        return devolver;
    }

    @Override
    public Object primero() throws Exception {
        if (colavacia()) {
            throw new Exception("Está vacía");
        }
        return datos[head];
    }

    /**
     * indica si la cola no tiene elementos
     *
     * @return true si numelementos es 0
     */
    private boolean colavacia() {
        return (numelementos == 0);
    }

    /**
     * indica si la cola esta llena
     *
     * @return true si no caben más elementos
     */
    private boolean colallena() {
        return (numelementos == capacidad);
    }
}
