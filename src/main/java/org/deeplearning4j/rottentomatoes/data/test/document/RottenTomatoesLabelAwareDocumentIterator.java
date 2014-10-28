package org.deeplearning4j.rottentomatoes.data.test.document;

import org.apache.commons.io.IOUtils;
import org.deeplearning4j.rottentomatoes.data.train.TextRetriever;
import org.deeplearning4j.text.documentiterator.LabelAwareDocumentIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesLabelAwareDocumentIterator implements LabelAwareDocumentIterator {

    private List<String> phrases;
    private List<String> labels;
    private int currRecord;
    private SentencePreProcessor preProcessor;

    public RottenTomatoesLabelAwareDocumentIterator(SentencePreProcessor preProcessor) {
        this.preProcessor = preProcessor;
        try {
            TextRetriever retriever = new TextRetriever();
            this.phrases = retriever.phrases();
            this.labels = retriever.labels();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public RottenTomatoesLabelAwareDocumentIterator() {
       this(null);
    }

    @Override
    public String currentLabel() {
        return labels.get(currRecord > 0 ? currRecord - 1 : 0);
    }

    @Override
    public InputStream nextDocument() {
        String ret = phrases.get(currRecord);
        currRecord++;
        return IOUtils.toInputStream(ret);
    }

    @Override
    public boolean hasNext() {
        return currRecord < labels.size();
    }

    @Override
    public void reset() {
       currRecord = 0;
    }



}
