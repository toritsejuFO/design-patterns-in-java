package co.learn.designpatterns.patterns.builder;

class Address {
    public String street, postcode, city;

    @Override
    public String toString() {
        return "Address{" +
                "street=" + street +
                ", postcode=" + postcode +
                ", city=" + city +
                '}';
    }
}

class Job {
    public String company, position;
    public  int annualIncome;

    @Override
    public String toString() {
        return "Job{" +
                "company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

public class Person2 {
    public Address address;

    public Job job;

    @Override
    public String toString() {
        return "Person2{" +
                "address=" + address +
                ", job=" + job +
                '}';
    }
}

class Person2Builder {
    Person2 person = new Person2();

    public Person2Builder address(Address address) {
        person.address = address;
        return this;
    }

    public Person2Builder job(Job job) {
        person.job = job;
        return this;
    }

    public Person2 build() {
        return person;
    }
}

class AddressBuilder {
    protected Address address;

    public AddressBuilder () {
        address = new Address();
    }

    public AddressBuilder street(String street) {
        address.street = street;
        return this;
    }

    public AddressBuilder postcode(String postcode) {
        address.postcode = postcode;
        return this;
    }

    public AddressBuilder city(String city) {
        address.city = city;
        return this;
    }

    public Address build() {
        return address;
    }
}

class JobBuilder {
    protected Job job;

    public JobBuilder() {
        job = new Job();
    }

    public JobBuilder company(String company) {
        job.company = company;
        return this;
    }

    public JobBuilder position(String position) {
        job.position = position;
        return this;
    }

    public JobBuilder annualIncome(int annualIncome) {
        job.annualIncome = annualIncome;
        return this;
    }

    public Job build() {
        return job;
    }
}

class Demo3 {
    public static void main(String[] args) {
        Person2Builder pb = new Person2Builder();

        Person2 p = pb
                .address(
                        new AddressBuilder().street("Best Close").postcode("121000").city("Warri").build()
                )
                .job(
                        new JobBuilder().company("Carbon").annualIncome(100).position("Software Engineer").build()
                )
                .build();

        System.out.println(p);
        System.out.println(p.address.street);
        System.out.println(p.job.annualIncome);
    }
}
