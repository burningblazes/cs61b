package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.StringJoiner;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder res=new StringBuilder();

        for (String word : words) {
            res.append(word).append(": {");
            TimeSeries temp= ngm.weightHistory(word, startYear, endYear);
            StringJoiner sj=new StringJoiner(", ");

            for (Integer year : temp.years()) {
                sj.add(year + "=" + temp.get(year));
            }
            res.append(sj.toString()).append("}\n");
        }


        return res.toString();
    }
}