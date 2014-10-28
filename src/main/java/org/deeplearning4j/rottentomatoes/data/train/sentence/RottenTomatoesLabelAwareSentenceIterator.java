package org.deeplearning4j.rottentomatoes.data.train.sentence;

import org.deeplearning4j.rottentomatoes.data.train.TextRetriever;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesLabelAwareSentenceIterator implements LabelAwareSentenceIterator {

    private List<String> phrases;
    private List<String> labels;
    private AtomicInteger currRecord;
    private SentencePreProcessor preProcessor;

    public RottenTomatoesLabelAwareSentenceIterator(SentencePreProcessor preProcessor) {
        this.preProcessor = preProcessor;
        try {
            TextRetriever retriever = new TextRetriever();
            this.phrases = new CopyOnWriteArrayList<>(retriever.phrases());
            this.labels = new CopyOnWriteArrayList<>(retriever.labels());
            System.out.println(labels.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        currRecord = new AtomicInteger(0);


    }

    public RottenTomatoesLabelAwareSentenceIterator() {
       this(null);
    }

    @Override
    public String currentLabel() {
        return labels.get(currRecord.get() > 0 ? currRecord.get() - 1 : 0);
    }

    @Override
    public synchronized  String nextSentence() {
        String ret = phrases.get(currRecord.get());
        currRecord.incrementAndGet();
        return ret;
    }

    @Override
    public synchronized  boolean hasNext() {
        return currRecord.get() < phrases.size();
    }

    @Override
    public void reset() {
       currRecord.set(0);
    }

    @Override
    public void finish() {

    }

    @Override
    public SentencePreProcessor getPreProcessor() {
        return preProcessor;
    }

    @Override
    public void setPreProcessor(SentencePreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
}
