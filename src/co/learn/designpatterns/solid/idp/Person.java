package co.learn.designpatterns.solid.idp;

import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Triplet extends Triple<Person, Relationship, Person> {
    private final Person p1;
    private final Relationship r;
    private final Person p2;

    public Triplet(Person p1, Relationship r, Person p2) {
        this.p1 = p1;
        this.r = r;
        this.p2 = p2;
    }

    @Override
    public Person getLeft() {
        return p1;
    }

    @Override
    public Relationship getMiddle() {
        return r;
    }

    @Override
    public Person getRight() {
        return p2;
    }
}

interface RelationshipBrowser {
    int getCountAsChildren(String name);
    int getCountAsParents(String name);
}

class Relationships implements RelationshipBrowser { // low-level
    private final List<Triplet> relations = new ArrayList<>();

    public List<Triplet> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet(parent, Relationship.PARENT, child));
        relations.add(new Triplet(child, Relationship.CHILD, parent));
    }

    @Override
    public int getCountAsChildren(String name) {
        return (int) relations.stream().filter(relation ->
                name.equals(relation.getLeft().getName())
                        && Relationship.CHILD == relation.getMiddle()).count();
    }

    @Override
    public int getCountAsParents(String name) {
        return (int) relations.stream().filter(relation ->
                name.equals(relation.getLeft().getName())
                        && Relationship.PARENT == relation.getMiddle()).count();
    }
}

class Research { // high-level
    private final RelationshipBrowser relationships;

    public Research(Relationships relationships) {
        this.relationships = relationships;
    }

    public int doResearch(String name, Relationship relationship) {
        int count = 0;
        switch (relationship) {
            case CHILD:
                count = relationships.getCountAsChildren(name);
                break;
            case PARENT:
                count = relationships.getCountAsParents(name);
                break;
            default:
                System.out.println("Invalid relationship");
                break;
        }
        return count;
    }
}

class Demo {
    public static void main(String[] args) {
        Person john = new Person("John");
        Person luke = new Person("Luke");
        Person mark = new Person("Mark");
        Person matthew = new Person("Matthew");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(john, mark);
        relationships.addParentAndChild(john, matthew);
        relationships.addParentAndChild(luke, matthew);

        Research research = new Research(relationships);

        int count = research.doResearch("John", Relationship.PARENT);
        System.out.printf("Parents named John occur in this relationship research data %s times%n", count);

        count = research.doResearch("Matthew", Relationship.PARENT);
        System.out.printf("Parents named Matthew occur in this relationship research data %s times%n", count);

        count = research.doResearch("Matthew", Relationship.CHILD);
        System.out.printf("Children named Matthew occur in this relationship research data %s times%n", count);

        count = research.doResearch("Mark", Relationship.CHILD);
        System.out.printf("Children named Mark occur in this relationship research data %s times%n", count);
    }
}
