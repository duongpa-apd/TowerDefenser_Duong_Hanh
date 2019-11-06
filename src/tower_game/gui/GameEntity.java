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
        arrBullet = new ArrayList<>();
        arrEnemy = new ArrayList<>();
        arrTower = new ArrayList<>();
        readMap("mapData.txt");
        
    }
    
    public void initBoss(ArrayList<Enemy> arr){
        this.arrEnemy.addAll(arr);
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
                            arrEnemy.add(new NormalEnemy(x+6, y+6, 5, 1, 10, 10));
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
            for (Bullet bullet:arrBullet){
                bullet.draw(g2d);
            }
            for (Tower tower:arrTower){
                tower.draw(g2d);
            }
            for (Enemy enemy:arrEnemy){
                enemy.draw(g2d);
            }
        }catch (ConcurrentModificationException ex){
            ex.printStackTrace();
        }
    }
    
    public void AI(){
        for (int i = arrBullet.size()-1; i >=0 ; i--) {
            boolean check = arrBullet.get(i).move();
            if (check == false){
                arrBullet.remove(i);
            }
        }
        
        for (int i = 0; i < arrEnemy.size(); i++) {
            arrEnemy.get(i).changeOrient(arrMaps);
            arrEnemy.get(i).move();
        }
    }
    
    public boolean checkGame(){
        if(heart == 0 || arrEnemy.size() == 0){
            return false;
        }
        return true;
    }
    
//    public ArrayList<GameTile> getRoad(){
//        ArrayList<GameTile> arrRoad = new ArrayList<>();
//        for(GameTile spawner: arrMaps){
//            if(spawner instanceof Spawner){
//                arrRoad.add(spawner);
//                break;
//            }
//        }
//        GameTile head = arrRoad.get(0);
//        GameTile left = null;
//        GameTile right = null;
//        GameTile up = null;
//        GameTile down = null;
//        
//        while(!(head instanceof Target)){
//            left = findMap(head.getX()+51,head.getY()+51);
//            right = findMap(head.getX()+51,head.getY()+51);
//            up = findMap(head.getX()+51,head.getY()+51);
//            down = findMap(head.getX()+51,head.getY()+51);
//            if(findMap(head.getX()+51,head.getY()+51) instanceof Road){
//                
//            }
//        }
//        return arrRoad;
//    }
//    

} 
