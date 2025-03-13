package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMap ngm = new NGramMap(wordFile, countFile);
        MyGraph wordGraph = new MyGraph(synsetFile, hyponymFile);
        return new HyponymsHandler(wordGraph, ngm);

        //throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
