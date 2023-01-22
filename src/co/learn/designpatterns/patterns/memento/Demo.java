package co.learn.designpatterns.patterns.memento;

import static co.learn.log.Log.logLine;

class Memento {
    private final int balance;

    public Memento(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}

class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public Memento deposit(int amount) {
        balance += amount;
        return new Memento(this.balance);
    }

    public void restore(Memento memento) {
        balance = memento.getBalance();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

public class Demo {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(100);
        Memento m1 = acc.deposit(50);
        Memento m2 = acc.deposit(25);

        logLine(acc);

        acc.restore(m1);
        logLine(acc);

        acc.restore(m2);
        logLine(acc);
    }
}
