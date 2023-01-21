package co.learn.designpatterns.patterns.proxy.two;


import co.learn.log.Log;

import java.util.Objects;

class Property<T> {
    private String name;
    private T value;

    public Property(T value, String name) {
        this.value = value;
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        // do some logging here
        this.value = value;
        Log.logLine(name + " is set to " + value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property<?> property = (Property<?>) o;

        return Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}

class Creature {
    private Property<Integer> agility = new Property<>(10, "agility");

    public Integer getAgility() {
        return agility.getValue();
    }

    public void setAgility(Integer value) {
        // we cannot do agility = value, sadly
        agility.setValue(value);
    }
}

public class Demo {
    public static void main(String[] args) {
        Creature c = new Creature();
        c.setAgility(12);
        c.setAgility(34);
        c.setAgility(17);
        c.setAgility(76);
    }
}
