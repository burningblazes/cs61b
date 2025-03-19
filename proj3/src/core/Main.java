package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;


public class Main {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);
        mainMenu();
        World world = mainMenuCommand();
        ter.renderFrame(world.getWorld());
    }

    public static void mainMenu(){
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

    public static World mainMenuCommand(){
        while(true){
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'q' || c == 'Q') {
                    System.exit(0);
                } else if (c == 'n' || c == 'N') {
                    return newGame();
                }else if (c == 'l' || c == 'L') {
                    World temp=new World(12345L); //TODO
                    return temp;
                }
            }
        }
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
