package p8_pcd;

/**
 * Cliente dibujado en el canvas: su numero y su tipo ('E' esquis, 'T' tabla).
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

    @Override
    public boolean equals(Object o) {
        Elemento e = (Elemento) o;
        return this.id == e.id;
    }
}
