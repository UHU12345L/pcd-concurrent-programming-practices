/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasep3;

/**
 *
 * @author Laura
 */
public class Recurso {

    private final ElCanvas cv; 
    public Recurso(ElCanvas cv) {
        this.cv=cv;
    }
    private int [] contadores={0,0};
    
    public synchronized void incrementa(int cual){
        contadores[cual]++;
        //quiero ir viendo como incrementa
        System.out.println("Los contadores van por: "+contadores[0]+" " + contadores[1]);
        //Ahora quiero q se pinte en el canva
        cv.representa(contadores[0], contadores[1]);
    }
    
    public int[] getContadores(){
        return contadores;
    }
}
