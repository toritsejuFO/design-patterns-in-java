package co.learn.designpatterns.patterns.builder;

public class Person1 {
    // address
    public String street, postcode, city;

    // employment
    public String company, position;
    public  int annualIncome;

    @Override
    public String toString() {
        return "Person1{" +
                "street='" + street + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

class Person1Builder {
    Person1 person = new Person1();

    public Person1AddressBuilder address() {
        return new Person1AddressBuilder(person);
    }

    public Person1JobBuilder job() {
        return new Person1JobBuilder(person);
    }

    public Person1 build() {
        return person;
    }
}

class Person1AddressBuilder extends Person1Builder {
    public Person1AddressBuilder(Person1 person) {
        this.person = person;
    }

    public Person1AddressBuilder street(String street) {
        person.street = street;
        return this;
    }

    public Person1AddressBuilder postcode(String postcode) {
        person.postcode = postcode;
        return this;
    }

    public Person1AddressBuilder city(String city) {
        person.city = city;
        return this;
    }
}

class Person1JobBuilder extends Person1Builder {
    public Person1JobBuilder(Person1 person) {
        this.person = person;
    }

    public Person1JobBuilder company(String company) {
        person.company = company;
        return this;
    }

    public Person1JobBuilder position(String position) {
        person.position = position;
        return this;
    }

    public Person1JobBuilder annualIncome(int annualIncome) {
        person.annualIncome = annualIncome;
        return this;
    }
}

class Demo2 {
    public static void main(String[] args) {
        Person1Builder pb = new Person1Builder();

        Person1 p = pb
                .address().street("Best Close").postcode("121000").city("Warri")
                .job().company("Carbon").annualIncome(100).position("Software Engineer")
                .build();

        System.out.println(p);
    }
}
