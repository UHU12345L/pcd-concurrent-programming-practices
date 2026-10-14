/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasep3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Es una clase q referencia un objeto q se mete dentro de un frame para poder pintar
 * @author Laura
 */
public class ElCanvas extends Canvas{
    int contador0=0;
    int contador1=0;
    
    public ElCanvas(int ancho, int alto){
        //cdo creo objeto le asocio tamaño, color de fondo, posicion
        this.setSize(ancho,alto);
        this.setBackground(Color.PINK);
        //this.setLocation(50,50);
    }
    
    public void representa(int valor0, int valor1){
        //aqui podria invocar al paint, pero de esta forma me van a decir el valor de los contadores
        contador0=valor0;
        contador1=valor1;
        
        //me pintaba solo un valor, quiero que se actualicen los valores y se invoque al paint
        repaint(); //obtiene graficos y llama update, update borra canvas y hace paint. 
        //reescribo update para que no borre el canvas y no parpadee
    }
    
    @Override
    public void update(Graphics g){
        paint(g); //el problema es que de esta forma escribe un numero encima de otro
        //solucion: pintar el fondo tmb para que no se vea lo de debajo cdo escribo un nº encima del otro
    }
    
    @Override
    public void paint(Graphics g){
        Font fuente1=new Font("Segoe Script", Font.BOLD | Font.ITALIC, 20);
        Font fuente2=new Font("Tahoma", Font.ITALIC, 20);
        //pintar el fondo tmb para que no se vea lo de debajo cdo escribo un nº encima del otro
        Image img =createImage(getWidth(),getHeight());
        Graphics og=img.getGraphics(); //asi puedo dibujar graficos en la imagen
        //entonces ahora tengo q escribir siempre en og
        
        og.setFont(fuente1);
        og.setColor(Color.BLUE);
        og.fillRect(150, 80, 30, 20);
        og.drawString("El contador 0 vale "+contador0, 200, 100);
        og.setFont(fuente2);
        og.setColor(Color.BLACK);
        og.fillOval(150, 180, 30, 20);
        og.drawString("El contador 1 vale "+contador1, 200, 200);
           
        //poner la imagen en el canvas
        g.drawImage(img,0,0,null);
    }
}
