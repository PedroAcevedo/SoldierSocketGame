
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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
public final class Background {
    Image Fondo,Cofre;
    String Suelo,Plataforma;
    int[] Px = {322,65,0,257,129,0,321};
    int[] Py = {386,386,322,322,257,193,193,446};
    ArrayList<Integer> Pisos = new ArrayList<>();
    
    Block[] Superficie;
    Block[] Plataformas;
    public Background() {
          this.Fondo = new ImageIcon(getClass().getResource("BackgroundImages//Background1.png")).getImage();
          this.Cofre = new ImageIcon(getClass().getResource("BackgroundImages//Cofresito.png")).getImage();
          this.Suelo = "floor";
          this.Plataforma = "Plataform";
          this.Superficie = new Block[8];
          this.Plataformas = new Block[8];
          this.DefEscenario();
    }
    
    public void DefEscenario(){
        for (int i = 0; i < 8; i++) {
            Superficie[i] = new Block(0 + (64*i),446,64,26,Suelo);
        }
        for (int i = 0; i < 7; i++) {
            Plataformas[i] = new Block(Px[i],Py[i],192,16,Plataforma);
        }
        for (int i = 0; i < 7; i++) {
            Pisos.add(Px[i]);
        }
        Pisos.add(446);
    }
    
    public void LimitMap(Bomberman Soldier){
        Boolean Colision=false; 
        for (int i = 0; i < 8; i++) {
             Colision = Superficie[i].Limites(Soldier);
        if (i<7) {
             Colision = Plataformas[i].LimitesP(Soldier);
        }  
        }
        if (Soldier.x<0) {
            Soldier.x = Soldier.x + Soldier.vx;
        }
        if (Soldier.x>498) {
            Soldier.x = Soldier.x - Soldier.vx;
        }
        if (Colision==false) {
            if (Soldier.currentDirection != Bomberman.UP && Soldier.currentDirection != Bomberman.DOWN) {
                Soldier.y = Soldier.y+Soldier.vy;
            }
        }
    }
    
    public void DrawBack(Graphics g){
       g.drawImage(Fondo, 0, 0, null);
       g.drawImage(Cofre, 240, 24, null);
        for (int i = 0; i < 8; i++) {
            Superficie[i].DrawBlock(g);
             if (i<7) {
                Plataformas[i].DrawBlock(g);
             }
        }
    }
    
    
   
    
}
