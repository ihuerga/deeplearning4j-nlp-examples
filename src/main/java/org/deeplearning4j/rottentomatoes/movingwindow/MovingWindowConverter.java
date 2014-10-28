package org.deeplearning4j.rottentomatoes.movingwindow;

import org.deeplearning4j.rottentomatoes.data.SentenceToSubPhrase;
import org.deeplearning4j.rottentomatoes.data.SpanUtil;
import org.deeplearning4j.text.movingwindow.Window;
import org.deeplearning4j.text.movingwindow.Windows;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Gibson
 */
public class MovingWindowConverter {
    private List<Window> windows = new ArrayList<>();

    public MovingWindowConverter(SentenceToSubPhrase phrases,int windowSize) {
        SpanUtil.mapLabels(windows, phrases, windowSize);

    }



    public List<Window> windows() {
        return windows;
    }


}
