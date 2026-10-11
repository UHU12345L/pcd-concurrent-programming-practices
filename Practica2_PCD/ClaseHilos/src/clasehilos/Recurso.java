/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasehilos;

/**
 *Datos comunes para todos los hilos
 * Contador para que sea otro tipo, no int para que no pase lo de que devuelve 0
 * @author Usuario
 */
public class Recurso {
    private int contador=0;
    
    //synchronized: solo puede haber un hilo haciendo ese metodo
    //me sirve por ejemplo para evitar comflicto de x=x+1
    //he hecho exclusión mutua
    public synchronized void incrementa(){
        contador++;
    }
    
    public void vercontador(){
        System.out.println("El contador vale: "+contador);
    }
}
