package proyecto4;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/**
 *
 * @author Laura
 */
public class MiCanvas extends Canvas{
    //Elemento: id, tipo, equals
    //listas
    //constructor(ancho, alto): setSize y setBackground
    //mete, saca y paint van synchronized
    //mete(lista, id, tipo) con add y repaint y saca con remove(lista, id) y repaint
    
    //@Override update(Graphics g) hace paint (g)
    //@Override paint(Graphics g)
        //Image img=createImage(getWidth(), getHeight())
        //Graphics og=img.getGraphics();
        //og. setColor y fillRect fondo
        //dibujalista por cada lista
        //g.drawImage
    
    //dibujaLista(og, titulo, lista,x,y,huecos)
        //og. setColor y drawString
        //for i<huecos og.drawRect(x+i*60,y,50,50)
        //for i<lista.size Elemento e=lista.get(i), e.tipo, og.setColor
        //og.fillRect, og.setColor, og.drawRect, og.drawString
    
    //EN LAS OTRAS CLASES: 
        //private MiCanvas cv
        //pasarlo al contructor de la clase general
        //para listas hacer cv.mete y cv.saca antes de incrementar o decrementar variables y primero la lista chica y depsues la general
        //cv.mete(cola) antes del while, cv.saca(cola)+cv.mete(zona) despues del while, cv.saca(zona) en sale
    
    //Si un hilo ocupa 2 sitios, mete y saca dos veces
    //en el main: Frame, add(cv), pack, setVisible, System.exit(0)
    
    public ArrayList<Elemento> colaParticular=new ArrayList<>();
    public ArrayList<Elemento> colaHosteleria=new ArrayList<>();
    public ArrayList<Elemento> lavadoras=new ArrayList<>();
  
    public MiCanvas(int ancho, int alto){
        setSize(ancho, alto);
        setBackground(Color.white);
    }
    
    public synchronized void mete(ArrayList<Elemento> lista, int id, char tipo){
        lista.add(new Elemento(id,tipo));
        repaint();
    }
    public synchronized void saca(ArrayList<Elemento> lista, int id){
        lista.remove(new Elemento(id,' '));
        repaint();
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    @Override 
    public synchronized void paint(Graphics g){
        Image img=createImage(getWidth(), getHeight());
        Graphics og=img.getGraphics();
        
        og.setColor(Color.white);
        og.fillRect(0, 0, getWidth(), getHeight());
        
        dibujaLista(og, "Cola particulares", colaParticular, 20, 40,0);
        dibujaLista(og, "Cola hosteleria", colaHosteleria, 20, 140,0);
        dibujaLista(og, "Lavadoras", lavadoras, 20, 240,5);

        g.drawImage(img,0,0,null);
    }
    
    private void dibujaLista(Graphics og, String titulo, ArrayList<Elemento> lista, int x, int y, int huecos){
        og.setColor(Color.black);
        og.drawString(titulo, x, y-5);
        for(int i=0;i<huecos;i++){
            og.drawRect(x+i*60,y,50,50);
        }
        for(int i=0;i<lista.size();i++){
            Elemento e=lista.get(i);
            if(e.tipo=='P'){
                og.setColor(Color.cyan);
            }else{
                og.setColor(Color.orange);
            }
            og.fillRect(x+i*60,y,50,50);
            og.setColor(Color.black);
            og.drawRect(x+i*60,y,50,50);
            og.drawString(e.tipo + "" + e.id, x + i * 60 + 15, y + 30);   //texto dentro de la caja, ej. "H7"

        }
    }
}
