package org.deeplearning4j.rottentomatoes.data.visualization;

import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.models.classifiers.dbn.DBN;
import org.deeplearning4j.models.featuredetectors.autoencoder.SemanticHashing;
import org.deeplearning4j.models.featuredetectors.rbm.RBM;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.plot.Tsne;
import org.deeplearning4j.rottentomatoes.data.SentenceToPhraseMapper;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by agibsonccc on 10/19/14.
 */
public class Visualization {


    public static void main(String[] args) throws Exception {
        SentenceIterator docIter = new CollectionSentenceIterator(new SentenceToPhraseMapper(new ClassPathResource("/train.tsv").getFile()).sentences());
        TokenizerFactory factory = new DefaultTokenizerFactory();
        Word2Vec  vec = new Word2Vec.Builder().iterate(docIter)
                .tokenizerFactory(factory).batchSize(100000)
                .learningRate(2.5e-2)
                .iterations(1).minWordFrequency(10)
                .layerSize(100).windowSize(5).build();
        vec.fit();
       




        Tsne t = new Tsne.Builder()
                .setMaxIter(100).stopLyingIteration(20).build();


        vec.getCache().plotVocab(t);

    }

}
