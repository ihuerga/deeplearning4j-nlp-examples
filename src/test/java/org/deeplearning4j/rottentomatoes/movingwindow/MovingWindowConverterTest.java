package org.deeplearning4j.rottentomatoes.movingwindow;


import org.apache.commons.io.IOUtils;
import org.deeplearning4j.rottentomatoes.data.SentenceToPhraseMapper;
import org.deeplearning4j.rottentomatoes.data.SentenceToSubPhrase;
import org.deeplearning4j.text.movingwindow.Window;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by agibsonccc on 10/24/14.
 */
public class MovingWindowConverterTest {

    @Test
    public void testMovingWindowConverter() throws Exception {
        ClassPathResource train  = new ClassPathResource("/train.tsv");
        File f = train.getFile();
        InputStream is = train.getInputStream();
        List<String> lines = IOUtils.readLines(is);
        is.close();
        SentenceToPhraseMapper mapper = new SentenceToPhraseMapper(f);
        assertTrue(!mapper.getSentenceToPhrase().isEmpty());
        assertTrue(mapper.getSentenceToPhrase().size() < lines.size());
        SentenceToSubPhrase phrase = mapper.getSentenceToPhrase().get(1);
        MovingWindowConverter convert = new MovingWindowConverter(phrase,5);
        List<Window> windows = convert.windows();
        assertEquals(63,mapper.getSentenceToPhrase().get(1).getSubPhrases().size());

    }


}
