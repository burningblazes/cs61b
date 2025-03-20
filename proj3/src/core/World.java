package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private int width;
    private int height;
    private TETile[][] myWorld;
    private Random random;
    private ArrayList<Room> rooms;
    private Avator avator;

    public World(long seed) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        random = new Random(seed);
        myWorld = new TETile[width][height];
        rooms = new ArrayList<>();
        clearWorld();
        drawWorld();
        avator=new Avator(this);
    }

    public World(TETile[][] tiles, int x, int y){
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        random = new Random();
        myWorld = tiles;
        avator=new Avator(this, x, y);
    }

    public Avator getAvator() {
        return avator;
    }

    public void clearWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                myWorld[x][y]=Tileset.NOTHING;
            }
        }
    }

    public void drawWorld() {
        drawRooms();
        Hallway hallway=new Hallway(this);
        hallway.drawAll(myWorld);
       // hallway.drawAllTurning(myWorld,random);
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

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Random getRandom() {
        return random;
    }

    private void drawRooms(){
        int num=random.nextInt(5)+7;
        for (int x = 0; x < num; x++) {
            Room room = new Room(this);
            rooms.add(room);
            room.draw(myWorld);
        }
    }

}
