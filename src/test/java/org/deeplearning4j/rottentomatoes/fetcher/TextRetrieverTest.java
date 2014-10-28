package org.deeplearning4j.rottentomatoes.fetcher;

import static org.junit.Assert.*;

import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.rottentomatoes.data.train.TextRetriever;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Map;

/**
 * Created by agibsonccc on 10/18/14.
 */
public class TextRetrieverTest {


    private TextRetriever retriever;
    public final static int NUM_TRAINING_EXAMPLES = 156060;

    @Before
    public void before() throws Exception {
       if(retriever == null) {
           ClassPathResource resource = new ClassPathResource("/train.tsv");
           File file = resource.getFile();
           retriever = new TextRetriever(file.getAbsolutePath());
       }

    }


    @Test
    public void testRetrieval() throws  Exception {
        Map<String,Pair<String,String>> data = retriever.data();
        Pair<String,String> phrase2 = data.get("2");
        assertEquals("A series of escapades demonstrating the adage that what is good for the goose",phrase2.getFirst());
        assertEquals("2",phrase2.getSecond());

    }


    @Test
    public void testNumPhrasesAndLabels() {
        assertEquals(NUM_TRAINING_EXAMPLES,retriever.phrases().size());
        assertEquals(NUM_TRAINING_EXAMPLES,retriever.labels().size());
    }





}
