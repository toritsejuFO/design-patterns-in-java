package co.learn.designpatterns.patterns.singleton.four;

class InnerStaticSingleton {
    public InnerStaticSingleton() {
    }

    private static class Impl {
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public static InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }
}

public class Demo {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            InnerStaticSingleton.getInstance();
        }, "Thread 1");

        Thread thread2 = new Thread(() -> {
            InnerStaticSingleton.getInstance();
        }, "Thread 2");

        thread2.start();
        thread1.start();
    }
}
