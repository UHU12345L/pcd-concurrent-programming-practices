/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasep4;

/**
 *
 * @author Laura
 */
public class Hilo extends Thread {

    private final int id;
    private final Ordenador o;

    public Hilo(int id, Ordenador o) {
        this.id = id;
        this.o = o;
    }

    @Override
    public void run() {
        
            //esperar a que el anterior ponga su mensaje
            //mejor dicho: esperar a que me toque el turno
            
            try{
                for (int i = 0; i < 5; i++) {
                 o.cogerturno(id); //te digo mi id, cuando salgo de ahi tengo el turno
            System.out.println("soy el hilo " + id);
            //avisar al siguiente que le toque poner su mensaje
            //mejor dicho: pasar el turno al siguiente, correr el turno
            o.pasarturno();
            //meter turno en un avariable: espero a que turno=miId
            //tengo que pasarle la variable turno a los 5 hilos: pasar objeto por referencia
                }    
            } catch(InterruptedException ex){}
           

        
    }
}
