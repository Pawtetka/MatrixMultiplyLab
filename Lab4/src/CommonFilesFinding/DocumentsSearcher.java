package CommonFilesFinding;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class DocumentsSearcher extends RecursiveTask<List<String>> {
    private final Folder folder;
    private final List<String> words;

    public DocumentsSearcher(Folder folder, List<String> searchedWords) {
        super();
        this.folder = folder;
        this.words = searchedWords;
    }

    @Override
    protected List<String> compute() {
        List<RecursiveTask<String>> forks = new LinkedList<>();

        for (Document doc : folder.getDocuments())
        {
            DocumentSearcher task = new DocumentSearcher(doc, words);
            forks.add(task);
            task.fork();
        }

        List<String> result = new ArrayList<>();

        for (RecursiveTask<String> task : forks)
        {
            String taskResult = task.join();
            if (taskResult == null) continue;

            result.add(taskResult);
        }

        return result;
    }

}
