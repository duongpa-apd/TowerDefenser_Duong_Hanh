package tower_game.tower;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import tower_game.enemy.Enemy;
import tower_game.gui.GameEntity;

public class Tower {
    
    protected int id ;
    protected int cost ;
    protected int speed; // tốc độ bắn
    protected int limit; // tầm bắn
    protected int damage; // sát thương;
    protected int x;
    protected int y;
    
    public int attackTime ; //(timer) how long do we want the laser/ attack or stay on
    public int attackDelay ; // (timer) Pause between each attack
    
    public int maxAttackTime ;
    public int maxAttackDelay ;
    
    public Enemy target ;
    
 
    protected Image image;
 
    public Tower (int x, int y)
    {
        this.x = x ;
        this.y = y ;
    }
    
    public Tower(int x, int y, int id, int cost, int speed, int limit, int damage, int maxAttackTime, int maxAttackDelay) {
        this.x = x;
        this.y = y;
        this.id = id ;
        this.cost = cost ;
        this.speed = speed;
        this.limit = limit;
        this.damage = damage;
        this.maxAttackTime = maxAttackTime ;
        this.maxAttackDelay = maxAttackDelay;
        
        this.attackTime = 0 ;
        this.attackDelay = 0 ;

    }

    public int getX ()
    {
        return this.x ;
    }
    public int getY ()
    {
        return this.y ;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, 50, 50, null);
    }
    
    // Bắn
    public void fire(ArrayList<Bullet> arr, int x, int y)
    {
        for (Bullet bullet: arr)
        {
            if (bullet != null)
            {
                
            }
        }
    }
    
    public Enemy calculateEnemy (ArrayList<Enemy> arrEnemy, int x, int y)
    {
        ArrayList<Enemy> enemyInRange = new ArrayList<> () ;
        int towerX = (int) x / 50;
        int towerY = (int) y / 50;
        
        int towerRadius = this.limit ;
        int enemyRadius = 1 ;
        
        int enemyX ;
        int enemyY ;
        
        for (Enemy enemy :arrEnemy)
        {
            if (enemy != null)
            {
                enemyX = (int) (enemy.getX() / 50) ;
                enemyY = (int) (enemy.getY() / 50) ;
                
                int dx = enemyX - towerX ;
                int dy = enemyY - towerY ;
                
                int dradius = towerRadius + enemyRadius ;
                if (dx * dx + dy * dy < dradius * dradius)
                {
                    enemyInRange.add(enemy) ;
                }
            }
        }
        if (true)
        {
            int totalEnemy = 0 ;
            for (Enemy enemy : enemyInRange)
            {
                if (enemy != null) totalEnemy ++ ;
            }
            if (totalEnemy > 0)
            {
                int enemyInt = new Random().nextInt (totalEnemy) ;
                int enemyTaken = 0 ;
                for (Enemy enemy : enemyInRange)
                {
                    if (enemyTaken == enemyInt && enemy != null)
                    {
                        return enemy ;
                    }
                    if (enemy != null)
                    {
                        enemyTaken ++ ;
                    }
                }
            }
        }
        return null ;
    }
    
    
    public void TowerAttack (int x, int y, Enemy enemy)
    {
        enemy.setHealth(enemy.getHealth() - this.damage) ;
        for (Bullet bullet: GameEntity.arrBullet)
        {
            if (bullet == null)
            {
                GameEntity.arrBullet.add(new Bullet (x + 50 *2 , y + 50 * 3, 10, 3, enemy));
                break ;
            }
        }
    }

}
