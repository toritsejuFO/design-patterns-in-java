package co.learn.designpatterns.solid.ocp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum Color {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green");

    Color(String color) {
    }
}

enum Size {
    SMALL,
    MEDIUM,
    LARGE,
    HUGE
}

interface Specification<T> {
    public boolean isSatisfied(T specification);
}

interface Filter<T> {
    public Stream<T> filter(List<T> items, Specification<T> specification);
}

public class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product product) {
        return color.equals(product.color);
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product product) {
        return size.equals(product.size);
    }
}

class MultiSpecification implements Specification<Product> {
    private List<Specification<Product>> specifications;

    public MultiSpecification(List<Specification<Product>> specifications) {
        this.specifications = specifications;
    }

    @Override
    public boolean isSatisfied(Product product) {
        boolean satisfiesAll = true;
        for (Specification<Product> specification : specifications) {
            if (!specification.isSatisfied(product)) {
                satisfiesAll = false;
                break;
            }
        }
        return satisfiesAll;
    }
}

class BetterFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> products, Specification<Product> specification) {
        return products.stream().filter(specification::isSatisfied);
    }
}

class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> color.equals(p.color));
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> size.equals(p.size));
    }

    public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color) {
        List<Product> productsBySize = filterBySize(products, size).collect(Collectors.toList());
        return filterByColor(productsBySize, color);
    }
}

class Demo {
    public static void main(String[] args) {
        Product apple = new Product("apple", Color.GREEN, Size.LARGE);
        Product house = new Product("house", Color.BLUE, Size.HUGE);
        Product tree = new Product("tree", Color.GREEN, Size.HUGE);

        List<Product> products = List.of(apple, house, tree);

        ProductFilter productFilter = new ProductFilter();
        productFilter.filterByColor(products, Color.GREEN)
                .forEach(p -> System.out.println(String.format("%s is color %s", p.name, p.color)));

        BetterFilter betterFilter = new BetterFilter();
        betterFilter.filter(products, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(String.format("Better: %s is color %s", p.name, p.color)));

        System.out.println("\nMilti Spec: ");
        List<Specification<Product>> specifications = List.of(new ColorSpecification(Color.BLUE), new SizeSpecification(Size.HUGE));
        betterFilter.filter(products, new MultiSpecification(specifications))
                .forEach(p -> System.out.println(String.format("%s size is %s", p.name, p.size)));
    }
}
