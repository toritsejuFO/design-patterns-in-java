package co.learn.designpatterns.patterns.factory.factorymethod;

class Point {
    private double x, y;

    private Point(double a, double b) {
        x = a;
        y = b;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static class Factory {
        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }
}


public class Demo {
    public static void main(String[] args) {
        Point p = Point.Factory.newPolarPoint(3, 4);
        System.out.println(p);

        p = Point.Factory.newCartesianPoint(3, 4);
        System.out.println(p);
    }
}
