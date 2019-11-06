package tower_game.gametile;

import javax.swing.ImageIcon;

public class EmptyBox extends GameTile{

    public EmptyBox(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/img_emptybox.png")).getImage();
    }
    
}
