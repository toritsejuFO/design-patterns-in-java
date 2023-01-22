package co.learn.designpatterns.patterns.memento.exercise;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Token {
    public int value = 0;

    public Token(int value) {
        this.value = value;
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
        return new Memento(tokens.stream().map(t -> new Token(t.value)).collect(Collectors.toList()));
    }

    public Memento addToken(Token token) {
        tokens.add(token);
        return new Memento(tokens.stream().map(t -> new Token(t.value)).collect(Collectors.toList()));
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
        machine.tokens.forEach(token -> v.append(token.value));
        Assertions.assertEquals("1234", v.toString());

        machine.revert(m1);
        StringBuilder v1 = new StringBuilder();
        machine.tokens.forEach(token -> v1.append(token.value));
        Assert.assertEquals("1", v1.toString());

        machine.revert(m3);
        StringBuilder v3 = new StringBuilder();
        machine.tokens.forEach(token -> v3.append(token.value));
        Assertions.assertEquals("123", v3.toString());

        machine.revert(m2);
        StringBuilder v2 = new StringBuilder();
        machine.tokens.forEach(token -> v2.append(token.value));
        Assertions.assertEquals("12", v2.toString());

        machine.tokens.get(0).value = 9; // Ensure memento value returns correct snapshot even if token is modified
        machine.revert(m4);
        StringBuilder v4 = new StringBuilder();
        machine.tokens.forEach(token -> v4.append(token.value));
        Assertions.assertEquals("1234", v4.toString());
    }
}