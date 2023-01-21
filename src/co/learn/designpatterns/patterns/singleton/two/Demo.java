package co.learn.designpatterns.patterns.singleton.two;

import co.learn.log.Log;

import java.io.File;
import java.io.IOException;

class StaticBlockSingleton {

    private static StaticBlockSingleton instance;

    public StaticBlockSingleton() throws IOException {
        Log.log("Initializing singleton");
        File.createTempFile(".", ".");
    }

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            Log.log(e);
        }
    }

    public StaticBlockSingleton getInstance() {
        return instance;
    }
}

public class Demo {
}
