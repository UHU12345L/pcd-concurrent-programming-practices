package proyecto4;

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
private MiCanvas cv;

public Puerto( MiCanvas cv){
    this.cv=cv;
}
  public synchronized void entraVelero(int id) throws InterruptedException{
     cv.mete(cv.colaVeleros,id,'V');
      esperandoVeleros++;
      while(amarresLibres==0 || veleros==4) {       
          wait();
      }
       cv.saca(cv.colaVeleros,id);
       cv.mete(cv.colaAmarres,id,'V');
      amarresLibres--;
      veleros++;
      esperandoVeleros--;
      System.out.println("Velero " + id + " amarra, quedan " + amarresLibres + " amarres libres");

  }
  
  public synchronized void saleVelero(int id) throws InterruptedException{
      cv.saca(cv.colaAmarres,id);
      veleros--;
      amarresLibres++;
      System.out.println("Velero " + id + " sale, quedan " + amarresLibres + " amarres libres");
      notifyAll();
  }
  
  public synchronized void entraYate(int id) throws InterruptedException{
      cv.mete(cv.colaYates,id,'Y');
      cv.mete(cv.colaYates,id,'Y');
      while(amarresLibres<2 || yates==2 || (esperandoVeleros !=0 && veleros<4)) wait();
       cv.saca(cv.colaYates,id);
        cv.saca(cv.colaYates,id);
        cv.mete(cv.colaAmarres,id,'Y');
        cv.mete(cv.colaAmarres,id,'Y');
      amarresLibres=amarresLibres-2;
      yates++;
      System.out.println("Yate " + id + " amarra, quedan " + amarresLibres + " amarres libres");

  }
  
  public synchronized void saleYate(int id) throws InterruptedException{
      cv.saca(cv.colaAmarres,id);
      cv.saca(cv.colaAmarres,id);
      yates--;
      amarresLibres=amarresLibres+2;
      System.out.println("Yate " + id + " sale, quedan " + amarresLibres + " amarres libres");
      notifyAll();
  }
    
}
