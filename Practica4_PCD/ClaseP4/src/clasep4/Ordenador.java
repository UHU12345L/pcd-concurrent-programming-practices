/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasep4;

/**
 * La clase que ordena, da el turno
 *
 * @author Laura
 */
public class Ordenador {

    int turno;

    public Ordenador(int inicial) {
        turno = inicial;
    }

    public synchronized void cogerturno(int id) throws InterruptedException {
        //esperar a que le toque el turno si no es su id el que recibe (no le toca)
        //siempre que use wait tengo q usar while para que al salir compruebe si de verdad le toca
        //pero ahora lo que pasa es que notify despierta solo un hilo y puede que no sea su id?
        //con notify all todos se paran en while si no es su turno
        while (turno != id) 
            wait(); //wait en o (Ordenador)
    }

    public synchronized void pasarturno() {
        turno=(turno+1)%5;
        //tengo que avidar al proximo de que le toca 
        //aviso/despierto a todos porq lo compruebo luego en el while
        notifyAll();
    }
}
