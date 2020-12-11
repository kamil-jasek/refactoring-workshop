package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Objects;

final class Person extends Customer {

    private String firstName;
    private String lastName;
    private String pesel;

    // for framework
    private Person() {}

    Person(String email, String firstName, String lastName, String pesel) {
        super(PERSON, LocalDateTime.now(), email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName)
            && Objects.equals(pesel, person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }
}
