package co.learn.designpatterns.patterns.prototype.one;

import co.learn.log.Log;

import java.util.Arrays;

class Address implements Cloneable {
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}

class Person {
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Person(names.clone(), (Address) address.clone());
    }
}

public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person john = new Person(new String[]{"John", "Smith"}, new Address("Best Close", 4));

        Person jane = (Person) john.clone();
        jane.names[0] = "Jane";
        jane.address.houseNumber = 2;

        Log.log(john);
        Log.log(jane);
    }
}
