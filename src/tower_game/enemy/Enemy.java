package tower_game.enemy;

import tower_game.gametile.GameTile;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import tower_game.audio.SoundManager;
import tower_game.gametile.Road;
import tower_game.gametile.Spawner;
import tower_game.gametile.Target;
import tower_game.gui.GameStage;
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
        if(orient == LEFT){
            System.out.println("orient_LEFT(" + x +"," + y +")");
        } else if(orient == RIGHT){
            System.out.println("orient_RIGHT(" + x +"," + y +")");
        }else if(orient == DOWN){
            System.out.println("orient_DOWN(" + x +"," + y +")");
        }else if(orient == UP){
            System.out.println("orient_UP(" + x +"," + y +")");
        }
        if(x<-50 || y<-50) return;
        switch (orient) {
            case LEFT:
                afterRoad = findMap(x,y,arrMaps);
                if(x<= afterRoad.getX() + (50-image.getWidth(null)/2)){
                    return;
                }
                xR = x-25;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = LEFT;
                } else {
                    afterRoad = findMap(x,y+50,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = DOWN;
                    } else {
                        orient = UP;
                    }
                }
                break;
            case RIGHT:
                xR = x+50;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Spawner){
                    return;
                } else if(x<=findMap(x,y,arrMaps).getX() + (50-image.getWidth(null)/2)){
                    return;
                } else if(afterRoad instanceof Road || afterRoad instanceof Target){
                    return;
                } else {
                    afterRoad = findMap(x,y+50,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = DOWN;
                    } else {
                        orient = UP;
                    }
                }
                break;
            case UP:
                afterRoad = findMap(x,y,arrMaps);
                if(y<=afterRoad.getY() + (50-image.getHeight(null)/2)){
                    return;
                }
                yR = y-25;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = UP;
                } else {
                    afterRoad = findMap(x+50,y,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = RIGHT;
                    } else {
                        orient = LEFT;
                    }
                }
                break;
            case DOWN:
                afterRoad = findMap(x,y,arrMaps);
                if(y<=afterRoad.getY() + (50-image.getHeight(null)/2)){
                    return;
                }
                yR = y+50;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = DOWN;
                } else {
                    afterRoad = findMap(x+50,y,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = RIGHT;
                    } else {
                        orient = LEFT;
                    }
                }
                break;
            default:
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
    
    public void setOrient(int orient){
        this.orient = orient;
    }
    
    public boolean checkFinish(ArrayList<GameTile> arrMaps){
        GameTile target = null;
        for(GameTile map : arrMaps){
            if(map instanceof Target){
                target = map;
                break;
            }
        }
        if(x>=target.getX() && y >=target.getY()
                && x<target.getX()+25 && y<target.getY()+25){
            return true;
        }
        return false;
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
