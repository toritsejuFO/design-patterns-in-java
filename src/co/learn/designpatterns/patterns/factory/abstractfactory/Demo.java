package co.learn.designpatterns.patterns.factory.abstractfactory;

import javafx.util.Pair;
import org.reflections8.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

interface HotDrink {
    public void consume();
}

interface HotDrinkFactory {
    public HotDrink prepare(int amount);
}

class Log {
    public static void log(String message) {
        System.out.println(message);
    }
}

class Tea implements HotDrink {
    @Override
    public void consume() {
        Log.log("This is a delicious tea");
    }
}

class Coffee implements HotDrink {
    @Override
    public void consume() {
        Log.log("This coffee is delicious");
    }
}

class TeaFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        Log.log("Prepare " + amount + " cup(s) of tea");
        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        Log.log("Prepare " + amount + " cup(s) of coffee");
        return new Coffee();
    }
}

class HotDrinkMachine {
    private final List<Pair<String, HotDrinkFactory>> nameFactories = new ArrayList<>();

    public HotDrinkMachine() throws Exception {
        Set<Class<? extends HotDrinkFactory>> types = new Reflections("co.learn.designpatterns.patterns.factory.abstractfactory").getSubTypesOf(HotDrinkFactory.class);
        for (Class<? extends HotDrinkFactory> type : types) {
            nameFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink(String type, int amount) {
        HotDrink drink = null;
        
        for(Pair<String, HotDrinkFactory> factory: nameFactories) {
            if (factory.getKey().equals(type)) {
                drink = factory.getValue().prepare(amount);
            }
        }
        
        if (drink == null) {
            Log.log("This type of hot drink is not available yet");
            throw new UnsupportedOperationException("Unsupported drink");
        }
        
        return drink;
    }
}

public class Demo {
    public static void main(String[] args) throws Exception {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink;

        drink = machine.makeDrink("Coffee", 2);
        drink.consume();

        drink = machine.makeDrink("Tea", 2);
        drink.consume();

        drink = machine.makeDrink("Caffe", 2);
        drink.consume();
    }
}
