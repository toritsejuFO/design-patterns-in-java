package co.learn.designpatterns.patterns.nullobject.two;

import java.lang.reflect.Proxy;

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
    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf) {
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[]{itf},
                ((proxy, method, args) -> {
                    if (method.getReturnType().equals(Void.TYPE)) {
                        return null;
                    } else {
                        return method.getReturnType().getConstructor().newInstance();
                    }
                })
        );
    }

    public static void main(String[] args) {
        Log noOpLog = noOp(Log.class);
        BankAccount bankAccount = new BankAccount(noOpLog);
        bankAccount.deposit(100);
    }
}
