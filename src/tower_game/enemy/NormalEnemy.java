/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tower_game.enemy;

import javax.swing.ImageIcon;

/**
 *
 * @author Duy
 */
public class NormalEnemy extends Enemy{

    public NormalEnemy(int x, int y, int heart, int speed, int defense, int reward) {
        super(x, y, heart, speed, defense, reward);
        image = new ImageIcon(getClass().getResource("/images/img_normalenemy.png")).getImage();
    }
   
    
}
