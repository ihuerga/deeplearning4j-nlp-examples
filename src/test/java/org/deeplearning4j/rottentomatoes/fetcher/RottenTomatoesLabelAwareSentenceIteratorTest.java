package org.deeplearning4j.rottentomatoes.fetcher;

import static org.junit.Assert.*;

import org.deeplearning4j.rottentomatoes.data.train.sentence.RottenTomatoesLabelAwareSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.junit.Test;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class RottenTomatoesLabelAwareSentenceIteratorTest {

    @Test
    public void testIter() {
        LabelAwareSentenceIterator iter = new RottenTomatoesLabelAwareSentenceIterator();
        assertTrue(iter.hasNext());
        //due to the nature of a hashmap internally, may not be the same everytime
        String sentence = iter.nextSentence();
        assertTrue(!sentence.isEmpty());
    }


}
