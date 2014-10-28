package org.deeplearning4j.rottentomatoes.data.test;

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
 * Created by agibsonccc on 10/23/14.
 */
public class TextRetriever {

    private Map<String,String> phrases = new HashMap<>();





    private Map<String,String> data() {
        return phrases;
    }
    private File csv;
    private Map<String,Pair<String,String>> pair;
    private int phraseIdIdx = 0;
    private int phrase = 1;


    public TextRetriever() throws IOException {
        ClassPathResource resource = new ClassPathResource("/test.tsv");
        File file = resource.getFile();
        init(file.getAbsolutePath());
    }

    public TextRetriever(String location) {
        init(location);

    }

    private void init(String location) {
        csv = new File(location);
        pair = new HashMap<>();
        CSV csv1 = CSV.separator('\t')
                .ignoreLeadingWhiteSpace().skipLines(1)
                .create();
        //PhraseId	SentenceId	Phrase
        csv1.read(csv,new CSVReadProc() {
            @Override
            public void procRow(int rowIndex, String... values) {
                phrases.put(values[0],values[2]);
            }
        });
    }




    public List<String> phrases() {
        List<String> phrases = new ArrayList<>();
        for(String s : pair.keySet())
            phrases.add(pair.get(s).getFirst());
        return phrases;
    }





}
