package org.deeplearning4j.rottentomatoes.data;

import org.deeplearning4j.models.rntn.Tree;
import org.deeplearning4j.text.movingwindow.Window;
import org.deeplearning4j.text.movingwindow.Windows;

import java.util.List;

/**
 * Created by agibsonccc on 10/24/14.
 */
public class SpanUtil {



    public static void mapLabels(List<Tree> trees,SentenceToSubPhrase phrases) {

    }


    public static void mapLabels(List<Window> windows,SentenceToSubPhrase phrases,int windowSize) {
        windows.addAll(Windows.windows(phrases.getSentence(), windowSize));
        for(Window window : windows) {
            window.setLabel(String.valueOf(phrases.getSentenceLabel()));
        }
        if(phrases.isOnlySentence())
           return;
        else {
            for(Window window : windows) {
                List<PhraseSpan> spans = phrases.spans();
                int lengthEncounted = Integer.MAX_VALUE;
                for(int i = 0; i < spans.size(); i++) {
                    PhraseSpan span = spans.get(i);
                    if(span.overlaps(window)) {
                        int lengthDiff = lengthDiff(spans.get(i),window);
                        if(lengthDiff < lengthEncounted) {
                            window.setLabel(String.valueOf(phrases.getLabels().get(i)));
                            lengthEncounted = lengthDiff;
                        }
                    }
                    //assuming spans are in sequential order we can terminate early when the span isn't
                    else if(span.getBegin() >= window.getEnd())
                        break;

                }
            }

        }
    }

    public static int length(Window window) {
        return Math.abs(window.getEnd() - window.getBegin());
    }

    public static int length(Tree tree) {
        return Math.abs(tree.getEnd() - tree.getBegin());
    }


    public static int length(PhraseSpan span) {
        return Math.abs(span.getEnd() - span.getBegin());
    }


    public static int lengthDiff(Window w1,Window window) {
        return Math.abs(length(w1) - length(window));
    }

    public static int lengthDiff(Tree tree,Window window) {
        return Math.abs(length(tree) - length(window));
    }

    public static int lengthDiff(Tree tree,Tree tw) {
        return Math.abs(length(tree) - length(tw));
    }

    public static int lengthDiff(PhraseSpan tree,Tree tw) {
        return Math.abs(length(tree) - length(tw));
    }

    public static int lengthDiff(PhraseSpan tree,PhraseSpan tw) {
        return Math.abs(length(tree) - length(tw));
    }

    public static int lengthDiff(PhraseSpan tree,Window tw) {
        return Math.abs(length(tree) - length(tw));
    }
}
