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
import org.deeplearning4j.rottentomatoes.data.train.sentence.RottenTomatoesLabelAwareSentenceIterator;
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
public class VisualizationSemanticHashing {


    public static void main(String[] args) throws Exception {
        SentenceIterator docIter = new CollectionSentenceIterator(new SentenceToPhraseMapper(new ClassPathResource("/train.tsv").getFile()).sentences());
        TokenizerFactory factory = new DefaultTokenizerFactory();
        Word2Vec  vec = new Word2Vec.Builder().iterate(docIter).tokenizerFactory(factory).batchSize(100000)
                .learningRate(2.5e-2).iterations(1)
                .layerSize(100).windowSize(5).build();
        vec.fit();

        NeuralNetConfiguration conf = new NeuralNetConfiguration.Builder().nIn(vec.getLayerSize()).nOut(vec.getLayerSize())
                .hiddenUnit(RBM.HiddenUnit.RECTIFIED).visibleUnit(RBM.VisibleUnit.GAUSSIAN).momentum(0.5f)
                .iterations(10).learningRate(1e-6f).build();

        InMemoryLookupCache l = (InMemoryLookupCache) vec.getCache();

        DBN d = new DBN.Builder()
                .configure(conf).hiddenLayerSizes(new int[]{250,100,2})
                .build();
        DataSet dPretrain = new DataSet(l.getSyn0(),l.getSyn0());
        DataSetIterator dPretrainIter =  new ListDataSetIterator(dPretrain.asList(),1000);
        while(dPretrainIter.hasNext()) {
            d.pretrain(dPretrainIter.next().getFeatureMatrix(), 1, 1e-6f, 10);


        }

        // d.pretrain(l.getSyn0(),1,1e-3f,1000);
        d.getOutputLayer().conf().setLossFunction(LossFunctions.LossFunction.RMSE_XENT);

        SemanticHashing s = new SemanticHashing.Builder().withEncoder(d)
                .build();

        d = null;

        dPretrainIter.reset();
        while(dPretrainIter.hasNext()) {
            s.fit(dPretrainIter.next());

        }




        Tsne t = new Tsne.Builder()
                .setMaxIter(100).stopLyingIteration(20).build();

        INDArray output = s.reconstruct(l.getSyn0(),4);
        l.getSyn0().data().flush();
        l.getSyn1().data().flush();
        s = null;
        System.out.println(Arrays.toString(output.shape()));
        t.plot(output,2,new ArrayList<>(vec.getCache().words()));
        vec.getCache().plotVocab(t);

    }

}
