package proyecto3;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author Laura
 */
public class Furgo implements Callable<Integer>{
    int id;
    private Tunel tu;
    
    public Furgo(int id, Tunel tu){
        this.id=id;
        this.tu=tu;
    }
    @Override
    public Integer call(){
        Random r=new Random(System.nanoTime());
        int segundos=0;
        try {
            System.out.println("Furgo " + id + " llega");
            tu.entraFurgo(id);

            System.out.println("Furgo " + id + " esta lavando");
            segundos=r.nextInt(1,4);
            Thread.sleep(segundos*1000);

            tu.saleFurgo(id);
            System.out.println("Furgo " + id + " se marcha");
        } catch (InterruptedException ex) {
        }
        return segundos;
    }
}
