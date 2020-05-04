

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
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
public class Bomberman {
    public static final int UP=0;
    public static final int RIGTH=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    public static final int NONE=-1;
    Animation Celebrando;
    Animation[] aniWalking,Sacando,Disparando,Guardando,aniRespiration,Muriendo;
    public int x;
    public int UserN;
    public int y;
    public int vx;
    public int vy;
    public int xWidth = 27;
    public int yHeight = 38;
    public int Life;
    String path;
    int currentDirection;
    int LastDir;
    int direction;
    int State;
    int Cofres;
    long Shot;
    long TiempoShot;
    boolean Dead;
    boolean Shotting;
    AudioStream audio;
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    

    public Bomberman(int x, int y, int vx, int vy, String path,int UserN) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.path = path;
        this.Cofres=0;
        this.UserN=UserN;
        this.currentDirection=LEFT;
        this.direction=NONE;
        aniRespiration= new Animation[2];
        aniWalking= new Animation[6];
        Sacando= new Animation[2];
        Disparando= new Animation[2];
        Guardando= new Animation[2];
        Muriendo = new Animation[2];
        this.Shotting=false;
        this.Dead = false;
        this.Life=9;
    }
    
    public void loadPic() throws FileNotFoundException, IOException{
        String sonido = "src/Sonidos//Disparo.wav";
        InputStream in = new FileInputStream(sonido);
        audio = new AudioStream(in);
        //Respirando
        String name[]={"R1I","R1D"};
        int[] SubSprites = {3,3};
        for (int j = 0; j <= 1; j++) {
            aniRespiration[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+name[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSprites[j]; k++) {
                    int inix;
                    if (j==0) {
                        inix = (0 + (27)*k);
                    }else{
                        inix = 102 - ((27)*k);
                    }
                    aniRespiration[j].addScene(aniRespiration[j].SubImages(bi,inix,0,27,38),200);
                } 
        }
       //Sacando Arma
        String nameSA[]={"SA1D","SA1I"};
        int[] SubSpritesSA = {7,7};
        for (int j = 0; j <= 1; j++) {
            Sacando[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+nameSA[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSpritesSA[j]; k++) {
                     int inix=0;
                    if(j==0){
                        inix = 325-(46*k);
                    }else{
                        inix = (46*k);
                    }
                    Sacando[j].addScene(Sacando[j].SubImages(bi,inix,0,46,44),20);
                } 
        }
        //Disparando
        String nameD[]={"D1D","D1I"};
        int[] SubSpritesD = {2,2};
        for (int j = 0; j <= 1; j++) {
            Disparando[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+nameD[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSpritesD[j]; k++) {
                     int inix=0;
                    if(j==0){
                        inix = (61*k);
                    }else{
                        inix = 124-(61*k);
                    }
                    Disparando[j].addScene(Disparando[j].SubImages(bi,inix,0,61,38),50);
                } 
        }
        //Guardando
        String nameGA[]={"GA1D","GA1I"};
        int[] SubSpritesGA = {2,2};
        for (int j = 0; j <= 1; j++) {
            Guardando[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+nameGA[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSpritesGA[j]; k++) {
                     int inix=0;
                    if(j==0){
                        inix = 83-(37*k);
                    }else{
                        inix = (37*k);
                    }
                    Guardando[j].addScene(Guardando[j].SubImages(bi,inix,0,37,47),20);
                } 
        }
        //Caminando
        String nameC[]={"S1I","C1D","DOWN1I","C1I","S1D","DOWN1D"};
        int[] SubSpritesC = {4,11,0,11,4,0};
        for (int j = 0; j < 6; j++) {
            aniWalking[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+nameC[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSpritesC[j]; k++) {
                    int inix=0,WidthX=0,Heigth=0;
                    switch(j){
                        case 0:{
                            WidthX = 29;
                            Heigth=44;
                            inix = (0 + (29)*k);
                        break;
                        }
                        case 1:{
                            WidthX = 26;
                            Heigth=40;
                            inix = 80 + ((26)*(k));
                        break;
                        }
                        case 2:{
                            WidthX = 29;
                            Heigth=44;
                            inix = 0;
                        break;
                        }
                        case 3:{
                            WidthX = 26;
                            Heigth = 40;
                            inix = 286 - ((26)*k);
                        break;
                        }
                        case 4:{
                            WidthX = 29;
                            Heigth = 44;
                            inix = 131 - ((29)*k);
                        break;
                        }
                        case 5:{
                            WidthX = 29;
                            Heigth=44;
                            inix = 0;
                        break;
                        }
                        
                    }
                    aniWalking[j].addScene(aniWalking[j].SubImages(bi,inix,0,WidthX,Heigth),50);
                } 
        }
        //Muriendo
        String nameM[]={"M1I","M1D"};
        int[] SubSpritesM = {10,10};
        for (int j = 0; j <= 1; j++) {
            Muriendo[j] = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//"+nameM[j]+".png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= SubSpritesM[j]; k++) {
                    int inix;
                    if (j==0) {
                        inix = (0 + (31)*k);
                    }else{
                        inix = 365 - ((31)*k);
                    }
                    Muriendo[j].addScene(Muriendo[j].SubImages(bi,inix,0,31,40),200);
                } 
        }
        //Celebrando
        Celebrando = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            path+"//Celebration.png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= 5; k++) {
                    int inix = (26*k);
                    Celebrando.addScene(Celebrando.SubImages(bi,inix,0,26,39),200);
                } 
    }
    
    public synchronized  void draw(Graphics g){
        switch (State){
            case 0:{
                switch(LastDir){
                     case LEFT:{
                         g.drawImage(aniRespiration[0].getImage(), x, y, null);         
                        break;
                    }
                    case RIGTH:{
                         g.drawImage(aniRespiration[1].getImage(), x, y, null);                   
                        break;
                    }
                }
                
                break;
            }
            case 1:{
                switch(currentDirection){
                     case LEFT:{
                         g.drawImage(aniWalking[currentDirection].getImage(), x, y-2, null);         
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(aniWalking[currentDirection].getImage(), x, y-2, null);                  
                        break;
                    }
                    case UP:{
                        switch(LastDir){
                            case LEFT:{
                                g.drawImage(aniWalking[0].getImage(), x, y, null);          
                               break;
                           }
                           case RIGTH:{
                                g.drawImage(aniWalking[4].getImage(), x, y, null);                      
                               break;
                           }
                       }
                        break;
                    }
                    case DOWN:{
                        switch(LastDir){
                            case LEFT:{
                                g.drawImage(aniWalking[2].getImage(), x, y, null);                
                               break;
                           }
                           case RIGTH:{
                                g.drawImage(aniWalking[5].getImage(), x, y, null);                           
                               break;
                           }
                       }
                        break;
                    }
                }
                
                break;
            }
            case 2:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Sacando[1].getImage(), x-5, y-4, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Sacando[0].getImage(), x-5, y-4, null);                 
                        break;
                    }
                }
                
                break;
            }
             case 3:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Disparando[1].getImage(), x-5, y, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Disparando[0].getImage(), x-5, y, null);                 
                        break;
                    }
                }
             break;
             }
              case 4:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Guardando[1].getImage(), x-5, y-7, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Guardando[0].getImage(), x-5, y-7, null);                 
                        break;
                    }
                }
                break;
              }
            case 5:{
                switch (LastDir){
                    case LEFT:{
                        g.drawImage(Muriendo[0].getImage(), x, y-7, null); 
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Muriendo[1].getImage(), x, y-7, null); 
                        break;
                    }
                }
            break;
            }
            case 6:{
                g.drawImage(Celebrando.getImage(), x, y-2, null); 
            break;
            }
            case 7:{
                switch (LastDir){
                    case LEFT:{
                        g.drawImage(Muriendo[0].getImage(), x, y-4, null); 
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Muriendo[1].getImage(), x, y-4, null); 
                        break;
                    }
                }
            break;
            }
            
        }
       
    }
    
    public synchronized  void draw(Graphics g,String Stats) throws InterruptedException{
        String[] Mov = Stats.split(":");
         /*if(direction!=NONE){
            animations[currentDirection].update(Integer.parseInt(Mov[3]) - Principal.start);
         }*/
         x = Integer.parseInt(Mov[1]);
         y =Integer.parseInt(Mov[2]);
         direction = Integer.parseInt(Mov[4]);
         State = Integer.parseInt(Mov[5]);
         LastDir = Integer.parseInt(Mov[6]);
         TiempoShot = Long.parseLong(Mov[7]);
         Life = Integer.parseInt(Mov[8]);
         Cofres = Integer.parseInt(Mov[9]);
         move(Long.parseLong(Mov[3]) - Long.parseLong(Mov[10]));
         switch (State){
            case 0:{
                 switch(LastDir){
                     case LEFT:{
                         g.drawImage(aniRespiration[0].getImage(), x, y, null);         
                        break;
                    }
                    case RIGTH:{
                         g.drawImage(aniRespiration[1].getImage(), x, y, null);                   
                        break;
                    }
                }
                break;
            }
            case 1:{
                switch(currentDirection){
                     case LEFT:{
                         g.drawImage(aniWalking[currentDirection].getImage(), x, y-2, null);         
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(aniWalking[currentDirection].getImage(), x, y-2, null);                  
                        break;
                    }
                    case UP:{
                        switch(LastDir){
                            case LEFT:{
                                g.drawImage(aniWalking[0].getImage(), x, y, null);          
                               break;
                           }
                           case RIGTH:{
                                g.drawImage(aniWalking[4].getImage(), x, y, null);                      
                               break;
                           }
                       }
                        break;
                    }
                    case DOWN:{
                        switch(LastDir){
                            case LEFT:{
                                g.drawImage(aniWalking[2].getImage(), x, y, null);                
                               break;
                           }
                           case RIGTH:{
                                g.drawImage(aniWalking[5].getImage(), x, y, null);                           
                               break;
                           }
                       }
                        break;
                    }
                }
                
                break;
            }
             case 2:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Sacando[1].getImage(), x-5, y-4, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Sacando[0].getImage(), x-5, y-4, null);                 
                        break;
                    }
                }
                
                break;
            }
             case 3:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Disparando[1].getImage(), x-5, y, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Disparando[0].getImage(), x-5, y, null);                 
                        break;
                    }
                }
             break;
             }
              case 4:{
                switch(LastDir){
                     case LEFT:{
                        g.drawImage(Guardando[1].getImage(), x-5, y-7, null);          
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Guardando[0].getImage(), x-5, y-7, null);                 
                        break;
                    }
                }
                break;
              }
              case 5:{

                switch (LastDir){
                    case LEFT:{
                        g.drawImage(Muriendo[0].getImage(), x, y-7, null); 
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Muriendo[1].getImage(), x, y-7, null); 
                        break;
                    }
                }
            break;
            }
            case 6:{
                g.drawImage(Celebrando.getImage(), x, y-2, null); 
            break;
            }
            case 7:{
                switch (LastDir){
                    case LEFT:{
                        g.drawImage(Muriendo[0].getImage(), x, y-4, null); 
                        break;
                    }
                    case RIGTH:{
                        g.drawImage(Muriendo[1].getImage(), x, y-4, null); 
                        break;
                    }
                }
            break;
            }
        }
        
    }
    
    
    public synchronized void  move(long time) throws InterruptedException{
        switch (State){
            case 0:{
                switch(LastDir){
                     case LEFT:{
                         aniRespiration[0].update(time);        
                        break;
                    }
                    case RIGTH:{
                        aniRespiration[1].update(time);         
                        break;
                    }
                }
                
                break;
            }
            case 1:{
                if(direction!=NONE)
                     currentDirection=direction;
                switch (direction){
                    case LEFT:{
                        x-=vx;    
                        LastDir=LEFT;
                        break;
                    }
                    case RIGTH:{
                        x+=vx;     
                        LastDir=RIGTH;
                        break;
                    }
                    case DOWN:{
                        y+=vy;                
                        break;
                    }
                    case UP:{
                        y-=vy;
                        break;
                    }


                }
                if(direction!=NONE)
                   aniWalking[currentDirection].update(time); 
                break;
                }
            case 2:{
                TiempoShot = System.currentTimeMillis()-Shot;
                switch (LastDir){
                    case LEFT:{
                        Sacando[1].update(TiempoShot);  
                        if (TiempoShot > Sacando[1].EndTime()) {
                            Shot = System.currentTimeMillis();
                            State=3;
                        }
                        break;
                    }
                    case RIGTH:{
                        Sacando[0].update(TiempoShot);
                        if (TiempoShot > Sacando[0].EndTime()) {
                            Shot = System.currentTimeMillis();
                            State=3;
                           
                        }
                        break;
                    }
                }

               break;
            }
            case 3:{
                if (UserN==1) {
                    TiempoShot = System.currentTimeMillis()-Shot;   
                }
                switch (LastDir){
                    case LEFT:{
                        Disparando[1].update(TiempoShot);  
                        if (TiempoShot > (Disparando[1].EndTime()*3)) {
                            Principal.Disparos.add(new Shot(x-30,y+10,30,5,-1));
                            Shot = System.currentTimeMillis();
                            State=4;
                        }else{
                            if (TiempoShot > (Disparando[1].EndTime()*2) && TiempoShot< ((Disparando[1].EndTime()*2)+5)) {
                                Principal.Disparos.add(new Shot(x-30,y+10,30,5,-1));
                            }else{
                                if (TiempoShot > (Disparando[1].EndTime()) && TiempoShot< ((Disparando[1].EndTime())+5) ) {
                                    Principal.Disparos.add(new Shot(x-30,y+10,30,5,-1));
                                }
                            }
                        }
                        break;
                    }
                    case RIGTH:{
                        Disparando[0].update(TiempoShot);
                        AudioPlayer.player.start(audio);
                          if (TiempoShot > (Disparando[1].EndTime()*3)) {
                            Principal.Disparos.add(new Shot(x+30,y+10,30,5,1));
                            Shot = System.currentTimeMillis();
                            State=4;
                            AudioPlayer.player.stop(audio);
                        }else{
                            if (TiempoShot >= (Disparando[1].EndTime()*2) && TiempoShot< ((Disparando[1].EndTime()*2)+6)) {
                                Principal.Disparos.add(new Shot(x+30,y+10,30,5,1));
                            }else{
                                if (TiempoShot >= (Disparando[1].EndTime()) && TiempoShot< ((Disparando[1].EndTime())+6) ) {
                                    Principal.Disparos.add(new Shot(x+30,y+10,30,5,1));
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 4:{
                if (UserN==1) {
                    TiempoShot = System.currentTimeMillis()-Shot;   
                }
                switch (LastDir){
                    case LEFT:{
                        Guardando[1].update(TiempoShot);  
                        if (TiempoShot > Guardando[1].EndTime()) {
                            State=0;
                            Shotting=false;
                        }
                        break;
                    }
                    case RIGTH:{
                        Guardando[0].update(TiempoShot);
                        if (TiempoShot > Guardando[0].EndTime()) {
                            State=0;
                            Shotting=false;
                        }
                        break;
                    }
                }
               break;
            }
            case 5:{
                if (UserN==1) {
                    TiempoShot = System.currentTimeMillis()-Shot;   
                }
                switch (LastDir){
                    case LEFT:{
                        Muriendo[0].update(TiempoShot);  
                        if (TiempoShot > Muriendo[0].EndTime()) {
                            State = 6;
                            Dead = false;
                            Cofres = 0;
                            y = 407; 
                            State = 0;
                            Life = 9;
                        }
                        break;
                    }
                    case RIGTH:{
                        Muriendo[1].update(TiempoShot);
                        if (TiempoShot > Muriendo[1].EndTime()) {
                            State = 6;
                            y = 407;
                            Cofres = 0;
                            Dead = false;
                            State = 0;
                            Life = 9;
                        }
                        break;
                    }
                }
            break;
            }
            case 6:{
                Celebrando.update(time);
            break;
            }
            case 7:{
                switch (LastDir){
                    case LEFT:{
                        Muriendo[0].update(time);
                        break;
                    }
                    case RIGTH:{
                        Muriendo[1].update(time);
                        break;
                    }
                }
            break;
            }
            }
       /**/
           
    }
    
    public synchronized String Position(){
        return currentDirection + ":" + x + ":" + y + ":" + System.currentTimeMillis() + ":" + direction + ":" + State + ":" + LastDir + ":" + TiempoShot + ":" + Life + ":" + Cofres +  ":" + Principal.start + ":" + Shot;
    }
    
    
}
