package co.learn.designpatterns.patterns.singleton.five;

import co.learn.log.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

enum EnumBasedSingleton {
    INSTANCE;

    private int value;

    EnumBasedSingleton() {
        this.value = 42;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class Demo {

    static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception {
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singleton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename) throws Exception {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (EnumBasedSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {

        String filename = "enumsingleton.bin";
//        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
//        saveToFile(singleton, filename);
//        singleton.setValue(222);

        EnumBasedSingleton singleton2 = readFromFile(filename);

//        Log.log(singleton);
        Log.log(singleton2);
//        Log.log(singleton == singleton2);

//        Log.log(singleton.getValue());
        Log.log(singleton2.getValue());
    }
}
