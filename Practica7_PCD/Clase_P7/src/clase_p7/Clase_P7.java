package clase_p7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Laura
 */
public class Clase_P7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Threadpull es bolsa con hilos, hilos se van encargando de tareas que estan en cola
        //termina tarea, hilo coge siguiente tarea en cola
        //puedo decir a las tareas que ddevuelvan un valor (antes con el run que es void no podia)

        //crear threadpull: crear objeto de clase executor
        ExecutorService thp = Executors.newFixedThreadPool(3);

        //ANTES: 
        /*Thread[] hilos = new Thread[5];
        for(int i=0;i<5;i++){
            SinRetorno t =new SinRetorno(i);
            hilos[i]=new Thread(t);
            hilos[i].start();
        }*/
        
        /*for (int i = 0; i < 5; i++) {
            SinRetorno t = new SinRetorno(i);
            thp.submit(t);
        }*/
        //se puede hacer tmb con ArrayList
        Future<Integer>[] f=new Future[5]; //tengo q ponerlo aqui para no crearlo en el bucle y poder cogerlo fuera
        //creo 5 porq tengo 5 hilo sy quiero recoger todos los valores
        for (int i = 0; i < 5; i++) {
            ConRetorno t = new ConRetorno(i);
            //recoger referncia al sitio donde el valor va a quedar en el FUTURO para no tener q esperar a que acabe la tarea
             f[i]=thp.submit(t);
        }
        
        //formas de que no se quede esperando:
        //no es apagarlo de golpe sino decirle q no admita mas tareas y lo elimina:
        thp.shutdown();
        //intenta finalizar todo en ese instante, lo pendiente se deshecha
        //thp.shutdownNow(); //manda interruptedexception q lo cojo en el run de tarea
        
        //recoger la info que esta almacenada en un futuro, espera a que la info esté disponible y devuelve el valor
        for(int i=0;i<5;i++){
            int v=f[i].get();
             System.out.println("El valor devuelto es "+v);
        }
           
        //aparece al principio y el threadpull se queda esperando tareas, asi q apago threadpull con shutdown o shutdownnow
        System.out.println("Fin de main"); //sigue apareciendo al principio hasta que hago lo de Future
        
    }

}
