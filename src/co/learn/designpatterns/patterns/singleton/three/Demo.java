package co.learn.designpatterns.patterns.singleton.three;

import co.learn.log.Log;

import java.util.concurrent.atomic.AtomicReference;

class LazySingleton {
    private static LazySingleton instance;

//    public static synchronized LazySingleton getInstance() {
//        Log.log(Thread.currentThread().getName() + "\n");
//        if (instance == null) {
//            Log.log("Initializing lazy singleton");
//            instance = new LazySingleton();
//        } else {
//            Log.log("Not initializing, instance exists already");
//        }
//        return instance;
//    }

    // double-checked locking
    public static synchronized LazySingleton getInstance() {
        Log.log(Thread.currentThread().getName() + "\n");
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    Log.log("Initializing lazy singleton");
                    instance = new LazySingleton();
                }
            }
        } else {
            Log.log("Not initializing, instance exists already");
        }
        return instance;
    }
}

public class Demo {
    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            LazySingleton.getInstance();
        }, "Thread 1");

        Thread thread2 = new Thread(() -> {
            LazySingleton.getInstance();
        }, "Thread 2");

        thread2.start();
        thread1.start();
    }
}
