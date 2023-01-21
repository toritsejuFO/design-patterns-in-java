package co.learn.designpatterns.patterns.decorator.two;

import co.learn.log.Log;

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
        return "A radius of " + radius;
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

class ColoredShape implements Shape {
    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + ", has color " + color;
    }
}

class TransparentShape implements Shape {
    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + ", has " + transparency + "% transparency";
    }
}

public class Demo {
    public static void main(String[] args) {
        Circle circle = new Circle(8);

        ColoredShape blueSquare = new ColoredShape(new Square(20), "blue");

        Log.log(blueSquare.info());

        TransparentShape transparentGreenShape = new TransparentShape(
                new ColoredShape(
                        new Circle(7),
                        "green")
                , 5);

        Log.log(transparentGreenShape.info());
    }
}
