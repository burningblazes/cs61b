package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Hallway {
    private ArrayList<ArrayList<Integer>> connections;
    private ArrayList<Room> rooms;

    public Hallway(Random random,World world) {
        connections=new ArrayList<>();
        rooms=world.getRooms();
        test();
    }

    private int distance(Room r1, Room r2) {
        return Math.abs(r1.getCenterX()-r2.getCenterX())+Math.abs(r1.getCenterY()-r2.getCenterY());
    }

    private void test(){
        ArrayList<Integer> temp =new ArrayList<>();
        Room r1=rooms.get(0);
        Room r2=rooms.get(1);
        temp.add(r1.getCenterX());
        temp.add(r1.getCenterY());
        temp.add(r2.getCenterX());
        temp.add(r2.getCenterY());
        connections.add(temp);
    }

    public void draw(TETile[][] tile) {
        System.out.println(connections);
        for (int i=0; i<connections.size(); i++) {
            ArrayList<Integer> temp=connections.get(i);
            System.out.println(temp);
            int turnX=temp.get(0);
            int turnY=temp.get(3);

            int lowY=Math.min(temp.get(1), temp.get(3));
            int highY=Math.max(temp.get(1), temp.get(3));
            for (int yy=lowY; yy<highY; yy++) {
                tile[turnX][yy]=Tileset.FLOOR;
            }

            int lowX=Math.min(temp.get(0), temp.get(2));
            int highX=Math.max(temp.get(0), temp.get(2));
            for (int xx=lowX;xx<highX; xx++) {
                tile[xx][turnY]=Tileset.FLOOR;
            }
        }
    }


}
