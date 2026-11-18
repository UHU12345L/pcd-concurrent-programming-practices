package p7_pcd;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Laura
 */
public class Generador {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numHilos = 50;
        Ventana f = new Ventana();
        f.setSize(800, 500);
        f.setTitle("Practica 7 - Pistas");

        MiCanvas cv = new MiCanvas();
        f.add(cv);   //meto el canvas en la ventana
        f.setVisible(true);

        Pistas pi = new Pistas(cv);
        
        ExecutorService poolInd = Executors.newFixedThreadPool(7);
        ExecutorService poolPar = Executors.newFixedThreadPool(5);
        
        Random r = new Random(System.currentTimeMillis());
        Future<Integer>[] fInd=new Future[numHilos];
        Future[] fPar=new Future[numHilos];
        
        for (int i = 0; i < numHilos; i++) {
            if (r.nextInt(100) < 60) {
                fInd[i] = poolInd.submit(new Individual(pi, i));
            } else {
                fPar[i] = poolPar.submit(new Pareja(pi, i));
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }
        
        poolInd.shutdown();
        poolPar.shutdown();
        int total=0;

       for(int i=0;i<numHilos;i++){
           if (fInd[i] != null) { //esta posicion era un individual
            total = total + fInd[i].get();  //get(): espera a que termine y me da sus segundos
        }
        if (fPar[i] != null) { //esta posicion era una pareja
            fPar[i].get(); //no devuelve nada, pero espera a que termine
        } 
        }

        System.out.println("Tiempo total de los individuales en las pistas: "+total+" segundos");
        try {
            Thread.sleep(3000);//espero 3 segundos para que se vea el estado final
        } catch (InterruptedException ex) {
        }
        System.exit(0);//cierro el programa
    }
}

