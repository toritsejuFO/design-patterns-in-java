package co.learn.designpatterns.patterns.prototype.two;

import co.learn.log.Log;

class Address implements Cloneable {
    public String streetAddress, city, country;

    public Address(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    public Address(Address other) {
        this(other.streetAddress, other.city, other.country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Employee {
    public String name;
    public Address address;

    public Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Employee(Employee other) {
        this(other.name, new Address(other.address));
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}


public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee john = new Employee("John Doe", new Address("Best Close", "Warri", "Nigeria"));

        Employee jane = new Employee(john);
        jane.name = "Jane Smith";
        jane.address.streetAddress = "Ebrumede";

        Log.log(john);
        Log.log(jane);
    }
}
