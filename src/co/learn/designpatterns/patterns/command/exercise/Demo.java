package co.learn.designpatterns.patterns.command.exercise;

class Command {
    public Action action;
    public int amount;
    public boolean success;

    public Command(Action action, int amount) {
        this.action = action;
        this.amount = amount;
    }

    enum Action {
        DEPOSIT, WITHDRAW
    }
}

class Account {
    public int balance;

    public void process(Command c) {
        // todo
        if (c.action.equals(Command.Action.DEPOSIT)) {
            deposit(c.amount);
            c.success = true;
        }
        if (c.action.equals((Command.Action.WITHDRAW)) && balance > c.amount) {
            withdraw(c.amount);
            c.success = true;
        }


    }

    private void withdraw(int amount) {
        balance -= amount;
    }

    private void deposit(int amount) {
        balance += amount;
    }
}

public class Demo {
}
