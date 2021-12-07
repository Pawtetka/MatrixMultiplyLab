package CommonWordsFinding;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CommonWordsFinding {
    public static void main(String[] args) throws IOException {
        List<Document> files = new LinkedList<>();
        files.add(Document.fromFile(new File("./Lab4/resources/commonFirst.txt")));
        files.add(Document.fromFile(new File("./Lab4/resources/commonSecond.txt")));

        WordsCounter wordCounter = new WordsCounter();
        System.out.println(wordCounter.getWords(files));
    }
}
