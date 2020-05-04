
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro david
 */
public class Reward {
    int x,y;
    int Ancho = 31;
    int Largo = 29;
    long Start;
    boolean Invisible;
    Animation Cofre;
    int NumberUser;
    public Reward(int x, int y,long Start) {
        this.x = x;
        this.y = y;
        this.Invisible=false;
        this.Start=Start;
        Cofre = new Animation();
            Image im = new ImageIcon(
                    getClass().getResource(
                            "BackgroundImages//Reward.png")).getImage();
                BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics bg = bi.getGraphics();
                bg.drawImage(im, 0, 0, null);
                bg.dispose();
                for (int k = 0; k <= 7; k++) {
                    int inix = (31*k);
                    Cofre.addScene(Cofre.SubImages(bi,inix,0,31,29),200);
                } 
        
    }
    
    public void DrawCofre(Graphics g){
         g.drawImage(Cofre.getImage(), x, y, null);
         Cofre.update(System.currentTimeMillis()-Start);
    }
    
    public void Catch(Bomberman Soldier,int Number){
        if ((Soldier.y + Soldier.yHeight) <= (y+Ancho+2) && (Soldier.y + Soldier.yHeight) >= ((y+Ancho)-5) && Soldier.x >= (x-5) && (x+Ancho+2) >= (Soldier.x +Soldier.xWidth)){
            Soldier.Cofres +=1;
            Invisible=true;
            NumberUser=Number;
        }
    }
    
    
}
