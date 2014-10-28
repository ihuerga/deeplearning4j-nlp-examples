package org.deeplearning4j.rottentomatoes.movingwindow;

import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.MultipleEpochsIterator;
import org.deeplearning4j.iterativereduce.actor.multilayer.ActorNetworkRunner;
import org.deeplearning4j.models.classifiers.dbn.DBN;
import org.deeplearning4j.models.featuredetectors.rbm.RBM;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;

import org.deeplearning4j.rottentomatoes.data.test.fetcher.RottenTomatoesWordVectorDataFetcher;
import org.deeplearning4j.rottentomatoes.data.train.RottenTomatoesTrainDataSetIterator;
import org.deeplearning4j.scaleout.conf.Conf;
import org.nd4j.linalg.api.activation.Activations;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * Created by agibsonccc on 10/19/14.
 */
public class MovingWindowDBN {



    public static void main(String[] args) {
        DataSetIterator iter = new MultipleEpochsIterator(1,new RottenTomatoesTrainDataSetIterator(800,800,new RottenTomatoesWordVectorDataFetcher()));
        NeuralNetConfiguration conf = new NeuralNetConfiguration.Builder().nIn(iter.inputColumns()).hiddenUnit(RBM.HiddenUnit.RECTIFIED)
                .visibleUnit(RBM.VisibleUnit.GAUSSIAN).iterations(10).momentum(0.5f)
                .nOut(5).activationFunction(Activations.hardTanh()).learningRate(1e-6f).regularization(true)
                .l2(2e-4f).build();
        ActorNetworkRunner runner = new ActorNetworkRunner(iter);


        Conf c = new Conf();
        c.setConf(conf);
        c.setMultiLayerClazz(DBN.class);
        c.setLayerConfigs();
        c.setSplit(100);
        c.getLayerConfigs().get(c.getLayerConfigs().size() - 1).setActivationFunction(Activations.softMaxRows());
        c.getLayerConfigs().get(c.getLayerConfigs().size() - 1).setLossFunction(LossFunctions.LossFunction.MCXENT);

        c.setLayerSizes(new int[]{1000,500});

        runner.setup(c);
        runner.train();







    }




}
