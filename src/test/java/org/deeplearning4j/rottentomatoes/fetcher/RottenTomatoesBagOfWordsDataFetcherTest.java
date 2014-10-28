package org.deeplearning4j.rottentomatoes.fetcher;

import static org.junit.Assert.*;

import org.deeplearning4j.rottentomatoes.data.train.fetcher.RottenTomatoesBagOfWordsDataFetcher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesBagOfWordsDataFetcherTest {
    private static Logger log = LoggerFactory.getLogger(RottenTomatoesBagOfWordsDataFetcherTest.class);

    @Test
    public void testDataFetcher() {
        RottenTomatoesBagOfWordsDataFetcher fetcher = new RottenTomatoesBagOfWordsDataFetcher();
        System.out.println(fetcher.getCountVectorizer().vocab().numWords());
        fetcher.fetch(10);
        assertEquals(10,fetcher.next().numExamples());
    }



}
