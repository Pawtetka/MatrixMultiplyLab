package CommonFilesFinding;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommonFilesFinding {
    public static void main(String[] args) throws IOException {
        List<String> words = Arrays.asList("Duna", "Paul");

        WordsCounter wordCounter = new WordsCounter();
        Folder folder = Folder.fromDirectory(new File("./Lab4/Resources"));
        for (String documentName : wordCounter.getWords(folder, words))
            System.out.println(documentName);
    }
}
