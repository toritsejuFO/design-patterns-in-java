package co.learn.designpatterns.patterns.nullobject.one;

interface Log {
    public void info(String message);
    public void warn(String message);
}

class ConsoleLog implements Log {
    @Override
    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("WARNING: " + message);
    }
}

class NullLog implements Log {
    @Override
    public void info(String message) {}

    @Override
    public void warn(String message) {}
}

class BankAccount {
    private Log log;
    private int balance;

    public BankAccount(Log log) {
        this.log = log;
    }

    public void deposit(int amount) {
        balance += amount;
        log.info("Deposited " + amount);
    }
}

public class Demo {
    public static void main(String[] args) {
        Log consoleLog = new ConsoleLog();
        Log nullLog = new NullLog();
        BankAccount bankAccount = new BankAccount(nullLog);
        bankAccount.deposit(100);
    }
}
