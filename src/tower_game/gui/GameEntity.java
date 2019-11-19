package tower_game.gui;

import java.awt.Color;
import java.awt.Graphics;
import tower_game.enemy.Enemy;
import tower_game.gametile.GameTile;
import tower_game.tower.Bullet;
import tower_game.tower.Tower;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import tower_game.enemy.BossEnemy;
import tower_game.enemy.NormalEnemy;
import tower_game.enemy.SmallerEnemy;
import tower_game.enemy.TankerEnemy;
import tower_game.gametile.EmptyBox;
import tower_game.gametile.Mountain;
import tower_game.gametile.Road;
import tower_game.gametile.Spawner;
import tower_game.gametile.Target;
import tower_game.gametile.Tree;
import tower_game.tower.MachineGunTower;
import tower_game.tower.NormalTower;
import tower_game.tower.SniperTower;

public class GameEntity {
    public ArrayList<Tower> arrTower;
    public static ArrayList<Bullet> arrBullet;
    public ArrayList<Enemy> arrEnemy;
    public ArrayList<GameTile> arrMaps;
    private int heart;
    private int coin;
    private int bit;
    private int orderRow;
    
    public GameField gameField ;
    public boolean isBuying = false ;
    public boolean placeTower = true ;
    public boolean isDisplaying = true ;
    public int mouseXPos = 0 ;
    public int mouseYPos = 0 ;
    
    public int mapRow = 0 ;
    public int mapColumn = 0 ;
    public Tower [] towerList = new Tower [3] ;
    public int id = -1 ;
    
    public GameEntity (GameField gameField)
    {
        this.gameField = gameField ;
    }
    
    //Bắt đầu màn chơi
    public void initGame(){
        heart = 20;
        bit = 0;
        coin = 200;
        orderRow = 0;
        arrBullet = new ArrayList<>(10);
        arrEnemy = new ArrayList<>();
        arrTower = new ArrayList<>();
        readMap("mapData.txt");
        defineMenuTower();
        initBoss();
    }
    
    public void initBoss(){
        int orient = start();
        GameTile spawer = getSpawner();
        int x = spawer.getX();
        int y = spawer.getY();
        int orientStart = start();
        File file = new File("src/maps/boss.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String line = reader.readLine();
            while (line!=null){
                if(row == orderRow){
                    String[] arr = line.split(" ");
                    int numberEnemy = Integer.parseInt(arr[0]);
                    if(numberEnemy>0){
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    arrEnemy.add(new NormalEnemy(x,y));
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    arrEnemy.add(new NormalEnemy(x,y));
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    arrEnemy.add(new NormalEnemy(x,y));
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    arrEnemy.add(new NormalEnemy(x,y));  
                                    break;
                            }
                        }
                    }
                    
                    numberEnemy = Integer.parseInt(arr[1]);
                    if(numberEnemy>0){
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    arrEnemy.add(new SmallerEnemy(x,y));
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    arrEnemy.add(new SmallerEnemy(x,y));
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    arrEnemy.add(new SmallerEnemy(x,y));
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    arrEnemy.add(new SmallerEnemy(x,y));  
                                    break;
                            }
                        }
                    }
                    
