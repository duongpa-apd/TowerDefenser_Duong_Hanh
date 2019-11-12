package tower_game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameField extends JPanel implements Runnable
{   
    private GameEntity gameEntity = new GameEntity();
    public boolean isRunning = true ;
    public int bitSet = 0;
    public GameField ()
    {
        setBackground(Color.BLACK);
        gameEntity.initGame();
        setFocusable(true);
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
        gameEntity.draw(g2d);
    }

    
    @Override
    public void run() {
        while (isRunning){
            gameEntity.AI();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
