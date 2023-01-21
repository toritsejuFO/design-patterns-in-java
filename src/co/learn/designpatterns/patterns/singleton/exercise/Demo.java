package co.learn.designpatterns.patterns.singleton.exercise;

import java.util.function.Supplier;

class SingletonTester {
    public static boolean isSingleton(Supplier<Object> func) {
        Object o = func.get();
        Object o2 = func.get();
        return o == o2;
    }
}

public class Demo {
}
