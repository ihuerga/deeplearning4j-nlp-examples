package org.deeplearning4j.rottentomatoes.rntn;

import org.deeplearning4j.models.rntn.RNTN;
import org.deeplearning4j.models.rntn.Tree;
import org.deeplearning4j.rottentomatoes.data.train.fetcher.RottenTomatoesWordVectorDataFetcher;
import org.deeplearning4j.rottentomatoes.data.train.sentence.RottenTomatoesLabelAwareSentenceIterator;
import org.deeplearning4j.text.corpora.treeparser.TreeParser;
import org.deeplearning4j.text.corpora.treeparser.TreeVectorizer;
import org.nd4j.linalg.api.activation.Activations;

import java.util.Arrays;
import java.util.List;

/**
 * Created by agibsonccc on 10/19/14.
 */
public class RNTNTrain {

    public static void main(String[] args) throws Exception {
        RottenTomatoesWordVectorDataFetcher fetcher = new RottenTomatoesWordVectorDataFetcher();
        RottenTomatoesLabelAwareSentenceIterator iter = new RottenTomatoesLabelAwareSentenceIterator();
        RNTN t = new RNTN.Builder()
                .setActivationFunction(Activations.hardTanh()).setFeatureVectors(fetcher.getVec())
                .setUseTensors(true).build();

        TreeVectorizer vectorizer = new TreeVectorizer(new TreeParser());

        while(iter.hasNext()) {
            List<Tree> trees = vectorizer.getTreesWithLabels(iter.nextSentence(),iter.currentLabel(), Arrays.asList("0","1","2","3","4"));
            t.fit(trees);
        }

    }

}
