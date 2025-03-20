package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Hallway {
    private ArrayList<Room[]> connections;
    private ArrayList<Room> rooms;
    private final static double P=0.25;
    private int numTurning;
    private Random random;

    public Hallway(World world) {
        this.random = world.getRandom();
        connections = new ArrayList<>();
        rooms = world.getRooms();
        initConnections();
        numTurning= (int) Math.round(P*connections.size()/(1-P));
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

    public void drawSingle(TETile[][] tile,Room[] connection) {
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

    public void drawAll(TETile[][] tile) {
        for (int i = 0; i < connections.size(); i++) {
            Room[] connection = connections.get(i);
            drawSingle(tile, connection);
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

    public void drawAllTurning(TETile[][] tile) {
        System.out.println(numTurning);
        for (int i = 0; i < numTurning; i++) {
            drawSingleTurning(tile);
        }
    }

    public void drawSingleTurning(TETile[][] tile) {
        int x = getRandNumXY();
        int y = getRandNumXY();

        while (tile[x][y] != Tileset.NOTHING ||!validTurn(tile, x, y)) {
            x = getRandNumXY();
            y = getRandNumXY();
        }

        // if x>25, goes left until it reaches a wall;
        // if x<=25, goes right until it reaches a wall
        int xx=x;
        tile[x][y] = Tileset.FLOOR;
        int stepX = (x < 25) ? 1 : -1;
        int stepY = (y < 25) ? 1 : -1;

        // Draw horizontal line and record its endpoints
        int horizontalEndX = drawLine(tile, x, y, stepX, 0);
        // Draw vertical line and record its endpoints
        int verticalEndY = drawLine(tile, x, y, 0, stepY);

        // Draw side walls around horizontal line
        drawWallsAlongLine(tile, x, y, horizontalEndX, y);
        // Draw side walls around vertical line
        drawWallsAlongLine(tile, x, y, x, verticalEndY);

        // Draw corner walls around turning point
        cornerWalls(tile, x, y);
    }

    private int drawLine(TETile[][] tile, int x, int y, int dx, int dy) {
        while (tile[x][y] != Tileset.FLOOR) {
            tile[x][y] = Tileset.FLOOR;
            x += dx;
            y += dy;
        }
        // return the last coordinate before hitting wall
        return dx != 0 ? x - dx : y - dy;
    }

    private void drawWallsAlongLine(TETile[][] tile, int x1, int y1, int x2, int y2) {
        if (x1 == x2) { // vertical line
            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            for (int yy = startY; yy <= endY; yy++) {
                sideWalls(tile, x1, yy);
            }
        } else { // horizontal line
            int startX = Math.min(x1, x2);
            int endX = Math.max(x1, x2);
            for (int xx = startX; xx <= endX; xx++) {
                sideWalls(tile, xx, y1);
            }
        }
    }


    private int getRandNumXY() {
        return random.nextInt(43)+3;
    }

    private boolean validTurn(TETile[][] tile, int x, int y) {
        return checkHorizon(tile, x, y)&& checkVertical(tile, x, y);
    }

    private boolean checkVertical(TETile[][] tile, int x, int y) {
        if (y<25){
            while (y<48){
                if (tile[x][y] != Tileset.NOTHING){
                    return true;
                }
                y++;
            }
        }else {
            while (y>0){
                if (tile[x][y] != Tileset.NOTHING){
                    return true;
                }
                y--;
            }
        }
        return false;
    }

    private boolean checkHorizon(TETile[][] tile, int x, int y) {
        if (x<25){
            while (x<48){
                if (tile[x][y] != Tileset.NOTHING){
                    return true;
                }
                x++;
            }
        }else {
            while (x>0){
                if (tile[x][y] != Tileset.NOTHING){
                    return true;
                }
                x--;
            }
        }
        return false;
    }

}