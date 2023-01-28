package co.learn.designpatterns.patterns.nullobject.exercise;

interface Log {
    // max # of elements in the log
    int getRecordLimit();

    // number of elements already in the log
    int getRecordCount();

    // expected to increment record count
    void logInfo(String message);
}

class Account {
    private Log log;

    public Account(Log log) {
        this.log = log;
    }

    public void someOperation() throws Exception {
        int c = log.getRecordCount();
        log.logInfo("Performing an operation");
        if (c + 1 != log.getRecordCount())
            throw new Exception();
        if (log.getRecordCount() >= log.getRecordLimit())
            throw new Exception();
    }
}

class NullLog implements Log {
    // todo
    private int record;

    @Override
    public int getRecordLimit() {
        return record + 1;
    }

    @Override
    public int getRecordCount() {
        return record;
    }

    @Override
    public void logInfo(String message) {
        record += 1;
    }
}

public class Demo {
    public static void main(String[] args) {
        Log log = new NullLog();
        Account account = new Account(log);
    }
}
