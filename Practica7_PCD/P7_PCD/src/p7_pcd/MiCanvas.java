package p7_pcd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Dibuja colas de espera. Sus metodos los llaman
 * muchos hilos a la vez: synchronized
 *
 * @author Laura
 */
public class MiCanvas extends Canvas {
    private ArrayList<Elemento> colaI = new ArrayList<>();
    private ArrayList<Elemento> colaP = new ArrayList<>();
    private ArrayList<Elemento> bajaI = new ArrayList<>();
    private ArrayList<Elemento> bajaP = new ArrayList<>();

    public MiCanvas() {
        this.setSize(800, 500);
        this.setBackground(Color.WHITE);
    }

    public synchronized void esperaIndividual(int id) {
        colaI.add(new Elemento(id, 'I'));
        repaint();
    }

    public synchronized void finEsperaIndividual(int id) {
        colaI.remove(new Elemento(id, 'I'));
        repaint();
    }

    public synchronized void entraBajarIndividual(int id, char sitio) {
        if (sitio == 'i') {
            bajaI.add(new Elemento(id, 'I'));
        } else {
            bajaP.add(new Elemento(id, 'I'));
        }
        repaint();
    }

    public synchronized void finBajarI(int id, char sitio) {
        if (sitio == 'i') {
            bajaI.remove(new Elemento(id, 'I'));
        } else {
            bajaP.remove(new Elemento(id, 'I'));
        }
        repaint();
    }

    public synchronized void esperaPareja(int id) {
        colaP.add(new Elemento(id, 'P'));
        repaint();
    }

    public synchronized void finEsperaPareja(int id) {
        colaP.remove(new Elemento(id, 'P'));
        repaint();
    }

    public synchronized void entraBajarPareja(int id) {
        bajaP.add(new Elemento(id, 'P'));
        repaint();
    }

    public synchronized void finBajarP(int id) {
        bajaP.remove(new Elemento(id, 'P'));
        repaint();
    }

    //DIBUJO
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public synchronized void paint(Graphics g) {
        Image img = createImage(getWidth(), getHeight());//dibujo primero en una imagen (buffer)
        Graphics og = img.getGraphics();
        og.setFont(new Font("Tahoma", Font.BOLD, 14));

        og.setColor(getBackground()); //mojar pincel en color para lo siguiente
        og.fillRect(0, 0, getWidth(), getHeight()); //fondo para tapar lo anterior

        dibujaCaja(og, "COLA INDIVIDUAL", colaI, 50, 60);
        dibujaCaja(og, "COLA PAREJA", colaP, 50, 180);
        dibujaCaja(og, "PISTA INDIVIDUAL (4)", bajaI, 50, 300);
        dibujaCaja(og, "PISTA PAREJA (2)", bajaP, 420, 300);

        //leyenda
        og.setColor(Color.BLUE);
        og.drawString("Azul = individual", 50, 450);
        og.setColor(Color.RED);
        og.drawString("Rojo = pareja", 200, 450);

        g.drawImage(img, 0, 0, null); //imagen terminada en el canvas
    }

    //Dibuja una caja con su titulo y los numeros de los clientes que hay en la lista
    private void dibujaCaja(Graphics og, String titulo, ArrayList<Elemento> lista, int x, int y) {
        og.setColor(Color.BLACK);
        og.drawString(titulo, x, y - 10); //titulo encima de la caja
        og.drawRect(x, y, 330, 50); //borde de la caja

        for (int i = 0; i < lista.size(); i++) {
            Elemento e = lista.get(i);
            if (e.tipo == 'I') {
                og.setColor(Color.BLUE);
            } else {
                og.setColor(Color.RED);
            }
            og.drawString("" + e.id, x + 10 + i * 35, y + 30);//cada numero 35 pixeles mas a la derecha
        }
    }

}
