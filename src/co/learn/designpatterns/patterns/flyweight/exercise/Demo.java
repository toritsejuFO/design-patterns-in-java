package co.learn.designpatterns.patterns.flyweight.exercise;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Sentence {
    private List<String> plainText = new ArrayList<>();
    private Map<Integer, Integer> wordMap = new HashMap<>();
    private List<Integer> tokenized = new ArrayList<>();

    public Sentence(String plainText) {
        // todo
        int index = 0;

        for (String word : plainText.split(" ")) {
            if (!this.plainText.contains(word)) {
                this.plainText.add(String.valueOf(word));
            }
            wordMap.put(index++, this.plainText.indexOf(word));
        }
    }

    public WordToken getWord(int index) {
        // todo
        WordToken wordToken = new WordToken();
        tokenized.add(index);
        return wordToken;
    }

    @Override
    public String toString() {
        // todo
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Integer> word : wordMap.entrySet()) {
            if (tokenized.contains(word.getKey())) {
                sb.append(plainText.get(word.getValue()).toUpperCase());
            } else {
                sb.append(plainText.get(word.getValue()));
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    static class WordToken {
        public boolean capitalize;
    }
}

public class Demo {
    public static void main(String[] args) {
        Sentence sentence = new Sentence("alpha beta gamma[] beta");
        sentence.getWord(1).capitalize = true;
        Log.log(sentence);
    }
}

