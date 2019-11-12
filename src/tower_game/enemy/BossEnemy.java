package tower_game.enemy;

import javax.swing.ImageIcon;

public class BossEnemy extends Enemy {

    public BossEnemy(int x, int y) {       
        super(x, y, 1, 1, 10, 10);
        image = new ImageIcon(getClass().getResource("/images/img_bossenemy.png")).getImage();
    }
    
}
