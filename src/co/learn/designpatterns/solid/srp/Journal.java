package co.learn.designpatterns.solid.srp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Journal implements Serializable {
    private List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String entry) {
        entries.add(String.format("%s: %s", ++count, entry));
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }
}

class Persistence<T> {
    public void saveToFile(T item, String filename, boolean overwrite) throws FileNotFoundException {
        if (overwrite || !new File(filename).exists()) {
            try (PrintStream out = new PrintStream(filename)) {
                out.println(item.toString());
            }
        }
    }
}

class Demo {
    public static void main(String[] args) throws IOException {
        Journal journal = new Journal();
        journal.addEntry("I cried yesterday");
        journal.addEntry("I ate a bug");

        Persistence<Journal> persistence = new Persistence<>();
        persistence.saveToFile(journal, "journal.txt", true);

        Runtime.getRuntime().exec("open " + "./journal.txt");
        System.out.println(journal);
    }
}
