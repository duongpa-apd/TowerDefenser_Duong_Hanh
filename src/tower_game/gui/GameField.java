package tower_game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import tower_game.enemy.BossEnemy;
import tower_game.enemy.Enemy;
import tower_game.enemy.NormalEnemy;
import tower_game.enemy.SmallerEnemy;
import tower_game.enemy.TankerEnemy;
import tower_game.tower.MachineGunTower;

public class GameField extends JPanel implements Runnable
{   
    public GameEntity gameEntity = new GameEntity(this);
    public GameStage gameStage ;
    public boolean isRunning = true ;
    public boolean isWaiting = true ;
    public boolean Starting = false ;
    public boolean isExiting = false ;
    public int bitSet = 0;
    public GameField (GameStage gameStage)
    {
        this.gameStage = gameStage ;
        this.gameStage.setFocusable(true);
        this.gameStage.addKeyListener(this.gameEntity.new KeyHandler ());
        this.gameStage.addMouseListener(this.gameEntity.new MouseHandler ());
        this.gameStage.addMouseMotionListener(this.gameEntity.new MouseHandler ());
        
        setFocusable(true);
        setBackground(Color.LIGHT_GRAY);
        gameEntity.initGame();
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
        if (Starting == true )
        {
                Image start_img = new ImageIcon(getClass().getResource("/images/01_Background_Games.png")).getImage();
                g2d.drawImage(start_img, 0, 0, gameStage.getWidth(), gameStage.getHeight(), null);
                gameEntity.draw(g2d);
        }
        else 
        {
                Image start_img = new ImageIcon(getClass().getResource("/images/start_background.png")).getImage();
                g2d.drawImage(start_img, 0, 0, gameStage.getWidth(), gameStage.getHeight(), null);
        }
        if (isExiting == true)
        {
            Image exit_img = new ImageIcon(getClass().getResource("/images/exit_background.png")).getImage();
            int centerXPos = this.gameStage.getWidth() / 2 ;
            int centerYPos = this.gameStage.getHeight()/ 2 ;
            g2d.drawImage(exit_img, centerXPos - 500 /2, centerYPos - 300 /2, 500, 300, null) ;
        }
    }
    @Override
    public void run() {
        
        while (isRunning){
            
            if (isWaiting == false ) gameEntity.AI();
            this.gameEntity.towerUpdate();
            this.gameEntity.enemyUpdate(); 
            this.gameEntity.bulletUpdate();
            
            if (this.gameEntity.getHeart() <= 0) {
                System.out.print("Game Over!");
               isExiting = true ;
            }
            
            repaint();
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
        System.exit(0) ;
    }
}
