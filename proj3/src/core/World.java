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
        //TODO: drawWalls
        //TODO: drawHallways

        for (int x = 20; x < 35; x++) {
            for (int y = 5; y < 10; y++) {
                myWorld[x][y] = Tileset.WALL;
            }
        }
    }

    public TETile[][] getWorld() {
        return myWorld;
    }


}
