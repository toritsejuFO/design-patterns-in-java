package co.learn.designpatterns.patterns.interpreter.one;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element {
    int eval();
}

class Integer implements Element {
    public int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element {
    public OperationType type;
    public Element left;
    public Element right;

    @Override
    public int eval() {
        switch (type) {
            case ADDITION:
                return left.eval() + right.eval();
            case SUBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }

    enum OperationType {
        ADDITION,
        SUBTRACTION
    }
}

class Token {
    public TokenType type;
    public String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "`";
    }

    enum TokenType {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }
}

public class Demo {
    static List<Token> lex(String input) {
        ArrayList<Token> tokens = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '+':
                    tokens.add(new Token(Token.TokenType.PLUS, "+"));
                    break;
                case '-':
                    tokens.add(new Token(Token.TokenType.MINUS, "-"));
                    break;
                case ')':
                    tokens.add(new Token(Token.TokenType.RPAREN, ")"));
                    break;
                case '(':
                    tokens.add(new Token(Token.TokenType.LPAREN, "("));
                    break;
                default:
                    StringBuilder sb = new StringBuilder("" + input.charAt(i));
                    for (int j = i + 1; j < input.length(); j++) {
                        if (Character.isDigit(input.charAt(j))) {
                            sb.append(input.charAt(j));
                            ++i;
                        } else {
                            tokens.add(new Token(Token.TokenType.INTEGER, sb.toString()));
                            break;
                        }
                    }
                    break;
            }
        }

        return tokens;
    }

    static Element parse(List<Token> tokens) {
        BinaryOperation operation = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            switch (token.type) {
                case INTEGER:
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if (!haveLHS) {
                        operation.left = integer;
                        haveLHS = true;
                    } else
                        operation.right = integer;
                    break;
                case PLUS:
                    operation.type = BinaryOperation.OperationType.ADDITION;
                    break;
                case MINUS:
                    operation.type = BinaryOperation.OperationType.SUBTRACTION;
                    break;
                case LPAREN:
                    int j = i; // location of lparen
                    for (; j < tokens.size(); j++) {
                        if (tokens.get(j).type == Token.TokenType.RPAREN)
                            break;
                    }
                    List<Token> subTokens = tokens.stream().skip(i + 1).limit(j - i - 1).collect(Collectors.toList());
                    Element element = parse(subTokens);
                    if (!haveLHS) {
                        operation.left = element;
                        haveLHS = true;
                    } else
                        operation.right = element;
                    i = j;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + token.type);
            }
        }

        return operation;
    }

    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        Log.logLine(tokens.stream().map(Token::toString).collect(Collectors.joining("\t")));

        Log.logLine(input + " = "  + parse(tokens).eval());
    }
}
