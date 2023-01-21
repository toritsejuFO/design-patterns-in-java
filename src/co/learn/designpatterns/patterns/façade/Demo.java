package co.learn.designpatterns.patterns.façade;

import co.learn.log.Log;

import java.util.ArrayList;
import java.util.List;

class Buffer {
    private char[] buf;
    private int lineWidth;

    public Buffer(int lineWidth, int lineHeight) {
        this.buf = new char[lineWidth * lineHeight];
        this.lineWidth = lineWidth;
    }

    public char charAt(int x, int y) {
        return buf[y * lineWidth + x];
    }

    public void put(char c, int x, int y) {
        buf[y * lineWidth + x] = c;
    }
}

class Viewport {
    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public Viewport(Buffer buffer, int width, int height, int offsetX, int offsetY) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y) {
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

class Console {
    private List<Viewport> viewports = new ArrayList<>();
    private int width;
    private int height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
    }

    public static Console newConsole(int width, int height) {
        Buffer buffer = new Buffer(width, height);
        buffer.put('A', 0, 0);
        buffer.put('B', width - 1, height - 1);
        Viewport viewport = new Viewport(buffer, width, height, 0, 0);
        Console console = new Console(width, height);
        console.addViewport(viewport);
        return console;
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Viewport vp : viewports) {
                    Log.log(vp.charAt(x, y));
                }
            }
            Log.logLine();
        }
    }

    public void render(String string) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Viewport vp : viewports) {
                    Log.log(vp.charAt(x, y));
                }
            }
            Log.logLine();
        }
    }

    public void print(String string) {
        render(string);
    }
}

public class Demo {
    public static void main(String[] args) {
        Console console = Console.newConsole(50, 7);
        console.render();
    }
}
