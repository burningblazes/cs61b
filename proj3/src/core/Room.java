package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.Random;

public class Room {
    private int x, y;
    private int width, height;

    public Room(Random random, World world) {
        int worldWidth = world.getWidth();
        int worldHeight = world.getHeight();
        initialize(worldWidth, worldHeight,random);
        while (!isValidRoom(world) || isOverlapped(world)) {
            initialize(worldWidth, worldHeight,random);
        }
    }

    private void initialize(int worldWidth, int worldHeight,Random random) {
        x = random.nextInt(worldWidth - 6) + 1;
        y = random.nextInt(worldHeight - 6) + 1;
        width = random.nextInt(8) +6;
        height = random.nextInt(8) + 6;
    }

    public boolean isValidRoom(World world) {
        int[][] coords = {{x,y},{x+width,y},{x,y+height},{x+width,y+height}};
        for (int[] coord : coords) {
            if (coord[0] >= world.getWidth() -1|| coord[1]>=world.getHeight() -1){
                return false;
            }
        }
        return true;
    }

    private boolean isOverlapped(World world) {
        for (int i = x - 1; i <= x + width + 1; i++) {
            for (int j = y - 1; j <= y + height + 1; j++) {
                if (!world.isTileEmpty(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void draw(TETile[][] tile) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                tile[i][j] = Tileset.FLOOR;
            }
        }
        for (int i=x; i<= x+width;i++){
            tile[i][y] = Tileset.WALL;
            tile[i][y+height] = Tileset.WALL;
        }
        for (int i=y; i<= y+height;i++){
            tile[x][i] = Tileset.WALL;
            tile[x+width][i] = Tileset.WALL;
        }
    }
}

