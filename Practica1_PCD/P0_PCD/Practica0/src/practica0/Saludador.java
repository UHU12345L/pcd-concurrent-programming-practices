/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica0;

/**
 * Esta clase se usa para explicar java para p0
 * @author Usuario
 */
public class Saludador implements Saludarable {

    private int tipo; //hay que extenderlo para que valor de i sea conocido después
    //he hecho refactor, rename porque se llamaba i y ahora tipo
    //lo puedo hacer final porque no lo voy a cambiar nunca

    Saludador(int i) {
        this.tipo = i; //esto es extender
    }

    @Override
    public void saludar(String mensaje) throws Exception {
        //añado pasar info al objeto (0/1) asi que necesito otro constructor
        if (tipo == 0) {
            System.out.println("Hola mundo");
        } else if (tipo == 1) {
            System.out.println(mensaje);
        } else {
            //System.out.println("Error, tipo no definido"); no lo pongo porq no lo se
            //lanzo una excepcion, lo que ponga debajo del throw no sale
            throw new Exception("tipo no definido");
        }
    }

}
