package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Avator {
    private Random random;
    private TETile[][] myWorld;
    private int x;
    private int y;

    public Avator(World world) {
        random= world.getRandom();
        myWorld=world.getWorld();
        addAvator();
    }

    public void addAvator() {
        x=getRandomXY();
        y=getRandomXY();
        while (!isFloor(x, y)) {
            x=getRandomXY();
            y=getRandomXY();
        }
        myWorld[x][y]=Tileset.AVATAR;
    }

    private int getRandomXY(){
        return random.nextInt(45)+2;
    }

    public boolean isFloor(int x, int y){
        return myWorld[x][y]==Tileset.FLOOR;
    }

    public boolean canMove(int dx, int dy){
        return isFloor(x+dx, y+dy);
    }

    public void move(int dx, int dy){
        if (canMove(dx, dy)) {
            myWorld[x+dx][y+dy]=Tileset.AVATAR;
            myWorld[x][y]=Tileset.FLOOR;
            x=x+dx;
            y=y+dy;
        }
    }

    public void updateAvator(){
        if (StdDraw.hasNextKeyTyped()){
            char c = StdDraw.nextKeyTyped();
            if (c=='w'||c=='W'){
                move(0,1);
            }else if (c=='s'||c=='S'){
                move(0,-1);
            }else if (c=='a'||c=='A'){
                move(-1,0);
            }else if (c=='d'||c=='D'){
                move(1,0);
            }else if (c=='q'||c=='Q'){
                System.exit(0);
            }

        }
    }



}
