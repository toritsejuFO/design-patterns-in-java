package co.learn.designpatterns.patterns.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Html {
    public String name, text;
    public List<Html> elements = new ArrayList<>();
    private int indentSize = 2;
    private final static String newLine = System.lineSeparator();

    public Html() {
    }

    public Html(String name, String text) {
        this.name = name;
        this.text = text;
    }

    private String toStringImpl(int indent) {
        StringBuilder sb = new StringBuilder();
        String i = String.join("", Collections.nCopies(indent * indentSize, " "));
        sb.append(String.format("%s<%s>%s", i, name, newLine));
        if (text != null && !text.isEmpty())
        {
            sb.append(String.join("", Collections.nCopies(indentSize*(indent+1), " ")))
                    .append(text)
                    .append(newLine);
        }

        for (Html e : elements)
            sb.append(e.toStringImpl(indent + 1));

        sb.append(String.format("%s</%s>%s", i, name, newLine));
        return sb.toString();
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }
}

class HtmlBuilder {
    private String rootName;
    private Html root = new Html();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }

    public HtmlBuilder addChild(String childName, String childText) {
        Html childElement = new Html(childName, childText);
        root.elements.add(childElement);
        return this;
    }

    public void clear() {
        root = new Html();
        root.name = rootName;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}

public class Demo {
    public static void main(String[] args) {
        HtmlBuilder builder = new HtmlBuilder("ul");

        builder
                .addChild("li", "Hello")
                .addChild("li", "World");

        System.out.println(builder);
    }
}
