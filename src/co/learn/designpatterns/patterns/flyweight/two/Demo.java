package co.learn.designpatterns.patterns.flyweight.two;

import java.util.ArrayList;
import java.util.List;

class FormattedText {
    private String plainText;
    private boolean[] capitalize;

    public FormattedText(String plainText) {
        this.plainText = plainText;
        capitalize = new boolean[plainText.length()];
    }

    public void capitalize(int start, int end) {
        for (int i = start; i <= end; ++i)
            capitalize[i] = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            char c = plainText.charAt(i);
            sb.append(capitalize[i] ? Character.toUpperCase(c) : c);
        }
        return sb.toString();
    }
}

class BetterFormattedText {
    private String plainText;
    private List<TextRange> formatting = new ArrayList<>();

    public BetterFormattedText(String plainText) {
        this.plainText = plainText;
    }

    public void setRange(int start, int end, Action action) {
        TextRange range = new TextRange(start, end);
        range.action = action;
        formatting.add(range);
    }

    public char performAction(Action action, char c) {
        switch (action) {
            case CAPITALIZE:
                return Character.toUpperCase(c);
            case LOWERCASE:
                return Character.toLowerCase(c);
            default:
                return c;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < plainText.length(); ++i) {
            char c = plainText.charAt(i);
            for (TextRange range : formatting)
                if (range.covers(i) && range.actionIsSet())
                    c = performAction(range.action, c);
            sb.append(c);
        }
        return sb.toString();
    }

    public class TextRange {
        public int start, end;
        public Action action = null;

        public TextRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean actionIsSet() {
            return action != null;
        }

        public boolean covers(int position) {
            return position >= start && position <= end;
        }
    }
}

enum Action {
    CAPITALIZE,
    LOWERCASE,
    BOLD,
    ITALIC;
}

public class Demo {
    public static void main(String[] args) {
        FormattedText ft = new FormattedText("This is a brave new world");
        ft.capitalize(10, 15);
        System.out.println(ft);

        BetterFormattedText bft = new BetterFormattedText("Make NIGERIA Great Again");
        bft.setRange(13, 18, Action.CAPITALIZE);
        bft.setRange(5, 11, Action.LOWERCASE);
        System.out.println(bft);
    }
}
