package org.deeplearning4j.rottentomatoes.data.train;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import org.deeplearning4j.berkeley.Pair;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A text retriever for rotten tomatoes
 *
 * @author Adam Gibson
 */
public class TextRetriever {

    private File csv;
    private Map<String,Pair<String,String>> pair;
    private Map<String,List<String>> sentenceToPhrase;
    public TextRetriever() throws IOException {
        ClassPathResource resource = new ClassPathResource("/train.tsv");
        File file = resource.getFile();
        init(file.getAbsolutePath());
    }

    public TextRetriever(String location) {
        init(location);

    }

    private void init(String location) {
        csv = new File(location);
        pair = new HashMap<>();
        sentenceToPhrase = new HashMap<>();

        CSV csv1 = CSV.separator('\t')
                .ignoreLeadingWhiteSpace().skipLines(1)
                .create();
        //PhraseId	SentenceId	Phrase	Sentiment
        csv1.read(csv,new CSVReadProc() {
            @Override
            public void procRow(int rowIndex, String... values) {
                //sentence id -> phrase id
                List<String> phrases = sentenceToPhrase.get(values[1]);
                if(phrases == null) {
                    phrases = new ArrayList<>();
                    sentenceToPhrase.put(values[1],phrases);
                }

                phrases.add(values[2]);

                pair.put(values[0],new Pair<>(values[2],values[3]));
            }
        });
    }


    public Map<String,Pair<String,String>> data() {
        return pair;
    }





    public List<String> labels() {
        List<String> phrases = new ArrayList<>();
        for(String s : pair.keySet())
            phrases.add(pair.get(s).getSecond());
        return phrases;
    }

    public List<String> phrases() {
        List<String> phrases = new ArrayList<>();
        for(String s : pair.keySet())
            phrases.add(pair.get(s).getFirst());
        return phrases;
    }




}
