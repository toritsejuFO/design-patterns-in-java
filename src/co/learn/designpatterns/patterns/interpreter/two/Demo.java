package co.learn.designpatterns.patterns.interpreter.two;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Evaluable {
    int evaluate();
}

class LexicalToken {
    public String token;
    public LexicalTokenType type;

    public LexicalToken(String token, LexicalTokenType type) {
        this.token = token;
        this.type = type;
    }

    @Override
    public String toString() {
        return "LexicalToken{" +
                "token='" + token + '\'' +
                ", type=" + type +
                '}';
    }

    enum LexicalTokenType {
        NUMBER,
        PLUS,
        MINUS,
        VAR
    }
}

class LexicalProcessor {
    public List<LexicalToken> tokenize(String expression) {
        List<LexicalToken> lexicalTokens = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            Character chr = expression.charAt(i);

            if (Character.isDigit(chr)) {
                StringBuilder number = new StringBuilder("" + chr);
                int j = i + 1;
                while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                    number.append(expression.charAt(j));
                    j++;
                }
                i = j - 1;
                lexicalTokens.add(new LexicalToken(number.toString(), LexicalToken.LexicalTokenType.NUMBER));
            } else if (chr.equals('-')) {
                lexicalTokens.add(new LexicalToken("-", LexicalToken.LexicalTokenType.MINUS));
            } else if (chr.equals('+')) {
                lexicalTokens.add(new LexicalToken("+", LexicalToken.LexicalTokenType.PLUS));
            } else if (Character.isAlphabetic(chr)) {
                StringBuilder variable = new StringBuilder("" + chr);
                int j = i + 1;
                while (j < expression.length() && Character.isAlphabetic(expression.charAt(j))) {
                    variable.append(expression.charAt(j));
                    j++;
                }
                i = j - 1;
                lexicalTokens.add(new LexicalToken(variable.toString(), LexicalToken.LexicalTokenType.VAR));
            }
            else {
                throw new UnsupportedOperationException("Unsupported Operation");
            }
        }

        return lexicalTokens;
    }
}

class Number implements Evaluable {
    int value;

    public Number(String value) {
        this.value = Integer.parseInt(value);
    }

    @Override
    public int evaluate() {
        return value;
    }
}

class Operation implements Evaluable {
    Number left;
    Number right;
    OperationType operator;

    @Override
    public int evaluate() {
        if (operator == OperationType.ADD) {
            return left.evaluate() + right.evaluate();
        } else if (operator == OperationType.SUBTRACT) {
            return left.evaluate() - right.evaluate();
        } else {
            throw new UnsupportedOperationException("Unsupported Operation");
        }
    }

    enum OperationType {
        ADD,
        SUBTRACT
    }
}

class Parser {
    public Evaluable parse(List<LexicalToken> lexicalTokens) {
        if (lexicalTokens.size() == 1) {
            return new Number(lexicalTokens.get(0).token);
        }

        Operation operation = new Operation();

        operation.left = new Number(lexicalTokens.get(0).token);
        operation.right = new Number(lexicalTokens.get(2).token);

        if (lexicalTokens.get(1).type.equals(LexicalToken.LexicalTokenType.PLUS)) {
            operation.operator = Operation.OperationType.ADD;
        } else if (lexicalTokens.get(1).type.equals(LexicalToken.LexicalTokenType.MINUS)) {
            operation.operator = Operation.OperationType.SUBTRACT;
        } else {
            throw new UnsupportedOperationException("Unsupported Operation");
        }

        if (lexicalTokens.size() == 3) {
            return operation;
        } else {
            List<LexicalToken> newLexicalTokens = new ArrayList<>();
            newLexicalTokens.add(new LexicalToken("" + operation.evaluate(), LexicalToken.LexicalTokenType.NUMBER));
            newLexicalTokens.addAll(lexicalTokens.subList(3, lexicalTokens.size()));
            return parse(newLexicalTokens);
        }
    }
}

class ExpressionProcessor {
    public Map<Character, Integer> variables = new HashMap<>();
    LexicalProcessor lexicalProcessor = new LexicalProcessor();
    Parser parser = new Parser();

    public int calculate(String expression) {
        List<LexicalToken> lexicalTokens = lexicalProcessor.tokenize(expression);
        for (int i = 0; i < lexicalTokens.size(); i++) {
            if (lexicalTokens.get(i).type.equals(LexicalToken.LexicalTokenType.VAR) && lexicalTokens.get(i).token.length() > 1) {
                return 0;
            }
            else if ( variables.containsKey(lexicalTokens.get(i).token.charAt(0))) {
                LexicalToken token = lexicalTokens.get(i);
                token.token = variables.get(lexicalTokens.get(i).token.charAt(0)).toString();
                lexicalTokens.set(i, token);
            }
        }

        Evaluable evaluable = parser.parse(lexicalTokens);
        return evaluable.evaluate();
    }
}

public class Demo {

    public static void main(String[] args) {
        ExpressionProcessor processor = new ExpressionProcessor();
        processor.variables.put('x', 3);
        processor.variables.put('y', 13);

        Log.logLine(processor.calculate("1"));
        Log.logLine(processor.calculate("1+2+xy"));
        Log.logLine(processor.calculate("10-2-x"));
        Log.logLine(processor.calculate("5+2-x"));
        Log.logLine(processor.calculate("y-2-x"));
        Log.logLine(processor.calculate("y-2*x"));
    }
}
