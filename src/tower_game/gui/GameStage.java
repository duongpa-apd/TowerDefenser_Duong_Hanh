package tower_game.gui;

import java.awt.Component;
import javax.swing.JFrame;


public class GameStage extends JFrame 
{
    public static final int H_FRAME = 589;
    public static final int W_FRAME = 765;
    private final GameField gameField;
    
    public GameStage ()
    {
        this.setSize(W_FRAME, H_FRAME);
        this.setTitle("TowerDefense!");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameField = new GameField (this) ;
        this.add(gameField) ;
        setVisible(true);
 
    }
    
    
}