                    numberEnemy = Integer.parseInt(arr[2]);
                    if(numberEnemy>0){
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    arrEnemy.add(new TankerEnemy(x,y));
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    arrEnemy.add(new TankerEnemy(x,y));
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    arrEnemy.add(new TankerEnemy(x,y));
                                    break;
                                case Enemy.DOWN: 
                                    y = y -100;
                                    arrEnemy.add(new TankerEnemy(x,y));  
                                    break;
                            }
                        }
                    }
                    numberEnemy = Integer.parseInt(arr[3]);
                    if(numberEnemy>0){
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    arrEnemy.add(new BossEnemy(x,y));
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    arrEnemy.add(new BossEnemy(x,y));
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    arrEnemy.add(new BossEnemy(x,y));
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    arrEnemy.add(new BossEnemy(x,y));  
                                    break;
                            }
                        }
                    }
                    break;
                }
                row++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Enemy e : arrEnemy){
            e.setOrient(orientStart);
        }
    }
    
    public void readMap(String map){
        arrMaps = new ArrayList<>();
        File file = new File("src/maps/"+map);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String line = reader.readLine();
            while (line!=null){
                for (int i = 0;i<line.length();i++){
                    int x = i * 50;
                    int y = row * 50;
                    int bit = Integer.parseInt(line.charAt(i)+"");
                    mapColumn ++ ;
                    switch(bit){
                        case 0 :
                            arrMaps.add(new Mountain(x,y));
                            break;
                        case 1 :
                            arrMaps.add(new Road(x,y));
                            break;
                        case 2 :
                            arrMaps.add(new Spawner(x,y));
                            break;
                        case 3 :
                            arrMaps.add(new Target(x,y));
                            break;
                        case 4 :
                            arrMaps.add(new EmptyBox(x,y));
                            break;
                        case 5 :
                            arrMaps.add(new Tree(x,y));
                            break;
                    }
                    
                }
                row++;
                line = reader.readLine();
                mapRow ++ ;
            
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
            mapColumn = mapColumn / mapRow ;
            System.out.print(mapColumn + "  " + mapRow);
    }
    
    public void draw(Graphics2D g2d){
        try{
            for (GameTile map:arrMaps) {
                map.draw(g2d);
            }
            if (this.gameField.isWaiting == true)
            {
                if (isBuying == false && isDisplaying == true )
                {
                    Image buying_menu = new ImageIcon(getClass().getResource("/images/buyingMenu.png")).getImage();
                    g2d.drawImage(buying_menu, 0, this.gameField.gameStage.getHeight() - buying_menu.getHeight(null) / 2 - 30, 360, 70, null) ;
                    for (int i = 0 ; i < 3 ; i ++)
                    {
                        towerList[i].draw(g2d);
                    }
                }
            }
            if (isBuying == true )
            {
                isDisplaying = false ;
                Image tower_img ;
                switch (id)
                {
                    case 0: tower_img = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage(); break ;
                    case 1: tower_img = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage(); break ;
                    case 2: tower_img = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage(); break ;
                    default : tower_img = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage(); break ;
                }
                
                g2d.drawImage(tower_img, mouseXPos - 50 /2, mouseYPos - 50 /2 , 50, 50, null) ;
            }
            else if (isBuying = false)
            {
                //g2d.dispose();
            }
            // coin + heart for player
            Image heart_player = new ImageIcon(getClass().getResource("/images/heart_player.png")).getImage();
            g2d.drawImage(heart_player, 10, 10, 150, 50, null) ;
            g2d.drawString("Health: " + heart, 10 + 50, 10 + 30);
            Image coin_player = new ImageIcon(getClass().getResource("/images/coin_player.png")).getImage();
            g2d.drawImage(coin_player, 10, 10 + 5 + 50, 150, 50, null) ;
            g2d.drawString("Coin: " + coin, 10 + 50 , 10 + 30 + 5 + 50);
            
            // tower
            for (Tower tower:arrTower){
                tower.draw(g2d);
                if (tower.target != null)
                {
                    g2d.setColor(Color.red);
                    g2d.drawLine(tower.getX() + 50 /2, tower.getY() + 50 /2, tower.target.getX() + 50 /2, tower.target.getY() + 50 /2);
                }
            }
            for (Enemy enemy:arrEnemy){
                enemy.draw(g2d);
            }
            for (Bullet bullet:arrBullet){
                bullet.draw(g2d);
            }
        }catch (ConcurrentModificationException ex){
            ex.printStackTrace();
        }
    }

    
    public void setBit(int bit) {
        this.bit = bit;
    }
    
    public void AI(){    
        int i;
        for (i=arrEnemy.size()-1;i>=0; i--) {
            arrEnemy.get(i).changeOrient(arrMaps);
            arrEnemy.get(i).move();
            if(arrEnemy.get(i).checkFinish(arrMaps)){
                arrEnemy.remove(i);
            }
            if(arrEnemy.size()==0){
                orderRow++;
                initBoss();
            }
        }
    }
    
    public int start(){
        GameTile spawner = getSpawner();
        int orientStart;
        if(spawner.getX()==0) {
            orientStart = Enemy.RIGHT;
        } else if (spawner.getX()<=GameStage.W_FRAME 
                && spawner.getX()>=GameStage.W_FRAME-50) {
            orientStart = Enemy.LEFT;
        } else if (spawner.getY()==0){
            orientStart = Enemy.DOWN;
        } else {
            orientStart = Enemy.UP;
        }
        return orientStart;
    }
    
    public GameTile getSpawner(){
        GameTile spawner = null;
        for(GameTile map: arrMaps){
            if(map instanceof Spawner){
                spawner = map;
                break;
            }
        }
        return spawner;
    }

    public void enemyUpdate ()
    {
        for (int i = 0 ; i < arrEnemy.size() ; i ++)
        {
            if (arrEnemy.get(i) != null)
            {
                Enemy enemy = arrEnemy.get(i).update() ;
                if (enemy == null) {   
                    this.coin += arrEnemy.get(i).getReward() ;
                    arrEnemy.remove(i);
                }
                else {
                    arrEnemy.set(i, enemy) ;
                }
            }
        }
    }
    
    public void towerUpdate ()
    {
        for (Tower tower: arrTower)
        {
            if (tower != null) TowerAttack (tower) ;
        }
    }
    
    public void bulletUpdate ()
    {
        for (int i = 0 ; i < arrBullet.size() ; i ++)
        {
            if (arrBullet.get(i)!= null)
            {
                Bullet bullet = arrBullet.get(i) ;
                bullet.update(); 
                if (bullet.target == null)
                {
                    arrBullet.remove(i) ;
                }
                else
                {
                    arrBullet.set(i, bullet) ;
                }
            }
        }
    }
    public void TowerAttack (Tower tower)
    {
        if (tower.target == null)
        {
            if (tower.attackDelay > tower.maxAttackDelay)
            {
                Enemy currentEnemy = tower.calculateEnemy(arrEnemy, tower.getX(), tower.getY());
                if (currentEnemy != null)
                {
                    tower.TowerAttack(tower.getX(), tower.getY(), currentEnemy);
                    tower.target = currentEnemy ;
                    tower.attackTime = 0 ;
                    tower.attackDelay = 0 ;
                    
                    System.out.println ("[Tower] Enemy attacked! health:" + currentEnemy.getHealth() + "x: " + (int)tower.getX() + "y: " + tower.getY() );
                }
            }
            else 
            {
                tower.attackDelay += 1 ;
            }
        }
        else 
        {
            if (tower.attackTime < tower.maxAttackTime)
            {
                tower.attackTime += 1 ;
            }
            else 
            {
                tower.target = null ;
            }
        }
    }
    
    
    
    //MouseListener
    public class BuyingBoard extends JPanel 
    {
        public boolean isRunning = true ;
        public void paintComponent (Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            super.paintComponent(g2d);
        }
        public void run ()
        {
            while (isRunning == true)
            {
                repaint () ;
                try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
            System.exit(0);
        }
    }
    
    public void defineMenuTower ()
    {
        towerList[0] = new NormalTower (13,this.gameField.gameStage.getHeight() - 140 + 47);
        towerList[1] = new SniperTower (78,this.gameField.gameStage.getHeight() - 140 + 47);
        towerList[2] = new MachineGunTower (147,this.gameField.gameStage.getHeight() - 140 + 47);
    }
    public void updateMouse (MouseEvent e)
    {
        if ( isBuying == false && this.gameField.Starting == true &&isDisplaying == true)
        {
            if (e.getX() >= 10 &&e.getX() <= 147 + 70 )
            {
                if (e.getY() > this.gameField.gameStage.getHeight() - 140 + 40 && e.getY() < this.gameField.gameStage.getHeight() )
                {
                    for (int i = 1 ; i <= 3 ; i++)
                    {
                        if (e.getX() >= 10 && e.getX() <= 10 + i * 70  )
                        {
                             id = i - 1 ;
                             isBuying = true ;
                             break ;
                        }
                    }
                }
            }  
            else if (e.getX() >= 147 + 70 + 70 && e.getX() < 147 + 70 + 70 + 100)
            {
                if (e.getY() > this.gameField.gameStage.getHeight() - 140 + 40 && e.getY() < this.gameField.gameStage.getHeight() )
                {
                    isBuying = false ;
                    this.gameField.isWaiting = false ;
                }
            }
        }  
        if (this.gameField.Starting == false && this.gameField.isWaiting == true)
        {
            if (e.getX() >= 290 && e.getX() <= 460 && e.getY() >= 440 && e.getY() <= 510)
            {
                this.gameField.Starting = true ;
            }
        }
        if (this.gameField.isExiting == true)
        {
            if (e.getX() >= gameField.gameStage.getWidth() / 2 - 500 / 2 + 10 &&e.getX() <= gameField.gameStage.getWidth() / 2 - 500 / 2 + 110)
            {
                if (e.getY() >= gameField.gameStage.getHeight() / 2 - 300 / 2 + 250&&e.getY() <= gameField.gameStage.getHeight() / 2 -300 / 2 + 310)
                {
                    this.gameField.isRunning = false ;
                }
            }  
            if (e.getX() >= gameField.gameStage.getWidth() / 2 - 500 / 2 + 160&&e.getX() <= gameField.gameStage.getWidth() / 2 - 500 / 2 + 270 )
            {
                if (e.getY() >= gameField.gameStage.getHeight() / 2 - 300 / 2 + 250&&e.getY() <= gameField.gameStage.getHeight() / 2 -300 / 2 + 310 )
                {
                    this.gameField.isExiting = false ;
                }
            }  
        }
        
    }
    public boolean PlaceTower (int x, int y)
    {
            int xPos = x / 50 ;
            int yPos = y / 50 ;
            // nếu map tại (xPos,yPos) == EmptyBox và tiền >= giá mua tháp thì thêm tháp vào arrTower
            
            if (arrMaps.get(yPos * mapColumn + xPos) instanceof EmptyBox  == true) {
               
                switch (id)
                {
                    case 0 : this.arrTower.add(new NormalTower (xPos * 50, yPos * 50)) ; break ;
                    case 1 : this.arrTower.add(new SniperTower (xPos * 50, yPos * 50)) ; break ;
                    case 2 : this.arrTower.add(new MachineGunTower (xPos * 50, yPos * 50)) ; break ;
                }
     
                
                return true ;
            }  
            return false ;
    }
    public void MousePosition (MouseEvent e)
    {
        mouseXPos = e.getX() ;
        mouseYPos = e.getY() ;
    }
    public class MouseHandler implements MouseListener, MouseMotionListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //mouseDown = 1 ;
           
              //  isMoving = true ;
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    if (isBuying == true)
                    {
                        placeTower = PlaceTower (e.getX(), e.getY()) ;  
                        if (placeTower == true) 
                        {
                       // isDisplaying = true ;
                        isBuying = false ;
                        }
                        
                    }
                    updateMouse (e);
                }
                if (isBuying == true)
                {
                    if (e.getButton() == MouseEvent.BUTTON3)
                        isBuying = false ;
                }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            MousePosition(e) ;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            MousePosition(e) ;
        }
        
    }
    
    
    // KeyListener
    public void keyESC ()
        {
            if (this.gameField.isRunning == true && this.gameField.isExiting == false)
            this.gameField.isExiting = true ;
            else if (this.gameField.isExiting == true)
            {
                this.gameField.isExiting = false ;
            }
        }

    public void keyENTER ()
        {
           if (this.gameField.isExiting == true)
           {
               this.gameField.isRunning = false ;
           }
           else if (this.gameField.isWaiting == true && isBuying == false && isDisplaying == false)
            {this.isDisplaying = true;}
           
           else this.gameField.isWaiting = false ;
        }
        
    public  void keySPACE() 
        {
           if (this.gameField.Starting == false && this.gameField.isWaiting == true)
            this.gameField.Starting = true;
        }
    public class KeyHandler implements KeyListener
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            int keyCode = e.getKeyCode();
            //System.out.println(e.getKeyCode() + "\n");
            if (keyCode == KeyEvent.VK_ESCAPE)
            {
               keyESC();
            }
            if (keyCode == KeyEvent.VK_ENTER)
            {
               keyENTER();
            }
            if (keyCode == KeyEvent.VK_SPACE)
            {
               keySPACE();
            }
        }
       
        @Override
        public void keyReleased(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        @Override
        public void keyTyped(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
