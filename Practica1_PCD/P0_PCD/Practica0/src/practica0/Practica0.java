/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica0;

import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Practica0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // System.out.println("Hola Mundo"); 
        //sout  
        //fori  
        //ctrl+espacio: autocompletado
        
        Saludador s=new Saludador(1); // 0/1 es para elegir el tipo
        
        //hacer Ran ctrl+espacio, elegir tipo, java util pa q ponga cabecera
        Random r= new Random(System.currentTimeMillis()); //o nanotime para hilos
        
        int i=r.nextInt(0,1);
        
        try{
          s.saludar("Hola mundo desde un objeto");  
        } catch (Exception ex){
            System.out.println("Salta la excepcion "+ex.getMessage());
        }finally{
            //que esto ocurra siempre
            System.out.println("Sale siempre");
        }
        System.out.println("Fin de la main");
        
    }
    
}
