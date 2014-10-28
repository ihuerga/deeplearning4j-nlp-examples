package org.deeplearning4j.rottentomatoes.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agibsonccc on 10/23/14.
 */
public class SentenceToSubPhrase  implements Serializable {

    private String sentence;
    private List<String> subPhrases = new ArrayList<>();
    private List<Integer> labels = new ArrayList<>();
    private int sentenceLabel = 0;


    /**
     * Add a sentence with an associated label
     * @param sentence the sentence to add
     * @param label the label to add
     */
    public void add(String sentence,int label) {
        if(this.sentence == null || sentence.length() > this.sentence.length()) {
            this.sentence = sentence;
            this.sentenceLabel = label;
        }
        subPhrases.add(sentence);
        labels.add(label);
    }


    /**
     * Add a sentence
     * @param sentence the sentence to add
     */
    public void add(String sentence) {
        if(this.sentence == null || sentence.length() > this.sentence.length())
            this.sentence = sentence;
        subPhrases.add(sentence);
    }


    /**
     * Returns whether th only one phrase
     * is present for a sentence
     * @return whether one phrase is present with a sentence
     */
    public boolean isOnlySentence() {
        return subPhrases.size() == 1;
    }

    /**
     * Returns the spans for each sentence that is a sub phrase
     * @return the spans for each phrase wrt the base sentence
     *
     */
   public List<PhraseSpan> spans() {
       List<PhraseSpan> spans = new ArrayList<>();
       for(String s : subPhrases) {
           int begin = sentence.indexOf(s);
           spans.add(new PhraseSpan(begin,begin + s.length()));


       }

       return spans;

   }


    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<String> getSubPhrases() {
        return subPhrases;
    }

    public void setSubPhrases(List<String> subPhrases) {
        this.subPhrases = subPhrases;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }

    public int getSentenceLabel() {
        return sentenceLabel;
    }

    public void setSentenceLabel(int sentenceLabel) {
        this.sentenceLabel = sentenceLabel;
    }

    @Override
    public String toString() {
        return "SentenceToSubPhrase{" +
                "sentence='" + sentence + '\'' +
                ", subPhrases=" + subPhrases +
                ", labels=" + labels +
                ", sentenceLabel=" + sentenceLabel +
                '}';
    }
}
