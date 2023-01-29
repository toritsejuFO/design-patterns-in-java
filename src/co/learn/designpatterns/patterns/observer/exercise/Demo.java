package co.learn.designpatterns.patterns.observer.exercise;

import co.learn.log.Log;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

class Game {
    // todo
    private final List<Rat> rats = new ArrayList<>();

    public void enter(Rat rat) {
        rats.add(rat);
        rats.forEach(r -> r.attack = rats.size());
    }

    public void removeRat(Rat rat) {
        rats.remove(rat);
        rat.attack = 1;
        rats.forEach(r -> r.attack = rats.size());
    }
}

class Rat implements Closeable {
    public int attack = 1;
    private Game game;

    public Rat(Game game) {
        this.game = game;
        // todo: rat enters game here
        this.game.enter(this);
    }

    @Override
    public void close() {
        // todo: rat dies ;(
        this.game.removeRat(this);
    }

    @Override
    public String toString() {
        return this.hashCode() + "'s Attack: " + this.attack;
    }
}

public class Demo {
    public static void main(String[] args) {
        Game game = new Game();
        Rat rat = new Rat(game);
        Rat rat2 = new Rat(game);
        Rat rat3 = new Rat(game);
        Rat rat4 = new Rat(game);

        Log.logLine(rat);
        Log.logLine(rat2);
        Log.logLine(rat3);
        Log.logLine(rat4);

        rat.close();
        rat3.close();
        Log.logLine();

        Log.logLine(rat);
        Log.logLine(rat2);
        Log.logLine(rat3);
        Log.logLine(rat4);
    }
}
