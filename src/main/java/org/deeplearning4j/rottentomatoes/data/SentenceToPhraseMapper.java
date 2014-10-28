package org.deeplearning4j.rottentomatoes.data;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agibsonccc on 10/23/14.
 */
public class SentenceToPhraseMapper {

    private Map<Integer,SentenceToSubPhrase> sentenceToPhrase = new HashMap<>();
    private int phraseIndex = 0;
    private int sentenceIndex = 1;
    private File csv;

    public SentenceToPhraseMapper(File csv) {
        this(csv,0,1);
    }

    public SentenceToPhraseMapper(File csv,int phraseIndex,int sentenceIndex) {
        this.sentenceIndex = sentenceIndex;
        this.phraseIndex = phraseIndex;
        this.csv = csv;
        init();

    }


    private void init() {
        CSV csv1 = CSV.separator('\t')
                .ignoreLeadingWhiteSpace().skipLines(1)
                .create();

        //PhraseId	SentenceId	Phrase	Sentiment
        csv1.read(csv,new CSVReadProc() {
            @Override
            public void procRow(int rowIndex, String... values) {
                //sentence id -> phrase id
                SentenceToSubPhrase phrases = sentenceToPhrase.get(Integer.parseInt(values[sentenceIndex]));
                if(phrases == null) {
                    phrases = new SentenceToSubPhrase();
                    sentenceToPhrase.put(Integer.parseInt(values[phraseIndex]),phrases);
                }

                //train
                if(values.length > 3)
                    phrases.add(values[2],Integer.parseInt(values[3]));
                else
                    phrases.add(values[2]);

            }
        });
    }


    public Map<Integer, SentenceToSubPhrase> getSentenceToPhrase() {
        return sentenceToPhrase;
    }

    public void setSentenceToPhrase(Map<Integer, SentenceToSubPhrase> sentenceToPhrase) {
        this.sentenceToPhrase = sentenceToPhrase;
    }


    public List<String> sentences() {
        List<String> ret = new ArrayList<>();
        for(SentenceToSubPhrase s : sentenceToPhrase.values())
            ret.add(s.getSentence());
        return ret;
    }



}
