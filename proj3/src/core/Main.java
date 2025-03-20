package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import utils.FileUtils;

import java.io.FileWriter;

import java.awt.*;
import java.util.ArrayList;


public class Main {

    private static final String SAVE_FILE = "src/save.txt";

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);
        World world = mainMenu();
        ter.renderFrame(world.getWorld());

        boolean readyToExit = false;

        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c==':'){
                    readyToExit = true;
                }else if ( readyToExit && (c=='q' || c=='Q')) {
                    saveWorld(world.getWorld());
                    System.exit(0);
                } else {
                    readyToExit = false;
                    world.getAvator().updateAvator(c);
                }
            }
            ter.renderFrame(world.getWorld());
        }
    }

    public static void showMainMenu(){
        StdDraw.clear(Color.BLACK);
        printTitle();

        Font font = new Font("Sans Serif",Font.PLAIN, 25);
        StdDraw.setFont(font);
        StdDraw.text(25, 30,"(N) New Game");
        StdDraw.text(25, 25, "(L) Load Game");
        StdDraw.text(25, 20, "(Q) Quit Game");
        StdDraw.show();
    }

    private static void printTitle(){
        StdDraw.setPenColor(255,255,255);
        Font font = new Font("Sans Serif",Font.PLAIN, 35);
        StdDraw.setFont(font);
        StdDraw.text(25, 45, "CS61B: BYOW");
        StdDraw.show();
    }

    public static World mainMenu(){
        showMainMenu();
        while(true){
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'q' || c == 'Q') {
                    System.exit(0);
                } else if (c == 'n' || c == 'N') {
                    return newGame();
                }else if (c == 'l' || c == 'L') {
                    return loadWorld(SAVE_FILE);
                }
            }
        }
    }

    public static void saveWorld(TETile[][] tiles){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 50; i++){
            for (int j = 0; j < 50; j++){
                if (tiles[i][j] == Tileset.NOTHING){
                    res.append('N');
                }else if (tiles[i][j] == Tileset.AVATAR){
                    res.append('A');
                }else if (tiles[i][j] == Tileset.WALL){
                    res.append('W');
                }else if (tiles[i][j]==Tileset.FLOOR){
                    res.append('F');
                }
            }
            res.append("\n");
        }
        FileUtils.writeFile(SAVE_FILE, res.toString());
    }

    public static World loadWorld(String filename){
        TETile[][] world = new TETile[50][50];
        int x=51;
        int y=51;
        String file= FileUtils.readFile(filename);
        String[] lines = file.split("\n");
        for (int i = 0; i < lines.length; i++){
            String line = lines[i];
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == 'N'){
                    world[i][j]=Tileset.NOTHING;
                }else if (line.charAt(j) == 'A'){
                    world[i][j]=Tileset.AVATAR;
                    x=i;
                    y=j;
                }else if (line.charAt(j) == 'W'){
                    world[i][j]=Tileset.WALL;
                }else if (line.charAt(j) == 'F'){
                    world[i][j]=Tileset.FLOOR;
                }
            }
        }
        return new World(world,x,y);

    }

    private static void enterSeedInstruction(){
        StdDraw.clear(Color.BLACK);
        printTitle();

        Font font = new Font("Sans Serif",Font.PLAIN, 30);
        StdDraw.setFont(font);
        StdDraw.text(25, 25, "Enter seed followed by S");
        StdDraw.show();
    }

    public static World newGame() {
        enterSeedInstruction();
        Long seed =enterSeed();
        return new World(seed);
    }

    private static Long enterSeed(){
        String seed = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (Character.isDigit(c)) {
                    seed += c;
                    if (seed.length()==19&&seed.compareTo("9223372036854775807") > 0) {
                        StdDraw.text(25, 15, "Seed is too big");
                        StdDraw.show();
                        return enterSeed();
                    }
                } else if (c == 'S' || c == 's') {
                    if (seed.isEmpty()) {
                        StdDraw.text(25, 15, "You have to enter a seed");
                        StdDraw.show();
                        return enterSeed();
                    }else {
                        return Long.parseLong(seed);
                    }
                }
                enterSeedInstruction();
                StdDraw.text(25, 20, seed);
                StdDraw.show();
            }
        }
    }

}
