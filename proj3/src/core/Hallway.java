package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Hallway {
    private ArrayList<Room[]> connections;
    private ArrayList<Room> rooms;

    public Hallway(Random random, World world) {
        connections = new ArrayList<>();
        rooms = world.getRooms();
        initConnections();

    }

    private void initConnections() {
        Set<Room> connectedRooms = new HashSet<>();
        Room current = rooms.get(0);
        connectedRooms.add(current);

        while (connectedRooms.size() < rooms.size()) {
            Room nearest = null;
            int minDistance = Integer.MAX_VALUE;

            for (Room r : rooms) {
                if (!connectedRooms.contains(r)) {
                    int d = distance(current, r);
                    if (d < minDistance) {
                        minDistance = d;
                        nearest = r;
                    }
                }
            }

            if (nearest != null) {
                connections.add(new Room[]{current, nearest});
                connectedRooms.add(nearest);
                current = nearest;
            }
        }
    }


    private int distance(Room r1, Room r2) {
        return Math.abs(r1.getCenterX() - r2.getCenterX()) + Math.abs(r1.getCenterY() - r2.getCenterY());
    }

    public void draw(TETile[][] tile) {
        for (int i = 0; i < connections.size(); i++) {
            Room[] connection = connections.get(i);
            int[] temp = {connection[0].getCenterX(), connection[0].getCenterY(),
                    connection[1].getCenterX(), connection[1].getCenterY()};

            int turnX = temp[0];
            int turnY = temp[3];

            int lowY = Math.min(temp[1], temp[3]);
            int highY = Math.max(temp[1], temp[3]);
            for (int yy = lowY; yy <= highY; yy++) {
                tile[turnX][yy] = Tileset.FLOOR;
                sideWalls(tile, turnX, yy);
            }

            int lowX = Math.min(temp[0], temp[2]);
            int highX = Math.max(temp[0], temp[2]);
            for (int xx = lowX; xx <= highX; xx++) {
                tile[xx][turnY] = Tileset.FLOOR;
                sideWalls(tile, xx, turnY);
            }
            cornerWalls(tile, turnX, turnY);
        }
    }

    private void sideWalls(TETile[][] tile, int x, int y) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int nx = x + d[0], ny = y + d[1];
            if (tile[nx][ny] == Tileset.NOTHING) {
                tile[nx][ny] = Tileset.WALL;
            }
        }
    }

    private void cornerWalls(TETile[][] tile, int x, int y) {
        int[][] diagonalDirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] d : diagonalDirs) {
            int nx = x + d[0], ny = y + d[1];
            if (tile[nx][ny] == Tileset.NOTHING) {
                tile[nx][ny] = Tileset.WALL;
            }
        }
    }
}