package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class Main {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);
        World temp=new World(23456789098765L);
        ter.renderFrame(temp.getWorld());

    }
}
