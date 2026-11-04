package proyecto4;

/**
 *
 * @author Laura
 */
public class Elemento {
    int id;
    char tipo;
    
    public Elemento(int id, char tipo){
        this.id=id;
        this.tipo=tipo;
    }
    
    @Override
    public boolean equals(Object o){
        Elemento otro=(Elemento)o;
        return id==otro.id;
    }
}
