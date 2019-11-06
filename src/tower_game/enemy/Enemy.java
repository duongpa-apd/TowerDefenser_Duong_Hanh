package tower_game.enemy;

import tower_game.gametile.GameTile;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import tower_game.audio.SoundManager;
import tower_game.gametile.Road;
import tower_game.gametile.Target;
import tower_game.tower.Bullet;

public class Enemy {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private int x;
    private int y;
    private int heart;
    private int speed;
    private int defense;
    private int reward;
    protected Image image;
    private int orient;

    public Enemy(int x, int y, int heart, int speed, int defense, int reward) {
        this.x = x;
        this.y = y;
        this.heart = heart;
        this.speed = speed;
        this.defense = defense;
        this.reward = reward;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, 25, 25, null);
    }

    public void changeOrient(ArrayList<GameTile> arrMaps) {
        int xR, yR;
        GameTile afterRoad = null;
        switch (orient) {
            case LEFT:
                xR = x-50;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = LEFT;
                } else
                break;
            case RIGHT:
                xR = y-50;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = RIGHT;
                }
                break;
            case UP:
                yR = y-50;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = UP;
                }
                break;
            case DOWN:
                yR = y+50;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = DOWN;
                }
                break;
        }
        
    }
    
    public GameTile findMap(int x, int y, ArrayList<GameTile> arrMaps){
        GameTile result = null;
        for(GameTile map : arrMaps){
            if(map.getX()<=x && map.getY()<=y 
                    && map.getX()+50>=x && map.getY()+50>=y){
                result = map;
                break;
            }
        }
        return result;
    }
    
    public void move() {
//        int xR = x;
//        int yR = y;

        switch (orient) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
        }
        
    }
    
    public Rectangle getRect(){
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        
        Rectangle rectangle = new Rectangle(x,y,w,h);
        return rectangle;
    }
    
    // public boolean checkDie(ArrayList<Bullet> arrBullet){
    //     for(Bullet bullet : arrBullet){
    //         Rectangle rectangle = getRect().intersection(bullet.getRect());
    //         if(rectangle.isEmpty() == false){
    //             arrBullet.remove(bullet);
    //             Clip clip = SoundManager.getSound("explosion_tank.wav");
    //             clip.start();
    //             return false;
    //         }
    //     }
    //     return true;
    // }
}
