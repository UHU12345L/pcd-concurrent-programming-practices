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
        
    public ArrayList<Elemento> colaVeleros=new ArrayList<>();
    public ArrayList<Elemento> colaYates=new ArrayList<>();
    public ArrayList<Elemento> colaAmarres=new ArrayList<>();
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
        
        dibujaLista(og, "Cola veleros", colaVeleros, 20, 40,0);
        dibujaLista(og, "Cola yate", colaYates, 20, 140,0);
        dibujaLista(og, "Amarres", colaAmarres, 20, 240,6);

        //cuento cuantos veleros y yates hay amarrados recorriendo la lista de amarres
        int numVeleros=0;
        int numYates=0;
        for(int i=0;i<colaAmarres.size();i++){
            if(colaAmarres.get(i).tipo=='V'){
                numVeleros++;
            }else{
                numYates++;
            }
        }
        numYates=numYates/2;   //cada yate esta 2 veces en la lista (ocupa 2 amarres)

        og.setColor(Color.black);
        og.drawString("Veleros "+numVeleros+"/4", 400, 40);
        og.drawString("Yates "+numYates+"/2", 400, 60);
        og.drawString("Amarres libres "+(6-colaAmarres.size())+"/6", 400, 80);

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
            if(e.tipo=='V'){
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
