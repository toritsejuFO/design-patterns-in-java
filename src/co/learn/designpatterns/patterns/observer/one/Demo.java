package co.learn.designpatterns.patterns.observer.one;

import co.learn.log.Log;

import javax.security.auth.login.LoginContext;
import java.util.ArrayList;
import java.util.List;

interface Observer<T> {
    void handler(PropertyChangedEvent<T> event);
}

class Observable<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    protected void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    protected void propertyChanged(T source, String propertyName, Object newValue) {
        for (Observer<T> o : observers) {
            o.handler(new PropertyChangedEvent<T>(source, propertyName, newValue));
        }
    }
}

class Person extends Observable<Person> {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (this.age == age) return;
        this.age = age;
        propertyChanged(this, "age", age);
    }
}

class PropertyChangedEvent<T> {
    public T source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEvent(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

class PersonObserver implements Observer<Person> {

    @Override
    public void handler(PropertyChangedEvent<Person> event) {
        Log.logLine(event.source.getClass().getSimpleName() + "'s " + event.propertyName + " has changed to " + event.newValue);
    }
}

public class Demo {
    public static void main(String[] args) {
        Person person = new Person();
        PersonObserver personObserver = new PersonObserver();
        person.subscribe(personObserver);

        for (int i = 20; i < 25; i++)
            person.setAge(i);
    }
}
