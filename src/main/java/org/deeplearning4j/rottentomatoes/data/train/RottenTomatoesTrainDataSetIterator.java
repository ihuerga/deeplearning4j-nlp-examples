package org.deeplearning4j.rottentomatoes.data.train;

import org.deeplearning4j.datasets.iterator.BaseDatasetIterator;
import org.deeplearning4j.datasets.iterator.DataSetFetcher;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesTrainDataSetIterator extends BaseDatasetIterator {
    public RottenTomatoesTrainDataSetIterator(int batch, int numExamples, DataSetFetcher fetcher) {
        super(batch, numExamples, fetcher);
    }





}
