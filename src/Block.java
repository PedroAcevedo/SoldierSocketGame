
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
 * @author pedro david
 */
public class Block {
    int x,y,Ancho,Alto,vy,vx;
    Image Block;
    String Nombre;
    public Block(int x, int y, int Ancho, int Alto,String Nombre) {
        this.x = x;
        this.y = y;
        this.Ancho = Ancho;
        this.Alto = Alto;
        this.Nombre = Nombre;
        this.vy = y + Alto;
        this.vx = x + Ancho;
        this.Block = new ImageIcon(getClass().getResource("BackgroundImages//"+this.Nombre+".png")).getImage();
    }
    
    public void DrawBlock(Graphics g){
        g.drawImage(Block, x, y, null);
    }
    
    public boolean Limites(Bomberman Soldier){   
        boolean SwIn=false;
        if (Soldier.yHeight + Soldier.y > y) {
           Soldier.y = Soldier.y - Soldier.vy;
            if (Soldier.State== 1 && (Soldier.currentDirection == Bomberman.UP || Soldier.currentDirection==Bomberman.DOWN)) {
                Soldier.State=0;
            }
           SwIn = true;
        }
        return SwIn;
    }
    
    public boolean LimitesP(Bomberman Soldier){   
        boolean SwIn=false;
        if (Soldier.yHeight + Soldier.y > y && Soldier.y < y && Soldier.x >= x && Soldier.x < (x+Ancho)) {
           Soldier.y = Soldier.y - Soldier.vy;
            if (Soldier.State== 1 && (Soldier.currentDirection == Bomberman.UP || Soldier.currentDirection==Bomberman.DOWN)) {
                Soldier.State=0;
            }
           SwIn = true;
        }
        return SwIn;
    }
    
    
}
