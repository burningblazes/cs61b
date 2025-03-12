package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        MyGraph graph = new MyGraph(synsetFile,hyponymFile);

        return new HyponymsHandler(graph);


        //throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
