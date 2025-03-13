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
        } else if (qType==NgordnetQueryType.ANCESTORS){
            return getCommonAncestors(wordList, startYear, endYear, k);
        }
        return null;
    }


    private String getHyponyms(List<String> wordList,int startYear, int endYear, int k){
        TreeSet<String> res = wordGraph.getHyponyms(wordList);

        if (k>0 && res.size() > k) {
            res=getTopK(res,k,startYear,endYear);
        }
        return output(res);
    }


    private String getCommonAncestors(List<String> wordList,int startYear, int endYear, int k){
        TreeSet<String> res = wordGraph.getAncestor(wordList);

        if (k >0 && res.size() > k) {
            res=getTopK(res,k,startYear,endYear);
        }
        return output(res);
    }

    private TreeSet<String> getTopK(TreeSet<String> wordSet,int k,int startYear,int endYear){
        TreeMap<String,Double> wordsCount = new TreeMap<>();
        for (String word : wordSet) {
            wordsCount.put(word, ngm.periodCount(word,startYear,endYear));
        }
        wordSet=getTopKHelper(wordsCount,k);
        return wordSet;
    }

    private TreeSet<String> getTopKHelper(TreeMap<String,Double> wordsCount, int k ) {
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

    private String output(TreeSet<String> wordSet){
        return "[" + wordSet.stream().collect(Collectors.joining(", ")) + "]";
    }
}

