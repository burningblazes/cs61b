package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.Random;

public class World {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private int width;
    private int height;
    private TETile[][] myWorld;
    private Random random;
    //private TERenderer ter;

    public World(long seed) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
//        ter = new TERenderer();
//        ter.initialize(width, height);
        random = new Random(seed);
        myWorld = new TETile[width][height];

        clearWorld();
        drawWorld();

    }

    public void clearWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                myWorld[x][y]=Tileset.NOTHING;
            }
        }
    }

    public void drawWorld() {
        //TODO: drawRooms
        drawRooms();
        //TODO: drawWalls
        //TODO: drawHallways

    }

    public TETile[][] getWorld() {
        return myWorld;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isTileEmpty(int x, int y) {
        return myWorld[x][y]==Tileset.NOTHING;
    }

    private void drawRooms(){
        int num=random.nextInt(5)+7;
        for (int x = 0; x < num; x++) {
            Room room = new Room(random, this);
            room.draw(myWorld);
        }
    }

}
