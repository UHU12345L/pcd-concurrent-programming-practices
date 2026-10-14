/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica3_PCD;

/**
 * ColaLenta circular (por eso modulos)
Head: apunta a primer elemento
Tail: apunta a la ultima posicion
numelementos: distinguir si cola esta llena porque head==tail pasa con sola vacia/llena
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
     * @param capacidad nº máx elementos cola
     * @param datos todos los elementos empiezan a null
     */
    public ColaLenta(int capacidad, CanvasCola elcanvas) {
        this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.capacidad = capacidad;
        this.numelementos = 0;
        this.canvas=elcanvas;
    }

    @Override
    public int getNum() {
        return this.numelementos;
    }
    
    //acola y desacola hacer un sleep(100) entre cada instruccion
    //hacer más evidente la interacción de la concurrencia
    @Override
    public synchronized void acola(Object elemento) throws Exception {
        if (colallena()) {
            canvas.avisa("La cola está llena");
            throw new Exception("Maxima capacidad alcanzada");
        }
        Thread.sleep(100);
        datos[tail] = elemento;
        Thread.sleep(100);
        numelementos++;
        Thread.sleep(100);
        tail = (tail + 1) % capacidad; //para que avance tail
        canvas.representa(datos, head, tail,numelementos);
    }

    @Override
    public synchronized Object desacola() throws Exception {
        if (colavacia()) {
            canvas.avisa("La cola está vacia");
            throw new Exception("Está vacía");
        }
        Thread.sleep(100);
        Object devolver = this.datos[this.head];
        Thread.sleep(100);
        datos[head]=null;
        Thread.sleep(100);
        numelementos--;
        Thread.sleep(100);
        head = (head + 1) % capacidad;
        Thread.sleep(100);
        canvas.representa(datos, head, tail, numelementos);
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
     * @return true si numelementos es 0
     */
    private boolean colavacia() {
        return (numelementos == 0);
    }

    /**
     * indica si la cola esta llena
     * @return true si no caben más elementos
     */
    private boolean colallena() {
        return (numelementos == capacidad);
    }
}
