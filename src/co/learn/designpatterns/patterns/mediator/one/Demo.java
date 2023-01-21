package co.learn.designpatterns.patterns.mediator.one;

import java.util.ArrayList;
import java.util.List;

class Person {
    public String name;
    public ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receive(String sender, String message) {
        String s = sender + ": '" + message + "'";
        System.out.println("[" + name + "'s chat session] " + s);
        chatLog.add(s);
    }

    public void say(String message) {
        room.broadcast(name, message);
    }

    public void privateMessage(String to, String message) {
        room.sendDM(name, to, message);
    }

    public void join(ChatRoom room) {
        String joinMsg = name + " joins the chat";
        room.broadcast("room", joinMsg);

        this.room = room;
        room.addPerson(this);
    }
}

class ChatRoom {
    private List<Person> people = new ArrayList<>();

    public void join(Person p) {
        String joinMsg = p.name + " joins the chat";
        broadcast("room", joinMsg);

        p.room = this;
        people.add(p);
    }

    public void broadcast(String source, String message) {
        for (Person person : people)
            if (!person.name.equals(source))
                person.receive(source, message);
    }

    public void sendDM(String source, String destination, String message) {
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }
}

public class Demo {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();

        Person john = new Person("John");
        Person jane = new Person("Jane");

        room.join(john); // no message here
        room.join(jane);

        john.say("hi room");
        jane.say("oh, hey john");

        Person simon = new Person("Simon");
        room.join(simon);
        simon.say("hi everyone!");

        jane.privateMessage("Simon", "glad you could join us!");
    }
}
