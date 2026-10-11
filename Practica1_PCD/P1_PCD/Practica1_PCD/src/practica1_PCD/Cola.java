/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica1_PCD;

/**
 * Cola circular (por eso modulos)
 * Head: apunta a primer elemento
 * Tail: apunta a la ultima posicion
 * numelementos: distinguir si cola esta llena porque head==tail pasa con sola vacia/llena
 * @author Laura
 */
public class Cola implements ICola {

    private int head;
    private int tail;
    private int capacidad;
    private int numelementos;
    private Object[] datos;

    /**
     * crea cola vacia con la capacidad indicada
     * @param capacidad nº máx elementos cola
     */
    public Cola(int capacidad) {
        this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.capacidad = capacidad;
        this.numelementos = 0;
    }

    @Override
    public int getNum() {
        return this.numelementos;
    }
    @Override
    public void acola(Object elemento) throws Exception {
        if (colallena()) {
            throw new Exception("Maxima capacidad alcanzada");
        }

        datos[tail] = elemento;
        numelementos++;
        tail = (tail + 1) % capacidad;
    }

    @Override
    public Object desacola() throws Exception {
        if (colavacia()) {
            throw new Exception("Está vacía");
        }
        Object devolver = this.datos[this.head];
        datos[head]=null;
        numelementos--;
        head = (head + 1) % capacidad;
        return devolver;
    }
    @Override
    public Object primero() throws Exception {
        if (this.numelementos == 0) {
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
