package org.deeplearning4j.rottentomatoes.data.visualization;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.plot.Tsne;
import org.deeplearning4j.rottentomatoes.data.SentenceToPhraseMapper;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * Created by agibsonccc on 10/19/14.
 */
public class Visualization {


    public static void main(String[] args) throws Exception {
        ClassPathResource r = new ClassPathResource("/train.tsv");
        if(r.exists()) {
            InputStream is = r.getInputStream();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("train.tsv")));
            IOUtils.copy(is, bos);
            bos.flush();
            bos.close();
            is.close();
        }
        SentenceIterator docIter = new CollectionSentenceIterator(new SentenceToPhraseMapper(new File("train.tsv")).sentences());
        TokenizerFactory factory = new DefaultTokenizerFactory();
        Word2Vec  vec = new Word2Vec.Builder().iterate(docIter)
                .tokenizerFactory(factory).batchSize(100)
                .learningRate(2.5e-2)
                .iterations(3).minWordFrequency(1)
                .layerSize(300).windowSize(5).build();
        vec.fit();
        FileUtils.writeLines(new File("vocab.csv"),vec.getCache().words());




        Tsne t = new Tsne.Builder()
                .setMaxIter(100).stopLyingIteration(20).build();


        vec.getCache().plotVocab(t);

    }

}
