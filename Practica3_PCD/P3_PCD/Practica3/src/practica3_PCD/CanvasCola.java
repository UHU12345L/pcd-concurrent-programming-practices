/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica3_PCD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Canvas;

/**
 * Zona de dibujo, muestra el estado de la cola
 * la cola cambia, llama a representa/avisa, llama a repaint, java llama a update, que llama a paint
 * @author Laura
 */
public class CanvasCola extends Canvas{
    private int head;
    private int tail;
    private int capacidad;
    private int numelementos;
    private Object[] datos;
    private String mensaje;
    
    //capacidad: nº casillas q se dibujarán
    public CanvasCola(int capacidad){
        this.capacidad=capacidad;
       this.datos = new Object[capacidad];
        this.head = 0;
        this.tail = 0;
        this.numelementos = 0;
        this.mensaje = "";
        this.setSize(600, 400); // tamaño del canvas
        this.setBackground(Color.PINK);  
    }
    
    // Dibuja la cola. Se dibuja primero en una imagen aparte (buffer)
    // y al final se pega entera en el canvas, para que no se vea a trozos.
    @Override
    public void paint(Graphics g){
        Font fuente1=new Font("Tahoma", Font.ITALIC, 20);
        //pintar el fondo tmb para que no se vea lo de debajo cdo escribo un nº encima del otro
        Image img =createImage(getWidth(),getHeight());
        Graphics og=img.getGraphics(); //asi puedo dibujar graficos en la imagen
        //entonces ahora tengo q escribir siempre en og
        
        og.setFont(fuente1);
        
        //titulo
        og.setColor(getBackground());
        og.fillRect(0, 0, getWidth(), getHeight());
        
        //titulo
        og.setColor(Color.BLACK);
        og.drawString("Elementos de la cola "+numelementos, 50, 50);
        
          // Una casilla por cada posición de la cola
          //50 es el margen izq
          //100 es el ancho de cada casilla
        for (int i = 0; i < capacidad; i++) {
            int x = 50 + i * 100;   // cada casilla, 100 píxeles más a la derecha

            if (datos[i] != null) {   // si hay un elemento, la casilla se rellena de amarillo
                og.setColor(Color.YELLOW);
                og.fillRect(x, 150, 100, 50);
                og.setColor(Color.BLACK);
                og.drawString("" + datos[i], x + 10, 180);   // "" + convierte el número en texto
            }
            og.setColor(Color.BLACK);
            og.drawRect(x, 150, 100, 50);   // borde de la casilla
        }

        // Dónde apuntan head (debajo) y tail (encima)
      
        og.setColor(Color.BLUE);
        og.drawString("HEAD", 50 + head * 100 + 30, 230);
        og.setColor(Color.MAGENTA);
        og.drawString("TAIL", 50 + tail * 100 + 30, 135);

        // Aviso de cola llena o vacía
        og.setColor(Color.RED);
        og.drawString(mensaje, 50, 300);
        
        //poner la imagen en el canvas
        g.drawImage(img,0,0,null);
    }
    
    //el update original borra el canvas antes de pintar y parpadea
    @Override
    public void update(Graphics g){
         paint(g);
    }
    
    //la cola lo llama cdo está llena o vacia
    public void avisa(String mensaje){
        this.mensaje=mensaje;
        repaint();
    }
    
    //la cola lo llama cada vez q cambia. guarda el estado y lo redibuja
    public void representa(Object[] buf, int head, int tail, int numele){
        this.datos = buf;
        this.head = head;
        this.tail = tail;
        this.numelementos = numele;
        this.mensaje = "";   // si la cola ha cambiado, quito el aviso anterior
        repaint();
    }
}
