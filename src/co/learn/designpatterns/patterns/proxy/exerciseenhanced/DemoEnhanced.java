package co.learn.designpatterns.patterns.proxy.exerciseenhanced;

import co.learn.log.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Human {
    String drink();

    String drive();

    String drinkAndDrive();
}

class Person implements Human {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String drink() {
        return "drinking";
    }

    public String drive() {
        return "driving";
    }

    public String drinkAndDrive() {
        return "driving while drunk";
    }
}

class ResponsiblePerson implements InvocationHandler {
    private Person person;

    public ResponsiblePerson(Person person) {
        // todo
        this.person = person;
    }

    public String drink() {
        return person.getAge() < 18 ? "too young to drive" : person.drink();
    }

    public String drive() {
        return person.getAge() < 16 ? "too young to drink" : person.drive();
    }

    public String drinkAndDrive() {
        return "dead";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("drink")) return drink();
        if (method.getName().equals("drive")) return drive();
        if (method.getName().equals("drinkAndDrive")) return drinkAndDrive();
        return method.invoke(person, args);
    }
}

class Demo {
    @SuppressWarnings("unchecked")
    public static Human enforceResponsibility(Person person, Class<Human> itf) {
        return (Human) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[] {itf},
                new ResponsiblePerson(person));
    }

    public static void main(String[] args) {
        Person person = new Person(21);
        Human personWithResponsibility = enforceResponsibility(person, Human.class);
        Log.logLine(personWithResponsibility.drink());
        Log.logLine(personWithResponsibility.drive());
        Log.logLine(personWithResponsibility.drinkAndDrive());

        Person person2 = new Person(5);
        Human person2WithResponsibility = enforceResponsibility(person2, Human.class);
        Log.logLine(person2WithResponsibility.drink());
        Log.logLine(person2WithResponsibility.drive());
        Log.logLine(person2WithResponsibility.drinkAndDrive());
    }
}
