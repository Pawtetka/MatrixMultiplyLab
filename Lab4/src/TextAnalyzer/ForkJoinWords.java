package TextAnalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinWords extends RecursiveTask<HashMap<String, Integer>> {
    private final List<String> words;
    private final HashMap<String, Integer> resultWordCounts = new HashMap<>();
    private static final int THRESHOLD = 20000;

    public ForkJoinWords(List<String> words){
        this.words = words;
    }

    @Override
    protected HashMap<String, Integer> compute(){
        if (this.words.size() > THRESHOLD) {
            ForkJoinTask.invokeAll(createTasks()).stream()
                    .map(ForkJoinTask::join)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> this.resultWordCounts.putIfAbsent(entry.getKey(), entry.getValue()));
            return this.resultWordCounts;
        } else {
            for (String word : words) {
                this.resultWordCounts.putIfAbsent(word, word.length());
            }
            return this.resultWordCounts;
        }
    }

    private Collection<ForkJoinWords> createTasks() {
        List<ForkJoinWords> tasks = new ArrayList<>();
        tasks.add(new ForkJoinWords(words.subList(0, words.size() / 2)));
        tasks.add(new ForkJoinWords(words.subList(words.size() / 2, words.size())));
        return tasks;
    }
}
