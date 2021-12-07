package CommonFilesFinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class WordsCounter {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    private static String[] wordsIn(String line)
    {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static Long getAllCommons(Document document, List<String> words) {
        long wordsCount = 0;

        for (String line : document.getLines()) {
            String[] wordsInLine = wordsIn(line);

            for (String word : wordsInLine)
            {
                if (words.contains(word))
                    wordsCount = wordsCount + 1;
            }
        }

        return wordsCount;
    }

    public List<String> getWords(Folder folder, List<String> searchedWords){
        return forkJoinPool.invoke(new DocumentsSearcher(folder, searchedWords));
    }

}
