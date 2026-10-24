package practica4_PCD;

/**
 * cabecera de todos los metodos que deben tener las clases 
 * (que sean intercambiables con otra clase de este interface)

 * @author Laura
 */
public interface ICola {
    /**
     * devuelve cuantos elementos hay en la cola
     * @return elementos almacenados
     */
    int getNum();
    
    /**
     * mete elementos al final de la cola
     * @param elemento lo que inserto
     * @throws Exception si la cola esta llena
     */
    void acola(Object elemento) throws Exception;
    
    /**
     * Devuelve primer elemento y lo extrae
     * @return
     * @throws Exception si la cola esta vacia
     */
    Object desacola() throws Exception;
    
    /**
     * devuelve el primer elemento sin sacarlo
     * @return el elemento
     * @throws Exception si la cola esta vacia
     */
    Object primero() throws Exception;
    
}
