package p6_pcd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Laura
 */
public class MiCanvas extends Canvas {

    private ArrayList<Integer> colaSubir = new ArrayList<>();
    private ArrayList<Integer> colaBajar = new ArrayList<>();
    private ArrayList<Integer> tramoSubir = new ArrayList<>();
    private ArrayList<Integer> tramoBajar = new ArrayList<>();

    public MiCanvas() {
        this.setSize(800, 450);
        this.setBackground(Color.WHITE);
    }

    public synchronized void esperaSubir(int id) {
        colaSubir.add(id);
        repaint();
    }

    public synchronized void entraSubir(int id) {
        colaSubir.remove((Integer) id);
        tramoSubir.add(id);
        repaint();
    }

    public synchronized void saleSubir(int id) {
        tramoSubir.remove((Integer) id);
        repaint();   //redibujo para que desaparezca de la escalera
    }

    public synchronized void esperaBajar(int id) {
        colaBajar.add(id);
        repaint();
    }

    public synchronized void entraBajar(int id) {
        colaBajar.remove((Integer) id);
        tramoBajar.add(id);
        repaint();
    }

    public synchronized void saleBajar(int id) {
        tramoBajar.remove((Integer) id);
        repaint();   //redibujo para que desaparezca de la escalera
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
        og.fillRect(0, 0, getWidth(), getHeight()); //fondo para tapar lo anterior

        dibujaCaja(og, "COLA SUBIR", colaSubir, 50, 60);
        dibujaCaja(og, "COLA BAJAR", colaBajar, 420, 60);
        dibujaCaja(og, "TRAMO SUBIDA (" + tramoSubir.size() + "/4)", tramoSubir, 50, 200);
        dibujaCaja(og, "TRAMO BAJADA (" + tramoBajar.size() + "/4)", tramoBajar, 420, 200);

        og.setColor(Color.BLACK);
        og.drawString("TOTAL EN LA ESCALERA: " + (tramoSubir.size() + tramoBajar.size()) + "/7", 50, 330);

        g.drawImage(img, 0, 0, null);   //pego la imagen terminada en el canvas
    }

    //Dibuja una caja con su titulo y los numeros de los clientes de la lista
    private void dibujaCaja(Graphics og, String titulo, ArrayList<Integer> lista, int x, int y) {
        og.setColor(Color.BLACK);
        og.drawString(titulo, x, y - 10);   //titulo encima de la caja
        og.drawRect(x, y, 330, 50);         //borde de la caja

        for (int i = 0; i < lista.size(); i++) {
            og.drawString("" + lista.get(i), x + 10 + i * 35, y + 30);   //cada numero 35 pixeles a la derecha
        }
    }

}
