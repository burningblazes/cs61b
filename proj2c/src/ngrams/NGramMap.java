package ngrams;

import java.util.Collection;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;



import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private HashMap<String,TimeSeries> ngrams;
    private TimeSeries wordsCount;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In in1 = new In(wordsFilename);
        ngrams = new HashMap<>();

        while (!in1.isEmpty()) {
            String nextLine = in1.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            if (! ngrams.containsKey(word)) {
                ngrams.put(word, new TimeSeries());
                TimeSeries ts = new TimeSeries();
                ts.put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
                ngrams.put(word,ts);
            }else{
                ngrams.get(word).put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
            }
        }

        In in2 = new In(countsFilename);
        wordsCount = new TimeSeries();

        while (!in2.isEmpty()) {
            String nextLine = in2.readLine();
            String[] splitLine = nextLine.split(",");
            wordsCount.put(Integer.parseInt(splitLine[0]),Double.parseDouble(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(ngrams.get(word),startYear,endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(wordsCount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries res = countHistory( word, startYear,  endYear);
        res=res.dividedBy(totalCountHistory());
        return res;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory( word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries res= new TimeSeries();
        for (String word : words) {
            res=res.plus(weightHistory(word, startYear, endYear));
        }
        return res;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    public double periodCount(String word, int startYear, int endYear) {
        TimeSeries allCount = countHistory(word, startYear, endYear);
        double res = 0;
        for (double d :allCount.data()){
            res+=d;
        }
        return res;
    }

}
