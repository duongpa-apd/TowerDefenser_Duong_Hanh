package tower_game.tower;

import javax.swing.ImageIcon;

public class SniperTower extends Tower{
    
    public SniperTower(int x, int y, int id, int cost , int speed, int limit, int damage,int maxAttackTime, int maxAttackDelay) {
        super(x, y, id, cost, speed, limit, damage, maxAttackTime, maxAttackDelay);
        image = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage();
    }
    
     public SniperTower (int x, int y)
    {
        super(x, y) ;
        this.image = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage();
        this.id = 1 ;
        this.cost = 60 ;
        this.speed =2 ;
        this.limit = 3;
        this.damage = 1 ;
        this.maxAttackTime = 6 ;
        this.maxAttackDelay = 9 ;
    }
    
    
    
}
