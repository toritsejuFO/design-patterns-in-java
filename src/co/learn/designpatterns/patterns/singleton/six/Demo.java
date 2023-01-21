package co.learn.designpatterns.patterns.singleton.six;

import co.learn.log.Log;

import java.util.HashMap;
import java.util.Map;

enum SubSystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK;
}

class Printer {
    private static int instanceCount = 0;
    private static Map<SubSystem, Printer> instances = new HashMap<>();

    private Printer() {
        instanceCount++;
        Log.log("Current instance count: " + instanceCount);
    }

    public static Printer getInstance(SubSystem ss) {
        if (!instances.containsKey(ss)) {
            instances.put(ss, new Printer());
        }
        return instances.get(ss);
    }
}

public class Demo {
    public static void main(String[] args) {
        Printer pri = Printer.getInstance(SubSystem.PRIMARY);
        Printer aux = Printer.getInstance(SubSystem.AUXILIARY);
        Printer aux2 = Printer.getInstance(SubSystem.AUXILIARY);
    }
}
