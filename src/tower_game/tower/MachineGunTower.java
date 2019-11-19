package tower_game.tower;

import javax.swing.ImageIcon;

public class MachineGunTower extends Tower {

    public MachineGunTower(int x, int y, int id, int cost, int speed, int limit, int damage,int maxAttackTime, int maxAttackDelay) {
        super(x, y, id, cost, speed, limit, damage, maxAttackTime, maxAttackDelay);
        image = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage();
    }
    
     public MachineGunTower (int x, int y)
    {
        super(x, y) ;
        image = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage();
        this.id = 2 ;
        this.cost = 100 ;
        this.speed =2 ;
        this.limit = 3;
        this.damage = 1 ;
        this.maxAttackTime = 6 ;
        this.maxAttackDelay = 9 ;
    }


}
