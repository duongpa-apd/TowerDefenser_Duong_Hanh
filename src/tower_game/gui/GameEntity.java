package tower_game.gui;

import tower_game.enemy.Enemy;
import tower_game.gametile.GameTile;
import tower_game.tower.Bullet;
import tower_game.tower.Tower;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import tower_game.enemy.NormalEnemy;
import tower_game.gametile.EmptyBox;
import tower_game.gametile.Mountain;
import tower_game.gametile.Road;
import tower_game.gametile.Spawner;
import tower_game.gametile.Target;
import tower_game.gametile.Tree;

public class GameEntity {
    private ArrayList<Tower> arrTower;
    private ArrayList<Bullet> arrBullet;
    private ArrayList<Enemy> arrEnemy;
    private ArrayList<GameTile> arrMaps;
    private int heart;
    private int coin;
    
    //Bắt đầu màn chơi
    public void initGame(){
        heart = 20;
        coin = 200;
        arrBullet = new ArrayList<>();
        arrEnemy = new ArrayList<>();
        arrTower = new ArrayList<>();
        readMap("mapData.txt");
        initBoss();
    }
    
    public void initBoss(){
        arrEnemy.add(new NormalEnemy(getSpawner().getX()-25, getSpawner().getY()+7));
        arrEnemy.add(new NormalEnemy(getSpawner().getX()-125, getSpawner().getY()+7));
        arrEnemy.add(new NormalEnemy(getSpawner().getX()-225, getSpawner().getY()+7));
        arrEnemy.add(new NormalEnemy(getSpawner().getX()-335, getSpawner().getY()+7));
        arrEnemy.add(new NormalEnemy(getSpawner().getX()-445, getSpawner().getY()+7));
    }
    
    public void readBoos(int dong){
        arrMaps = new ArrayList<>();
        File file = new File("src/maps/boss.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String line = reader.readLine();
            while (line!=null){
                if(row == dong){
                    String[] arrInt = line.split(" ");
                    int numberEnemy = 
                }
                row++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2d){
        try{
            for (GameTile map:arrMaps) {
                map.draw(g2d);
            }
            for (Tower tower:arrTower){
                tower.draw(g2d);
            }
            for (Bullet bullet:arrBullet){
                bullet.draw(g2d);
            }
            for (Enemy enemy:arrEnemy){
                enemy.draw(g2d);
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
                initBoss();
                start();
            }
        }
        
    }
    
    public void start(){
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
        for(Enemy e : arrEnemy){
            e.setOrient(orientStart);
        }
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

}
