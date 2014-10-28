package org.deeplearning4j.rottentomatoes.movingwindow;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.classifiers.dbn.DBN;
import org.deeplearning4j.util.SerializationUtils;

import java.io.File;

/**
 * Created by agibsonccc on 10/23/14.
 */
public class EvalMovingWindowDBN {

    public static void main(String[] args) {
        Evaluation eval = new Evaluation();
        DBN d = SerializationUtils.readObject(new File("model-saver"));

    }

}
