package co.learn.designpatterns.patterns.factory.exercise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Person {
    public int id;
    public String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory {
    static int index = 0;

    public Person createPerson(String name) {
        return new Person(index++, name);
    }
}

class Log {
    public static void log(String message) {
        System.out.println(message);
    }
}

public class Demo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Nkiz", "Toriboi", "Gloria");
        List<Person> persons;

        persons = names.stream().map(name -> new PersonFactory().createPerson(name)).collect(Collectors.toList());

        for (Person person : persons) {
            Log.log(person.id + ": " + person.name);
        }
    }
}
