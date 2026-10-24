/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clasep4;

/**
 *
 * @author Laura
 */
public class ClaseP4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread[]hilos=new Thread[5]; //tengo un array de hilos
        Ordenador o= new Ordenador(0); //crea variable turno en la clase ordenador, le da el valor 0, asi paso la referencia porq asi no hago mas new
        //creo 5 hilos
        for(int i=0;i<5;i++){
            hilos[i]=new Hilo(i,o);
        }
        for(int i=0; i<5;i++){
            hilos[i].start();
        }
         for(int i=0; i<5;i++){
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                System.getLogger(ClaseP4.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
    
}
