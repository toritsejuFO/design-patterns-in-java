package co.learn.designpatterns.patterns.command.one;

import co.learn.log.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface Command {
    void execute();

    void undo();
}

class BankAccount {
    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount) {
        balance += amount;
        Log.logLine("Deposited " + amount + ". Current Balance: " + balance);
    }

    public boolean withdraw(int amount) {
        if (balance - amount >= overdraftLimit) {
            balance -= amount;
            Log.logLine("Withdrew " + amount + ". Current Balance: " + balance);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

class BankAccountCommand implements Command {
    private BankAccount account;
    private Action action;
    private int amount;
    private boolean succeeded;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void execute() {
        switch (action) {
            case DEPOSIT:
                account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {
        switch (action) {
            case WITHDRAW:
                if (succeeded)
                    account.deposit(amount);
                break;
            case DEPOSIT:
                account.withdraw(amount);
                break;
        }
    }

    public enum Action {
        DEPOSIT,
        WITHDRAW
    }
}

public class Demo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        Log.logLine(account);

        List<BankAccountCommand> commands = Arrays.asList(
                new BankAccountCommand(account, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(account, BankAccountCommand.Action.WITHDRAW, 1000)
        );

        for (Command command : commands) {
            command.execute();
            Log.logLine(account);
        }

        Log.logLine();

        Collections.reverse(commands);
        for (Command command : commands) {
            command.undo();
            Log.logLine(account);
        }
    }
}
