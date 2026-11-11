package proyecto2;

import java.util.ArrayList;

/**
 *
 * @author Laura
 */
public class Puerto {

    public int amarresLibres = 6;      //amarres libres
    public int veleros = 0;            //veleros amarrados (max 4)
    public int yates = 0;              //yates amarrados (max 2), cuenta YATES, no amarres
    public int esperandoVeleros = 0;   //veleros esperando: para la prioridad
    public int velerosSeguidos=0;
    public int esperandoYates=0;
    public synchronized void entraVelero(int id) throws InterruptedException {
        esperandoVeleros++;
        while (amarresLibres == 0 || veleros == 4|| (esperandoYates>0 && yates < 2 &&velerosSeguidos>=3)) {
            wait();
        }
        if(esperandoYates!=0)velerosSeguidos++;
        
        amarresLibres--;
        veleros++;
        esperandoVeleros--;
        System.out.println("Velero " + id + " amarra, quedan " + amarresLibres + " amarres libres");

    }

    public synchronized void saleVelero(int id) throws InterruptedException {
        veleros--;
        amarresLibres++;
        System.out.println("Velero " + id + " sale, quedan " + amarresLibres + " amarres libres");
        notifyAll();
    }

    public synchronized void entraYate(int id) throws InterruptedException {
        esperandoYates++;

        while (amarresLibres < 2 || yates == 2|| (esperandoVeleros != 0 && veleros < 4 &&velerosSeguidos<3) ) {
            wait();
        }
       if(velerosSeguidos>=3){
             System.out.println("Yate " + id + " amarra, por prioridad");

       }
        velerosSeguidos=0;
        notifyAll();
        amarresLibres = amarresLibres - 2;
        yates++;
        esperandoYates--;

        System.out.println("Yate " + id + " amarra, quedan " + amarresLibres + " amarres libres");

    }

    public synchronized void saleYate(int id) throws InterruptedException {
        yates--;
        amarresLibres = amarresLibres + 2;
        System.out.println("Yate " + id + " sale, quedan " + amarresLibres + " amarres libres");
        notifyAll();
    }

}
