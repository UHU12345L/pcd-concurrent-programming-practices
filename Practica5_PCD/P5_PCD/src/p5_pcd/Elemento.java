package p5_pcd;

/**
 * Cliente dibujado en canvas: numero y tarjeta/efectivo
 *
 * @author Laura
 */
public class Elemento {

    int id;
    char tipo;

    public Elemento(int id, char tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    //sobreescribir equals para cuando remove busque el elemento
    @Override
    public boolean equals(Object o) {
        Elemento e = (Elemento) o;
        return this.id == e.id;
    }
}
