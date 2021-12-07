package CommonWordsFinding;

import javax.print.Doc;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DocumentsSearcher extends RecursiveTask<Set<String>> {
    private final List<Document> documents;

    public DocumentsSearcher(List<Document> documents) {
        super();
        this.documents = documents;
    }

    @Override
    protected Set<String> compute() {
        Set<String> result;
        List<RecursiveTask<Set<String>>> forks = new LinkedList<>();

        for (Document doc : documents)
        {
            DocumentSearcher task = new DocumentSearcher(doc);
            forks.add(task);
            task.fork();
        }

        if (forks.size() == 0) return new HashSet<>();

        result = forks.get(0).join();
        forks.remove(0);
        for (RecursiveTask<Set<String>> task : forks) {
            result.retainAll(task.join());
        }

        return result;
    }

}
