package co.learn.designpatterns.patterns.bridge.one;

import co.learn.log.Log;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.aopalliance.intercept.MethodInterceptor;

interface Renderer {
    public void renderCircle(float radius);
}

class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        Log.log("Drawing a circle with lines and points, of radius " + radius);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        Log.log("Drawing a circle with pixels, of radius " + radius);
    }
}

abstract class Shape {
    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();

    public abstract void resize(float factor);
}

class Circle extends Shape {
    public float radius;

    @Inject
    public Circle(Renderer renderer) {
        super(renderer);
    }

    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}

class ShapeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Renderer.class).to(VectorRenderer.class);
    }
}

class Demo {
    public static void main(String[] args) {
        run1();
    }

    private static void run1() {
        RasterRenderer rasterRenderer = new RasterRenderer();
        VectorRenderer vectorRenderer = new VectorRenderer();

        Circle circle = new Circle(vectorRenderer, 5);
        circle.draw();

        circle.resize(2);
        circle.draw();
    }

    private static void run2() {
        Injector injector = Guice.createInjector(new ShapeModule());
        Circle instance = injector.getInstance(Circle.class);

        instance.radius = 3;
        instance.draw();

        instance.resize(2);
        instance.draw();

    }
}
