package org.deeplearning4j.rottentomatoes.data;

import org.deeplearning4j.models.rntn.Tree;
import org.deeplearning4j.text.movingwindow.Window;

/**
 * Represents a begin and end index
 * for a particular sub set of text.
 *
 * @author Adam Gibson
 */
public class PhraseSpan {
    private int begin,end;

    public PhraseSpan(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public boolean overlaps(Window window) {
        return begin >= window.getBegin()  && end <= window.getEnd();
    }

    public boolean overlaps(Tree tree) {
        return begin >= tree.getBegin() && end <= tree.getEnd();
    }


    public boolean subsumedBy(Tree tree) {
        return tree.getBegin() > begin && tree.getEnd() < end;
    }


    public boolean subsumedBy(Window window) {
        return window.getBegin() > begin && window.getEnd() < end;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "PhraseSpan{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
