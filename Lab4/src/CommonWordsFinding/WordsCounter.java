package CommonWordsFinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class WordsCounter {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    private static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static Set<String> getAllWords(Document document) {
        Set<String> allWords = new HashSet<>();

        for (String line : document.getLines()) {
            allWords.addAll(Arrays.asList(wordsIn(line)));
        }

        return allWords;
    }

    public Set<String> getWords(List<Document> documents){
        return forkJoinPool.invoke(new DocumentsSearcher(documents));
    }

}
