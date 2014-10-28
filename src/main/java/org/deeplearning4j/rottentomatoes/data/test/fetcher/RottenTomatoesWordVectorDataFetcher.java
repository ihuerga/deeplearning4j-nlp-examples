package org.deeplearning4j.rottentomatoes.data.test.fetcher;



import org.deeplearning4j.datasets.fetchers.BaseDataFetcher;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.rottentomatoes.data.train.sentence.RottenTomatoesLabelAwareSentenceIterator;
import org.deeplearning4j.text.movingwindow.Window;
import org.deeplearning4j.text.movingwindow.WindowConverter;
import org.deeplearning4j.text.movingwindow.Windows;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.util.FeatureUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesWordVectorDataFetcher extends BaseDataFetcher {

    private LabelAwareSentenceIterator iter;
    private Word2Vec vec;
    private List<String> labels = Arrays.asList("0","1","2","3","4");
    private TokenizerFactory factory = new DefaultTokenizerFactory();
    private List<DataSet> leftOver;



    public RottenTomatoesWordVectorDataFetcher(Word2Vec vec) {
        iter = new RottenTomatoesLabelAwareSentenceIterator();
        this.vec = vec;

    }

    public RottenTomatoesWordVectorDataFetcher() {
        iter = new RottenTomatoesLabelAwareSentenceIterator();

        vec = new Word2Vec.Builder().iterate(iter).tokenizerFactory(factory)
                .learningRate(1e-3).vocabCache(new InMemoryLookupCache(300))
                .layerSize(300).windowSize(5).build();
        vec.fit();

        iter.reset();


    }


    @Override
    public void fetch(int numExamples) {

        if(leftOver != null && leftOver.size() >= numExamples) {
            List<DataSet> merge = new ArrayList<>();
            for(int i = 0; i < numExamples; i++)
                merge.add(leftOver.remove(0));
            curr = DataSet.merge(leftOver);
            cursor += curr.numExamples();
        }

        else if(!iter.hasNext()) {
            if(leftOver != null && !leftOver.isEmpty()) {
                List<DataSet> merge = new ArrayList<>();
                for(int i = 0; i < leftOver.size(); i++)
                    merge.add(leftOver.remove(0));
                curr = DataSet.merge(leftOver);
                cursor += curr.numExamples();

            }
        }

        //set the current dataset
        List<DataSet> list = new ArrayList<>();
        for(int i = 0; i < numExamples; i++) {
            if(!iter.hasNext())
                break;
            String sentence = iter.nextSentence();
            String label = iter.currentLabel();
            List<Window> windows = Windows.windows(sentence,vec.getWindow());
            for(Window window : windows) {
                INDArray vector = WindowConverter.asExampleArray(window,vec,false);
                INDArray label2 = FeatureUtil.toOutcomeVector(labels.indexOf(label),labels.size());
                list.add(new DataSet(vector,label2));
            }
        }

        List<DataSet> merge = new ArrayList<>();
        for(int i = 0; i < numExamples; i++)
            merge.add(list.remove(0));
        if(leftOver == null)
            leftOver = new ArrayList<>();
        if(!list.isEmpty())
            leftOver.addAll(list);

        curr = DataSet.merge(merge);
        cursor += curr.numExamples();

    }

    public Word2Vec getVec() {
        return vec;
    }

    @Override
    public int inputColumns() {
        return vec.getLayerSize() * vec.getWindow();
    }

    @Override
    public int totalOutcomes() {
        return 5;
    }

    @Override
    public boolean hasMore() {
        return leftOver != null && !leftOver.isEmpty() || iter.hasNext();
    }
}
