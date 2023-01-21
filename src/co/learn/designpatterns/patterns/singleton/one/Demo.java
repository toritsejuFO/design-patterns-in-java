package co.learn.designpatterns.patterns.singleton.one;

import co.learn.log.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class BasicSingleton implements Serializable {
    public static final BasicSingleton INSTANCE = new BasicSingleton();
    private int value;

    private BasicSingleton() {
    }

    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected Object readResolve() {
        return INSTANCE;
    }
}

public class Demo {
    static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
        try(FileOutputStream fileOut = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String filename) throws Exception {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (BasicSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(111);

        String filename = "singleton.bin";

        saveToFile(singleton, filename);
        singleton.setValue(222);

        BasicSingleton singleton2 = readFromFile(filename);

        Log.logLine(singleton);
        Log.logLine(singleton2);
        Log.logLine(singleton == singleton2);

        Log.logLine(singleton.getValue());
        Log.logLine(singleton2.getValue());
    }
}
