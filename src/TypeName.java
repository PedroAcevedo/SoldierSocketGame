
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdacevedo
 */
public class TypeName extends JFrame{
   
    public Thread th;
    public Canvas c;
    static long start;
    static String Name="";
    static boolean End = false;
    public void iniKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                switch(ke.getKeyCode()){
                   case KeyEvent.VK_BACK_SPACE:{
                      Name = Name.substring(0,Name.length()-1);
                      break;
                    }
                    case KeyEvent.VK_ENTER:{
                        End = true;
                        
                    break;
                   }
                }
                if(Character.isAlphabetic(ke.getKeyChar())) {
                        Name =  Name + ke.getKeyChar();
                }
                
            }


            @Override
            public void keyReleased(KeyEvent ke) {
                switch(ke.getKeyCode()){
                   
                }
            }
        });
    }
    
    public TypeName(){
        setSize(800,600);
        c=new Canvas();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(c,BorderLayout.CENTER);
        iniKeyListener();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                c.createBufferStrategy(2);
                start=System.currentTimeMillis();
                while(true){
                    try{
                        Graphics g= c.getBufferStrategy().getDrawGraphics();
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());
                        String Mundo = "TYPE YOUR NAME:";
                         g.setColor(Color.WHITE);
                         Font font = new Font("Courier New",Font.BOLD,200);
                         try {
                         InputStream is = TypeName.class.getResourceAsStream("Font//GROBOLD.ttf");
                         font = Font.createFont(Font.TRUETYPE_FONT, is);
                         font = font.deriveFont(Font.PLAIN,50);
                            
                        } catch (IOException|FontFormatException e) {
                         //Handle exception
                        }
                        g.setFont(font);
                        g.drawString(Mundo,20,55);
                        font = font.deriveFont(Font.PLAIN,70);
                        g.setFont(font);
                        g.drawString(Name,35,400);  
                        c.getBufferStrategy().show();
                        synchronized(th){
                            if (End) {
                                th.notify();
                                dispose();
                            }
                          }
                        Thread.sleep(10);
                    }catch(Exception e){}
                }
            }
        });
        
    }

    
}
