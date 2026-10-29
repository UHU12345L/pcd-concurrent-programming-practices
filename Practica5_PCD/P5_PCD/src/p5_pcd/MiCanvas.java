package p5_pcd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Dibuja colas de espera y puntos de venta ocupados Sus metodos los llaman
 * muchos hilos a la vez: synchronized
 *
 * @author Laura
 */
public class MiCanvas extends Canvas {

    private ArrayList<Elemento> colaT = new ArrayList<>();   //clientes de tarjeta esperando
    private ArrayList<Elemento> colaE = new ArrayList<>();   //clientes de efectivo esperando
    private ArrayList<Elemento> pagaT = new ArrayList<>();   //pagando en puntos de solo tarjeta
    private ArrayList<Elemento> pagaE = new ArrayList<>();   //pagando en puntos de efectivo (de los 2 tipos)

    public MiCanvas() {
        this.setSize(800, 500);
        this.setBackground(Color.WHITE);
    }

    //TARJETA
    public synchronized void esperaTarjeta(int id) {
        colaT.add(new Elemento(id, 'T'));
        repaint();
    }

    public synchronized void finEsperaTarjeta(int id) {
        colaT.remove(new Elemento(id, 'T'));
        repaint();
    }

    public synchronized void entraPagarTarjeta(int id, char sitio) {
        if (sitio == 't') {
            pagaT.add(new Elemento(id, 'T'));
        } else {
            pagaE.add(new Elemento(id, 'T')); //es de tarjeta en un punto de efectivo
        }
        repaint();
    }

    public synchronized void finPagarT(int id, char sitio) {
        if (sitio == 't') {
            pagaT.remove(new Elemento(id, 'T'));
        } else {
            pagaE.remove(new Elemento(id, 'T'));
        }
        repaint();
    }

    //EFECTIVO
    public synchronized void esperaEfectivo(int id) {
        colaE.add(new Elemento(id, 'E'));
        repaint();
    }

    public synchronized void finEsperaEfectivo(int id) {
        colaE.remove(new Elemento(id, 'E'));
        repaint();
    }

    public synchronized void entraPagarEfectivo(int id) {
        pagaE.add(new Elemento(id, 'E'));
        repaint();
    }

    public synchronized void finPagarE(int id) {
        pagaE.remove(new Elemento(id, 'E'));
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

        dibujaCaja(og, "COLA TARJETA", colaT, 50, 60);
        dibujaCaja(og, "COLA EFECTIVO", colaE, 50, 180);
        dibujaCaja(og, "PAGO TARJETA (2 puntos)", pagaT, 50, 300);
        dibujaCaja(og, "PAGO EFECTIVO (2 puntos)", pagaE, 420, 300);

        //leyenda
        og.setColor(Color.BLUE);
        og.drawString("Azul = tarjeta", 50, 450);
        og.setColor(Color.RED);
        og.drawString("Rojo = efectivo", 200, 450);

        g.drawImage(img, 0, 0, null); //imagen terminada en el canvas
    }

    //Dibuja una caja con su titulo y los numeros de los clientes que hay en la lista
    private void dibujaCaja(Graphics og, String titulo, ArrayList<Elemento> lista, int x, int y) {
        og.setColor(Color.BLACK);
        og.drawString(titulo, x, y - 10); //titulo encima de la caja
        og.drawRect(x, y, 330, 50); //borde de la caja

        for (int i = 0; i < lista.size(); i++) {
            Elemento e = lista.get(i);
            if (e.tipo == 'T') {
                og.setColor(Color.BLUE);
            } else {
                og.setColor(Color.RED);
            }
            og.drawString("" + e.id, x + 10 + i * 35, y + 30);//cada numero 35 pixeles mas a la derecha
        }
    }

}
