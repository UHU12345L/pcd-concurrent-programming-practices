package p8_pcd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Dibuja la cola de botas y las 4 zonas (botas, traje, esquis, tabla).
 * Un cliente que ha terminado en una zona pero espera sitio en la siguiente
 * se sigue viendo en su zona, entre corchetes: [7].
 * Todos los metodos son synchronized porque los llaman muchos hilos a la vez.
 *
 * @author Laura
 */
public class MiCanvas extends Canvas {

    private ArrayList<Elemento> colaBotas = new ArrayList<>();
    private ArrayList<Elemento> botas = new ArrayList<>();
    private ArrayList<Elemento> traje = new ArrayList<>();
    private ArrayList<Elemento> esquis = new ArrayList<>();
    private ArrayList<Elemento> tabla = new ArrayList<>();
    private ArrayList<Integer> esperanAvanzar = new ArrayList<>(); //han terminado su zona y esperan la siguiente

    public MiCanvas() {
        this.setSize(900, 500);
        this.setBackground(Color.WHITE);
    }

    public synchronized void esperaBotas(int id, char tipo) {
        colaBotas.add(new Elemento(id, tipo));
        repaint();
    }

    public synchronized void entraBotas(int id, char tipo) {
        colaBotas.remove(new Elemento(id, tipo)); //remove busca el que tenga ese id y usa equals
        botas.add(new Elemento(id, tipo));
        repaint();
    }

    public synchronized void saleBotas(int id) {
        botas.remove(new Elemento(id, ' '));
        repaint();
    }

    public synchronized void esperaTraje(int id) {
        esperanAvanzar.add(id);
        repaint();
    }

    public synchronized void entraTraje(int id, char tipo) {
        esperanAvanzar.remove((Integer) id);
        traje.add(new Elemento(id, tipo));
        repaint();
    }

    public synchronized void saleTraje(int id) {
        traje.remove(new Elemento(id, ' '));
        repaint();
    }

    public synchronized void esperaSiguiente(int id) {
        esperanAvanzar.add(id);
        repaint();
    }

    public synchronized void entraEsquis(int id) {
        esperanAvanzar.remove((Integer) id);
        esquis.add(new Elemento(id, 'E'));
        repaint();
    }

    public synchronized void saleEsquis(int id) {
        esquis.remove(new Elemento(id, 'E'));
        repaint();
    }

    public synchronized void entraTabla(int id) {
        esperanAvanzar.remove((Integer) id);
        tabla.add(new Elemento(id, 'T'));
        repaint();
    }

    public synchronized void saleTabla(int id) {
        tabla.remove(new Elemento(id, 'T'));
        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public synchronized void paint(Graphics g) {
        Image img = createImage(getWidth(), getHeight());//dibujo primero en una imagen (buffer)
        Graphics og = img.getGraphics();
        og.setFont(new Font("Tahoma", Font.BOLD, 14));

        og.setColor(getBackground());
        og.fillRect(0, 0, getWidth(), getHeight()); //fondo, para tapar lo anterior

        //cuento cuantos de cada tipo hay en la cola de botas (para ver la preferencia)
        int colaE = 0;
        int colaT = 0;
        for (int i = 0; i < colaBotas.size(); i++) {
            if (colaBotas.get(i).tipo == 'E') {
                colaE++;
            } else {
                colaT++;
            }
        }

        dibujaCaja(og, "COLA BOTAS (esquis: " + colaE + ", tabla: " + colaT + ")", colaBotas, 50, 60, 800);
        dibujaCaja(og, "BOTAS (2)", botas, 50, 180, 180);
        dibujaCaja(og, "TRAJE (2)", traje, 260, 180, 180);
        dibujaCaja(og, "ESQUIS (2)", esquis, 470, 140, 180);
        dibujaCaja(og, "TABLA (2)", tabla, 470, 260, 180);

        og.setColor(Color.BLUE);
        og.drawString("Azul = esquis", 50, 400);
        og.setColor(Color.RED);
        og.drawString("Rojo = tabla", 200, 400);
        og.setColor(Color.BLACK);
        og.drawString("[n] = ha terminado y espera sitio en la siguiente zona", 50, 430);

        g.drawImage(img, 0, 0, null);
    }

    //Dibuja una caja con su titulo y los clientes de la lista
    private void dibujaCaja(Graphics og, String titulo, ArrayList<Elemento> lista, int x, int y, int ancho) {
        og.setColor(Color.BLACK);
        og.drawString(titulo, x, y - 10);//titulo encima de la caja
        og.drawRect(x, y, ancho, 50);//borde de la caja

        for (int i = 0; i < lista.size(); i++) {
            Elemento e = lista.get(i);
            if (e.tipo == 'E') {
                og.setColor(Color.BLUE);
            } else {
                og.setColor(Color.RED);
            }
            String texto = "" + e.id;
            if (esperanAvanzar.contains(e.id)) {
                texto = "[" + e.id + "]";
            }
            og.drawString(texto, x + 10 + i * 45, y + 30);
        }
    }
}
