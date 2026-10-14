/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasep3;

/**
 *
 * @author Laura
 */
public class Sumador extends Thread {

    private final int pos;
    private final Recurso datos;

    //me da array y la posicion que quiero sumar
    public Sumador(Recurso datos, int pos) {
        this.datos=datos;
        this.pos = pos;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
               //datos[pos]++;
               datos.incrementa(pos);
            try {
                sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }
}
