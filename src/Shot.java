
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdacevedo
 */
public class Shot {
    int x,y,Ancho,Alto,vx,Direction;
    Image Shot;
    boolean Destroy;
    public Shot(int x, int y, int Ancho, int Alto, int Direction) {
        this.x = x;
        this.y = y;
        this.Destroy=false;
        this.vx = 3;
        this.Ancho = Ancho;
        this.Direction = Direction;
        this.Alto = Alto;
        if (Direction==1) {
            this.Shot = new ImageIcon(getClass().getResource("BackgroundImages//ShotD.png")).getImage();
        }else{
            this.Shot = new ImageIcon(getClass().getResource("BackgroundImages//ShotI.png")).getImage();
        }
        
    }
    
    public synchronized  void DrawShot(Graphics g){
        g.drawImage(Shot, x, y, null);
        x = x + Direction*vx;
    }
    
    public synchronized void Daño(Bomberman Soldier){
        if (x > Soldier.x && x < (Soldier.xWidth+Soldier.x) && y > Soldier.y && y < (Soldier.yHeight+Soldier.y)) {
                Destroy = true;
                Soldier.Life-=1;
        }
    }
    
    
}
