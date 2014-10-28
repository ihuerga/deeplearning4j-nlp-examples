package org.deeplearning4j.rottentomatoes.fetcher;

import static org.junit.Assert.*;

import org.deeplearning4j.rottentomatoes.data.test.fetcher.RottenTomatoesWordVectorDataFetcher;
import org.junit.Test;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesWordVectorDataFetcherTest {
    @Test
    public void testWordVectors() {
        RottenTomatoesWordVectorDataFetcher fetcher = new RottenTomatoesWordVectorDataFetcher();
        fetcher.fetch(10);
        assertEquals(10,fetcher.next().numExamples());
    }


}
