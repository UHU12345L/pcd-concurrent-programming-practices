package proyecto1;

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

  public synchronized void entraVelero(int id) throws InterruptedException{
     esperandoVeleros++;
      while(amarresLibres==0 || veleros==4) {
          
          wait();
      }
         
      amarresLibres--;
      veleros++;
      esperandoVeleros--;
      System.out.println("Velero " + id + " amarra, quedan " + amarresLibres + " amarres libres");

  }
  
  public synchronized void saleVelero(int id) throws InterruptedException{
      veleros--;
      amarresLibres++;
      System.out.println("Velero " + id + " sale, quedan " + amarresLibres + " amarres libres");
      notifyAll();
  }
  
  public synchronized void entraYate(int id) throws InterruptedException{
      while(amarresLibres<2 || yates==2 || (esperandoVeleros !=0 && veleros<4)) wait();
      
      amarresLibres=amarresLibres-2;
      yates++;
      System.out.println("Yate " + id + " amarra, quedan " + amarresLibres + " amarres libres");

  }
  
  public synchronized void saleYate(int id) throws InterruptedException{
      yates--;
      amarresLibres=amarresLibres+2;
      System.out.println("Yate " + id + " sale, quedan " + amarresLibres + " amarres libres");
      notifyAll();
  }
    
}
