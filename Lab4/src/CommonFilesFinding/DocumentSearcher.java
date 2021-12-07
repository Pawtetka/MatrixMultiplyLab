package CommonFilesFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DocumentSearcher extends RecursiveTask<String>
{
    private final Document document;
    private final List<String> words;

    public DocumentSearcher(Document document, List<String> searchedWords)
    {
        super();
        this.document = document;
        this.words = searchedWords;
    }

    @Override
    protected String compute()
    {
        if(WordsCounter.getAllCommons(document, words) > 0)
        {
            return document.documentName;
        }
        return null;
    }
}
