package co.learn.designpatterns.patterns.chainofresponsibility.exercise;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

enum Statistic {
    ATTACK, DEFENSE
}

abstract class Creature {
    protected Game game;
    protected int baseAttack = 1;
    protected int baseDefense = 1;

    public Creature(Game game, int baseAttack, int baseDefense) {
        this.game = game;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    public abstract int getAttack();

    public abstract int getDefense();

    public abstract void query(Object source, StatQuery statQuery);
}

class Goblin extends Creature {
    protected Goblin(Game game, int baseAttack, int baseDefense) {
        super(game, baseAttack, baseDefense);
    }

    public Goblin(Game game) {
        // todo
        super(game, 1, 1);
    }

    @Override
    public int getAttack() {
        // todo
        StatQuery statQuery = new StatQuery(Statistic.ATTACK);
        for (Creature creature : game.creatures) {
            creature.query(this, statQuery);
        }
        return statQuery.result;
    }

    @Override
    public int getDefense() {
        // todo
        StatQuery statQuery = new StatQuery(Statistic.DEFENSE);
        for (Creature creature : game.creatures) {
            creature.query(this, statQuery);
        }
        return statQuery.result;
    }

    public void query(Object source, StatQuery statQuery) {
        if (this == source) {
            switch (statQuery.statistic) {
                case ATTACK:
                    statQuery.result += baseAttack;
                    break;
                case DEFENSE:
                    statQuery.result += baseDefense;
                    break;
            }
        } else if (statQuery.statistic.equals(Statistic.DEFENSE)) {
            statQuery.result += 1;
        }
    }
}

class GoblinKing extends Goblin {
    public GoblinKing(Game game) {
        // todo
        super(game, 3, 3);
    }

    public void query(Object source, StatQuery statQuery) {
        if (!this.equals(source) && statQuery.statistic.equals(Statistic.ATTACK)) {
            statQuery.result += 1;
        } else {
            super.query(source, statQuery);
        }
    }
}

class Game {
    public List<Creature> creatures = new ArrayList<>();
}

class StatQuery {
    public int result;
    Statistic statistic;

    public StatQuery(Statistic statistic) {
        this.statistic = statistic;
    }
}

public class Demo {
    public static void main(String[] args) {
        Game game = new Game();
        Goblin goblin = new Goblin(game);
        game.creatures.add(goblin);

        GoblinKing goblinKing = new GoblinKing(game);
        game.creatures.add(goblinKing);

        Goblin goblin2 = new Goblin(game);
        game.creatures.add(goblin2);

        assertEquals(2, goblin.getAttack());
        assertEquals(3, goblin.getDefense());

        assertEquals(3, goblinKing.getAttack());
        assertEquals(5, goblinKing.getDefense());

        assertEquals(2, goblin2.getAttack());
        assertEquals(3, goblin2.getDefense());
    }
}
