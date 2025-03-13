package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler  {

    private MyGraph wordGraph;
    private NGramMap ngm;

    public HyponymsHandler(MyGraph wordGraph, NGramMap ngm) {
        this.wordGraph = wordGraph;
        this.ngm=ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> wordList = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        NgordnetQueryType qType=q.ngordnetQueryType();

        if (qType==NgordnetQueryType.HYPONYMS){
            return getHyponyms(wordList, startYear, endYear, k);
        }
        return null;
    }

    private String getHyponyms(List<String> wordList,int startYear, int endYear, int k){
        TreeSet<String> res = wordGraph.getHyponyms(wordList);

        if (res.size() > k) {
            TreeMap<String,Double> wordsCount = new TreeMap<>();
            for (String word : res) {
                wordsCount.put(word, ngm.periodCount(word,startYear,endYear));
            }
            res=getTopK(wordsCount,k);
        }
        return "[" + res.stream().collect(Collectors.joining(", ")) + "]";
    }

    private TreeSet<String> getTopK(TreeMap<String,Double> wordsCount, int k ) {
        PriorityQueue<Map.Entry<String, Double>> minHeap =
                new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        for (Map.Entry<String, Double> entry : wordsCount.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        TreeSet<String> res = new TreeSet<>();
        while (!minHeap.isEmpty()) {
            res.add(minHeap.poll().getKey());
        }
        return res;
    }
}

