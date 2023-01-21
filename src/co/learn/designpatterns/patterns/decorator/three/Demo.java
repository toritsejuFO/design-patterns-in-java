package co.learn.designpatterns.patterns.decorator.three;

import co.learn.log.Log;

import java.util.function.Supplier;

interface Shape {
    public String info();
}

class Circle implements Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public void resize(int factor) {
        radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + radius;
    }
}

class Square implements Shape {
    private int size;

    public Square(int size) {
        this.size = size;
    }

    @Override
    public String info() {
        return "A square of side " + size;
    }
}

class ColoredShape<T extends Shape> implements Shape {
    private Shape shape;
    private String color;

    public ColoredShape(Supplier<? extends T> constructor, String color) {
        this.shape = constructor.get();
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + ", has color " + color;
    }
}

class TransparentShape<T extends Shape> implements Shape {
    private Shape shape;
    private int transparency;

    public TransparentShape(Supplier<? extends T> constructor, int transparency) {
        this.shape = constructor.get();
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + ", has " + transparency + "% transparency";
    }
}

public class Demo {
    public static void main(String[] args) {
        ColoredShape<Square> blueSquare = new ColoredShape<>(() -> new Square(20), "blue");
        Log.log(blueSquare.info());

        TransparentShape<ColoredShape<Circle>> circle =
                new TransparentShape<>(() ->
                        new ColoredShape<>(() -> new Circle(6), "red"), 80);

        Log.log(circle.info());
    }
}

