package co.learn.designpatterns.patterns.observer.two;

import co.learn.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Person {
    Event<PropertyChangedEventData> propertyChangedEvent = new Event<>();
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (this.age == age) return;
        this.age = age;
        propertyChangedEvent.fire(new PropertyChangedEventData(this, "age", age));
    }
}

class PropertyChangedEventData {
    public Object source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEventData(Object source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

class Event<T> {
    private int count;
    private Map<Integer, Consumer<T>> observers = new HashMap<>();

    public Subscription addObserver(Consumer<T> observer) {
        int i = count;
        observers.put(count++, observer);
        return new Subscription(this, i);
    }

    public void fire(T eventData) {
        observers.forEach((index, consumer) -> consumer.accept(eventData));
    }

    class Subscription implements AutoCloseable {
        private Event<T> event;
        private int id;

        public Subscription(Event<T> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() {
            event.observers.remove(id);
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Person person = new Person();
        Event<PropertyChangedEventData>.Subscription sub = person.propertyChangedEvent.addObserver((eventData) -> {
            Log.logLine(eventData.source.getClass().getSimpleName() + "'s " + eventData.propertyName + " has changed to " + eventData.newValue);
        });

        person.setAge(19);
        person.setAge(20);
        person.setAge(21);
        sub.close();
        person.setAge(22);
    }
}
