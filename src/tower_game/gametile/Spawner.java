package tower_game.gametile;

import javax.swing.ImageIcon;

public class Spawner extends GameTile{

    public Spawner(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/img_spawner.png")).getImage();
    }
    
    
    
}
