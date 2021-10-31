package TextAnalyzer;

import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TextAnalyzer {
    public static void main(String[] args) throws IOException {
        List<String> words = TextLoader.getWordsFromFile("./Lab4/resources/text.txt");

        System.out.printf("Number of words: %d\n\n", words.size());

        System.out.printf("SpeedUp = %.2f\n", computeSpeedUp(words));

        HashMap<String, Integer> res = WordsCounter.compute(words);
        computeCharacteristics(res);
    }

    private static void computeCharacteristics(HashMap<String, Integer> words){
        System.out.println();
        System.out.printf("Number of uniq words: %d\n", words.keySet().size());
        System.out.printf("Mean length: %f\n", words.values().stream().mapToDouble(i -> i).sum() /
                words.values().stream().mapToDouble(i -> i).count());
        System.out.printf("Var length: %f\n", (words.values().stream().mapToDouble(i -> Math.pow(i, 2)).sum() /
                words.values().stream().mapToDouble(i -> i).count()) -
                Math.pow((double) words.values().stream().mapToDouble(i -> i).sum() /
                        words.values().stream().mapToDouble(i -> i).count(), 2));
        System.out.println();
    }

    private static Pair<Long, Long> computeWordsCount(List<String> words){
        long currTime = System.nanoTime();
        HashMap<String, Integer> res = WordsCounter.compute(words);
        long currTimeSimple = System.nanoTime() - currTime;

        currTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        res = pool.submit(new ForkJoinWords(words)).join();
        long currTimeForkJoin = System.nanoTime() - currTime;

        return new Pair<Long, Long>(currTimeSimple, currTimeForkJoin);
    }

    private static double computeSpeedUp(List<String> words){
        double speedUp = 0;
        for(int i = 0; i < 10; i++){
            Pair<Long, Long> res = computeWordsCount(words);
            speedUp += (double) res.getKey() / res.getValue();
        }
        return speedUp / 10;
    }


}
