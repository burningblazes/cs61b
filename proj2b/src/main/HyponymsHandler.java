package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler  {

    private MyGraph wordGraph;

    public HyponymsHandler(MyGraph wordGraph) {
        this.wordGraph = wordGraph;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> wordList = q.words();
        TreeSet<String> res = wordGraph.getHyponyms(wordList);
        return "[" + res.stream().collect(Collectors.joining(", ")) + "]";
    }
}
