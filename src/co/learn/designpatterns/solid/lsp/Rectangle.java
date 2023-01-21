package co.learn.designpatterns.solid.lsp;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return getWidth() * getHeight();
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}

class Square extends Rectangle {

    public Square(int size) {
        super(size, size);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
}

class Demo {
    public static void main(String[] args) {
        Rectangle r = new Rectangle(20, 10);
        useIt(r);

        Rectangle s = new Square(20);
        useIt(s);
    }

    public static void useIt(Rectangle r) {
        int width = r.getWidth();
        r.setHeight(10);
        System.out.println(String.format("Expected an area of %s, and got %s", width * 10, r.getArea()));
    }
}
