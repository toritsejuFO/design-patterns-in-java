package co.learn.designpatterns.patterns.builder;

class Address1 {
    public String street, postcode, city;
    private static Address1Builder addressBuilder;

    public static Address1Builder builder () {
        addressBuilder = new Address1Builder();
        return addressBuilder;
    }

    public Address1 build() {
        return addressBuilder.build();
    }

    @Override
    public String toString() {
        return "Address1{" +
                "street=" + street +
                ", postcode=" + postcode +
                ", city=" + city +
                '}';
    }
}

class Job1 {
    public String company, position;
    public  int annualIncome;

    private static Job1Builder jobBuilder;

    public static Job1Builder builder() {
        jobBuilder = new Job1Builder();
        return jobBuilder;
    }

    public Job1 build() {
        return jobBuilder.build();
    }

    @Override
    public String toString() {
        return "Job1{" +
                "company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

public class Person3 {
    public Address1 address;
    public Job1 job;

    private static Person3Builder personBuilder;

    public static Person3Builder builder() {
        personBuilder = new Person3Builder();
        return personBuilder;
    }

    public Person3 build() {
        return personBuilder.build();
    }

    @Override
    public String toString() {
        return "Person3{" +
                "address=" + address +
                ", job=" + job +
                '}';
    }
}

class Person3Builder {
    Person3 person = new Person3();

    public Person3Builder address(Address1 address) {
        person.address = address;
        return this;
    }

    public Person3Builder job(Job1 job) {
        person.job = job;
        return this;
    }

    public Person3 build() {
        return person;
    }
}

class Address1Builder {
    protected Address1 address = new Address1();

    public Address1Builder street(String street) {
        address.street = street;
        return this;
    }

    public Address1Builder postcode(String postcode) {
        address.postcode = postcode;
        return this;
    }

    public Address1Builder city(String city) {
        address.city = city;
        return this;
    }

    public Address1 build() {
        return address;
    }
}

class Job1Builder {
    protected Job1 job = new Job1();

    public Job1Builder company(String company) {
        job.company = company;
        return this;
    }

    public Job1Builder position(String position) {
        job.position = position;
        return this;
    }

    public Job1Builder annualIncome(int annualIncome) {
        job.annualIncome = annualIncome;
        return this;
    }

    public Job1 build() {
        return job;
    }
}

class Demo4 {
    public static void main(String[] args) {
        Person3 p = Person3
                .builder()
                .address(
                        Address1.builder().street("Best Close").postcode("121000").city("Warri").build()
                )
                .job(
                        Job1.builder().company("Carbon").annualIncome(100).position("Software Engineer").build()
                )
                .build();

        System.out.println(p);
        System.out.println(p.address.city);
    }
}