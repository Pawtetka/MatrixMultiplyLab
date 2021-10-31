package TextAnalyzer;

import java.util.HashMap;
import java.util.List;

public class WordsCounter {
    public static HashMap<String, Integer> compute(List<String> words) {
        HashMap<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            wordCounts.putIfAbsent(word, word.length());
        }

        return wordCounts;
    }
}
