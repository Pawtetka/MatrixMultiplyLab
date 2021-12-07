package CommonWordsFinding;

import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DocumentSearcher extends RecursiveTask<Set<String>>
{
    private final Document document;

    public DocumentSearcher(Document document)
    {
        super();
        this.document = document;
    }

    @Override
    protected Set<String> compute()
    {
        return WordsCounter.getAllWords(document);
    }
}
