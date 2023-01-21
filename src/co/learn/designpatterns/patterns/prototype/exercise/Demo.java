package co.learn.designpatterns.patterns.prototype.exercise;

import co.learn.log.Log;

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point (Point point) {
        this(point.x, point.y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line copy() {
        return new Line(start, end);
    }

    public Line deepCopy() {
        return new Line(new Point(start), new Point(end));
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

public class Demo {
    public static void main(String[] args) {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(3, 3);

        Line line1 = new Line(point1, point2);

        Line line2 = line1.deepCopy();
//        line2.end = new Point(4, 4);
        line2.end.y = 4;

        Log.log(line1);
        Log.log(line2);
    }
}
