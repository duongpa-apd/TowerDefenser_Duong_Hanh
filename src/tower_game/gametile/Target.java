package tower_game.gametile;

import javax.swing.ImageIcon;

public class Target extends GameTile {

    public Target(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/img_target.png")).getImage();
    }
    
}
