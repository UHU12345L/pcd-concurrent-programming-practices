/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clasehilos;

/**
 *
 * @author Laura
 */
public class ClaseHilos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
         Recurso contadormain=new Recurso();
        HiloThread h1 = new HiloThread(1, contadormain); //le asocio identificador (1)
        HiloThread h2 = new HiloThread(2, contadormain); //le asocio el contador para que lo compartan
        
        //El contador da el mismo numero para los dos si lo hago en su clase

        //ejecutar codigo concurrente: invocar run pero no de forma directa
        //lo que invoco es start: invoco run de forma concurrente (sirve solo para clase Thread)
        h1.start();
        h2.start();
        //tengo tres hilos: h1, h2, main
        
        /*
        for (int i = 0; i < 10; i++) {
            //System.out.println("soy la main");
            //puedo mostrar el mismo mensaje que con los hilos pero añadiendo Thread.currentThread()
            System.out.println("Soy la main con prioridad "+Thread.currentThread().getPriority()+
                    " nombre "+Thread.currentThread().getName()+" estado "+Thread.currentThread().getState()+" e identificador "+
                    Thread.currentThread().threadId());
            Thread.sleep(1000);
            
        }*/

        //Ahora quiero que un hilo espere a que otro termine, el que hace join se queda parado
        h1.join(); //join=parate (el hilo donde estes, en este caso el main) hasta que termin el hijo que te digo
        h2.join();     
        
        
        /*
        //necesito crear hilo generico (thread) para usarlo con runneable
        HiloRunnable r1=new HiloRunnable(3); //teniendo un solo runneable, solo hay un contador, ejecutan el mismo codigo los dos (ahora el contador es distinto)
        //el contador no da lo que suponemos (el doble) por el problema de x+1
        //HiloRunnable r2=new HiloRunnable(4);
        Thread h3=new Thread(r1); //thread asocia el run suyo al run del runnable
        Thread h4=new Thread(r1);  
        h3.start();
        h4.start();
        
        //para esperar que termine el thread creado con runneable tengo que hacerlo con el thread
        //h1.join();
        //h2.join();
        h3.join();
        h4.join();
*/
       contadormain.vercontador(); 
        System.out.println("Fin de main");
    }

}
