package co.learn.log;

public class Log {
    public static void logLine(Object message) {
        System.out.println(message);
    }

    public static void logLine() {
        System.out.println();
    }

    public static void log(Object message) {
        System.out.print(message);
    }
}
