package tower_game.tower;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Tower {
    
    private int speed; // tốc độ bắn
    private int limit; // tầm bắn
    private int damage; // sát thương;
    private int x;
    private int y;
    protected Image image;

    public Tower(int x, int y, int speed, int limit, int damage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.limit = limit;
        this.damage = damage;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, null);
    }
    
    // Bắn
    public void fire(ArrayList<Bullet> arr){
        
    }
    
    
    
    
    
}
