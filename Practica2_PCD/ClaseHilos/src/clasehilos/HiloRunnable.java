/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasehilos;

import static java.lang.Thread.sleep;

/**
 *
 * @author Usuario
 */
public class HiloRunnable implements Runnable{

     private int id;
     private int contador=0;
     
    HiloRunnable(int i) {
        id=i;
    }
    @Override
    public void run(){
        //puedo cambiar todos los Thread.currentThread(). por Thread actual=Thread.currentThread(); y hacer actual. abajo
        //codigo concurrente:    
        
         //cambiar prioridades:
        Thread.currentThread().setPriority(id);
        
        //lo que hace
        for (int i = 0; i < 10; i++) {
            System.out.println("Soy el hilo "+id+", con prioridad "+Thread.currentThread().getPriority()+
                    " nombre "+Thread.currentThread().getName()+" estado "+Thread.currentThread().getState()+" e identificador "+
                    Thread.currentThread().threadId());
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
    
            }
            contador++;
        }
        
        System.out.println("El contador vale: "+contador);
    }
    
}
