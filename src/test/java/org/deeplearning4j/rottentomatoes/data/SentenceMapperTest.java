package org.deeplearning4j.rottentomatoes.data;

import static org.junit.Assert.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by agibsonccc on 10/23/14.
 */
public class SentenceMapperTest {

    @Test
    public void testSentenceMapper() throws Exception {
        ClassPathResource train  = new ClassPathResource("/train.tsv");
        File f = train.getFile();
        InputStream is = train.getInputStream();
        List<String> lines =IOUtils.readLines(is);
        is.close();
        SentenceToPhraseMapper mapper = new SentenceToPhraseMapper(f);
        assertTrue(!mapper.getSentenceToPhrase().isEmpty());
        assertTrue(mapper.getSentenceToPhrase().size() < lines.size());
        assertEquals(63,mapper.getSentenceToPhrase().get(1).getSubPhrases().size());


    }

    @Test
    public void testSentenceMapperTest() throws Exception {
        ClassPathResource train  = new ClassPathResource("/test.tsv");
        File f = train.getFile();
        InputStream is = train.getInputStream();
        List<String> lines = IOUtils.readLines(is);
        is.close();
        SentenceToPhraseMapper mapper = new SentenceToPhraseMapper(f);
        assertTrue(!mapper.getSentenceToPhrase().isEmpty());
        assertTrue(mapper.getSentenceToPhrase().size() < lines.size());




    }



}
