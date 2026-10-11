/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasehilos;

/**
 *
 * @author Laura
 */
public class HiloThread extends Thread{
    private int id;
    private Recurso contador;
    
    //ahora quien me da el ontador es la main
    HiloThread(int i, Recurso contadormain) {
        id=i;
        contador=contadormain;
    }
    @Override
    public void run(){
        //codigo concurrente: 
       
        //cambiar prioridades:
        setPriority(id);
        
        //lo que hace:
        for (int i = 0; i < 10; i++) {
            /*System.out.println("Soy el hilo "+id+", con prioridad "+getPriority()+
                    " nombre "+getName()+" estado "+getState()+" e identificador "+
                    threadId());
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
    
            }*/
            contador.incrementa();
        }
        //System.out.println("El contador vale "+contador);
    }
}
