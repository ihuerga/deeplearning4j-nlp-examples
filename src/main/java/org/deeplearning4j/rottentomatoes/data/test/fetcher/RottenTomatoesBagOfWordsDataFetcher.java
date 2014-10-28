package org.deeplearning4j.rottentomatoes.data.test.fetcher;


import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.bagofwords.vectorizer.TextVectorizer;
import org.deeplearning4j.datasets.fetchers.BaseDataFetcher;
import org.deeplearning4j.rottentomatoes.data.train.sentence.RottenTomatoesLabelAwareSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.dataset.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesBagOfWordsDataFetcher extends BaseDataFetcher {

    private LabelAwareSentenceIterator iter;
    private TextVectorizer countVectorizer;
    private TokenizerFactory factory = new DefaultTokenizerFactory();

    public RottenTomatoesBagOfWordsDataFetcher() {
        iter = new RottenTomatoesLabelAwareSentenceIterator();
        countVectorizer = new BagOfWordsVectorizer.Builder()
                .iterate(iter).tokenize(factory).labels(Arrays.asList("0","1","2","3","4")).build();
        countVectorizer.fit();
        iter.reset();


    }

    public TextVectorizer getCountVectorizer() {
        return countVectorizer;
    }

    @Override
    public void fetch(int numExamples) {
        //set the current dataset
        List<DataSet> list = new ArrayList<>();
        for(int i = 0; i < numExamples; i++) {
            if(!iter.hasNext())
                break;
            String sentence = iter.nextSentence();
            String label = iter.currentLabel();
            list.add(countVectorizer.vectorize(sentence,label));
        }

        curr = DataSet.merge(list);

    }

    @Override
    public int totalOutcomes() {
        return 5;
    }

    @Override
    public int inputColumns() {
        return super.inputColumns();
    }
}
