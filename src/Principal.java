


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lgguzman
 */
public class Principal extends JFrame{
    public Bomberman j1;
    public Bomberman j2;
    public Thread th;
    public Canvas c;
    public Background B;
    static long start;
    MediaPlayer mediaPlayer;
    Image Fondo;
    int Usuario;
    boolean Inicio = true;
    boolean EndGame = false;
    Font font;
    static long StartShot;
    static ArrayList<Shot> Disparos = new ArrayList<>();
    static ArrayList<Reward> Cofres = new ArrayList<>();
    String Name;
    public void iniKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) { 
                
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_LEFT:{
                        if(!EndGame){
                        if (j1.Shotting==false && !j1.Dead) {
                            j1.State=1;
                            j1.setDirection(Bomberman.LEFT);
                        }
                        }
                        break;
                    }
                    case KeyEvent.VK_RIGHT:{
                        if(!EndGame){
                        if (j1.Shotting==false && !j1.Dead) {
                            j1.State=1;
                            j1.setDirection(Bomberman.RIGTH);                          
                        }
                        }
                        break;
                    }
                    case KeyEvent.VK_UP:{
                        if(!EndGame){
                        if (j1.Shotting==false && !j1.Dead) {
                                j1.State=1;
                                j1.setDirection(Bomberman.UP);
                        }
                        }
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        if(!EndGame){
                        if (j1.Shotting==false && !j1.Dead) {
                            j1.State=1;
                            j1.setDirection(Bomberman.DOWN);
                        }
                        }
                        break;
                    }
                    case KeyEvent.VK_SPACE:{
                        if(!EndGame){
                        if (j1.Shotting==false && !j1.Dead) {
                            j1.State=2;
                            j1.Shotting=true;
                            j1.Shot = System.currentTimeMillis();
                        }
                        }
                    break;
                    }
                    case KeyEvent.VK_ENTER:{
                        if (EndGame) {
                            dispose();
                        }
                    break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_LEFT:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_UP:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                }
            }
        });
    }
    
    public Principal(String nombre){
        setSize(513,510);
        c=new Canvas();
        Name = nombre;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(c,BorderLayout.CENTER);
        iniKeyListener();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                c.createBufferStrategy(2);
                start=System.currentTimeMillis();
                font = new Font("Courier New",Font.BOLD,200);
                try {
                    InputStream is = TypeName.class.getResourceAsStream("Font//GROBOLD.ttf");
                    font = Font.createFont(Font.TRUETYPE_FONT, is);
                    font = font.deriveFont(Font.PLAIN,25);

                } catch (IOException|FontFormatException e) {
                                 //Handle exception
                }
                while(true){
                    try{
                        Graphics g= c.getBufferStrategy().getDrawGraphics();
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());
                        if (Client.Ready) {
                            B.DrawBack(g);
                            g.drawImage(Fondo, 0, 0, null);
                            j1.move(System.currentTimeMillis()-start);                      
                            B.LimitMap(j1);
                            g.setFont(font);
                            g.setColor(Color.WHITE);
                            if (Usuario==0) {
                                g.drawString(Name,30,50);
                                g.drawString(j1.Life+"",30,80);
                                g.drawString(Client.message.split("%")[0],400,50);
                                g.drawString(j2.Life+"",400,80);
                            }else{
                                g.drawString(Name,400,50);
                                g.drawString(j1.Life + "",400,80);
                                g.drawString(Client.message.split("%")[0],30,50);
                                g.drawString(j2.Life + "",30,80);
                            }
                            g.drawString("X" + j1.Cofres ,264,50);
                            if (EndGame) {
                                font =font.deriveFont(Font.PLAIN,50);
                                g.setFont(font);
                                g.setColor(Color.RED);
                                g.drawString("THE END",50,250);
                                font =font.deriveFont(Font.PLAIN,20);
                                g.setFont(font);
                                g.drawString("press enter to continue...",20,380);
                            }
                            if (Disparos.size()>0) {
                                for (Shot Disparo : Disparos) {
                                    Disparo.DrawShot(g);
                                    Disparo.Daño(j1);
                                    Disparo.Daño(j2);
                                    if (Disparo.Destroy){
                                       Disparos.remove(Disparo);
                                    }
                            }
                            }
                            if (Cofres.size()>0) {
                                for (Reward Cofre : Cofres) {
                                    if (!Cofre.Invisible) {
                                        Cofre.DrawCofre(g);
                                        Cofre.Catch(j1,Usuario);
                                        if (Usuario==0) {
                                            Cofre.Catch(j2,1);
                                        }else{
                                            Cofre.Catch(j2,0);
                                        }
                                        
                                    }
                                }
                            }
                            if (Tomados() && j1.Cofres==Cofres.size() && EndGame==false) {
                                EndGame = true;
                                j1.State = 6;
                            }else{
                                if (Tomados() && j1.Cofres==0 && EndGame == false) {
                                    EndGame = true;
                                    j1.State=7;
                                }
                            }
                            if (j1.Life<0 && j1.Dead==false) {
                                j1.State = 5;
                                j1.Dead = true;
                                j1.Shot=System.currentTimeMillis();
                            }else{
                                j1.draw(g);
                            }
                            if (j1.Dead) {
                                for (Reward Cofre : Cofres) {
                                    if (Cofre.Invisible) {
                                        if (Cofre.NumberUser==Usuario) {
                                            Cofre.Invisible=false;
                                        }
                                    }
                                }
                            }else{
                                int Otro = 0;
                                if (Usuario==0) {
                                    Otro=1;
                                }
                                if (j2.State==5) {
                                    for (Reward Cofre : Cofres) {
                                        if (Cofre.Invisible) {
                                            if (Cofre.NumberUser==Otro) {
                                                Cofre.Invisible=false;
                                            }
                                        }
                                    }
                                }
                            }
                            
                            j2.draw(g,Client.message.split("%")[1]);
                        }else{
                            g.setFont(font);
                            g.setColor(Color.WHITE);
                            g.drawString("Usuarios en espera  :D", 20,150);
                            g.drawString(Client.message,50,250);
                                if (Client.message.equals("Ready")) {
                                    Client.Ready = true;
                                }
                            }
                        c.getBufferStrategy().show();
                        Thread.sleep(10);
                    }catch(Exception e){}
                }
            }
        });
    }
    
    public void DefUsers(int number) throws FileNotFoundException, IOException{
        int inix1 = 100,inix2 = 350;
        Usuario = number;
        B = new Background();
        String sonido = "src/Sonidos//Stage1.wav";
        InputStream in = new FileInputStream(sonido);
        AudioStream audio = new AudioStream(in);
        AudioPlayer.player.start(audio);
        DefReward();
        if (number  == 0) {
            j1= new Bomberman(inix1, 407, 3, 3, "SoldierGreen",1);
            j2= new Bomberman(inix2, 407, 3, 3, "SoldierGray",2);
            j1.LastDir=Bomberman.RIGTH;
        }else{
            j1= new Bomberman(inix2, 407, 3, 3, "SoldierGray",1);
            j2= new Bomberman(inix1, 407, 3, 3, "SoldierGreen",2);
            j1.LastDir=Bomberman.LEFT;
        }
        j1.loadPic();
        j2.loadPic();
        Inicio=false;
    }
    
    public void DefReward(){
        int[] Cx = {50,340,45};
        int[] Cy = {417,164,164};
        for (int i = 0; i < 3; i++) {
            Cofres.add(new Reward(Cx[i],Cy[i],System.currentTimeMillis()));
        }
    
    }
    public boolean Tomados(){
        boolean Tomados=false;
        int Q = 0;
        for (Reward Cofre : Cofres) {
            if (Cofre.Invisible) {
                Q = Q +1;
            }
        }
        if (Q == Cofres.size()) {
            Tomados=true;
        }
        return Tomados;
    }
}
