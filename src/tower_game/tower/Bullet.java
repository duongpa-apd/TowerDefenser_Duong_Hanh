package tower_game.tower;

import tower_game.gui.GameStage;
import java.awt.*;
import javax.swing.ImageIcon;
import tower_game.enemy.Enemy;

public class Bullet {
    private int speedFire;
    private int limit;
    private int damage;
    public int x;
    public int y;
    public double orient ;
    public Enemy target ;
    private Image image = new ImageIcon(getClass().getResource(
            "/images/bullet.png")).getImage();
    
    public Bullet(int x, int y, int speedFire, int limit, int damage) {
        this.x = x - image.getWidth(null) / 2;
        this.y = y - image.getHeight(null) / 2;
        this.speedFire = speedFire;
        this.limit = limit;
        this.damage = damage;
    }
    public Bullet (int x, int y, int speedFire, int damage, int limit, Enemy target)
    {
        this.x = x ;
        this.y = y ;
        this.speedFire = speedFire ;
        this.damage = damage ;
        this.limit = limit ;
        this.target = target ;
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, this.x, this.y, 15, 15, null);
    }
    
    public boolean move(){
        // Dịch chuyển đạn
        
        if (x <= 0 || y <= 0 || x >= GameStage.W_FRAME || y >= GameStage.H_FRAME 
                || x >= x+limit || x <= x-limit
                || y >= y+limit || y <= y-limit) {
            return false;
        }
        return true;
    }
    
    public Rectangle getRect(){
        Rectangle rectangle = new Rectangle(x,y,image.getWidth(null)
                ,image.getHeight(null));
        return rectangle;
    }
    
}
