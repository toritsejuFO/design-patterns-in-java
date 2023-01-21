package co.learn.designpatterns.patterns.builder;

public class Person {
    public String name;
    public String position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<T extends PersonBuilder<T>> {
    protected Person person = new Person();

    public T withName (String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    protected T self() {
        return (T) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    public EmployeeBuilder worksAt(String position) {
        person.position = position;
        return self();
    }

    @Override
    public EmployeeBuilder self() {
        return this;
    }
}

class Demo1 {
    public static void main(String[] args) {
        Person p = new EmployeeBuilder().withName("John").worksAt("Carbon").build();
        System.out.println(p);
    }
}
