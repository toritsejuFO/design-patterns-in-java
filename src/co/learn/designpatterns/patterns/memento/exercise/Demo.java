package co.learn.designpatterns.patterns.memento.exercise;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Token {
    private int value = 0;

    public Token(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Memento {
    private final List<Token> tokens;

    public Memento(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}

class TokenMachine {
    public List<Token> tokens = new ArrayList<>();

    public Memento addToken(int value) {
        Token token = new Token(value);
        tokens.add(token);
//        return new Memento(tokens.stream().collect(Collectors.toList())); // this works as well
        return new Memento(List.copyOf(tokens));
    }

    public Memento addToken(Token token) {
        tokens.add(token);
        return new Memento(List.copyOf(tokens));
    }

    public void revert(Memento m) {
        tokens = m.getTokens();
    }
}

public class Demo {
    public static void main(String[] args) {
        TokenMachine machine = new TokenMachine();
        Memento m1 = machine.addToken(1);
        Memento m2 = machine.addToken(2);
        Memento m3 = machine.addToken(new Token(3));
        Memento m4 = machine.addToken(new Token(4));

        StringBuilder v = new StringBuilder();
        machine.tokens.forEach(token -> v.append(token.getValue()));
        Assertions.assertEquals("1234", v.toString());

        machine.revert(m1);
        StringBuilder v1 = new StringBuilder();
        machine.tokens.forEach(token -> v1.append(token.getValue()));
        Assert.assertEquals("1", v1.toString());

        machine.revert(m3);
        StringBuilder v3 = new StringBuilder();
        machine.tokens.forEach(token -> v3.append(token.getValue()));
        Assertions.assertEquals("123", v3.toString());

        machine.revert(m2);
        StringBuilder v2 = new StringBuilder();
        machine.tokens.forEach(token -> v2.append(token.getValue()));
        Assertions.assertEquals("12", v2.toString());
    }
}