package main;

import com.google.common.collect.Sets;
import edu.princeton.cs.algs4.In;

import java.util.*;


public class MyGraph {

    private HashMap<Integer, Set<Integer>> children;
    private HashMap<Integer, Set<String>> words;
    private HashMap<String, Set<Integer>> idOfSingleWords;

    public MyGraph(String synsetFileName, String hyponymFileName) {
        children = new HashMap<>();
        words=new HashMap<>();
        idOfSingleWords = new HashMap<>();

        In in1=new In(synsetFileName);
        while (!in1.isEmpty()) {
            String nextLine = in1.readLine();
            String[] splitLine=nextLine.split(",");
            int thisId=Integer.parseInt(splitLine[0]);
            String[] splitWords=splitLine[1].split(" ");

            Set<String> thisWords=new HashSet<>();
            thisWords.addAll(Arrays.asList(splitWords));
            words.put(thisId, thisWords);

            for (String w : splitWords) {
                Set<Integer> ids = idOfSingleWords.getOrDefault(w, new HashSet<>());
                ids.add(thisId);
                idOfSingleWords.put(w, ids);
            }
        }

        In in2=new In(hyponymFileName);
        while (!in2.isEmpty()) {
            String nextLine = in2.readLine();
            String[] splitLine=nextLine.split(",");
            int temp=Integer.parseInt(splitLine[0]);

            Set<Integer> theChildren = children.getOrDefault(temp, new HashSet<>());
            for (int i=1;i<splitLine.length;i++) {
                theChildren.add(Integer.parseInt(splitLine[i]));
            }
            children.put(temp, theChildren);
        }
    }

    public TreeSet<String> getHyponyms(String word){
        TreeSet<String> res=new TreeSet<>();
        Queue<Integer> q=new ArrayDeque<>();
        q.addAll(idOfSingleWords.get(word));
        while(!q.isEmpty()){
            Integer temp= q.remove();
            res.addAll(words.get(temp));
            Set<Integer> aa = children.get(temp);
            if (aa!=null) {
                q.addAll(aa);
            }
        }
        return res;
    }

    public TreeSet<String> getHyponyms(List<String> wordlist){
        TreeSet<String> res= getHyponyms(wordlist.get(0));
        for (int i=1;i<wordlist.size();i++){
            TreeSet<String> temp= getHyponyms(wordlist.get(i));
            res.retainAll(temp);
        }
        return res;
    }


}
