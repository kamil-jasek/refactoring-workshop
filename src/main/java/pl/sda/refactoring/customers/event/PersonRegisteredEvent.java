package pl.sda.refactoring.customers.event;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

public final class PersonRegisteredEvent implements Event {

    private final UUID customerId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean verified;

    public PersonRegisteredEvent(UUID customerId, String firstName, String lastName, String email, boolean verified) {
        requireNonNull(customerId);
        requireNonNull(firstName);
        requireNonNull(lastName);
        requireNonNull(email);
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.verified = verified;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return verified;
    }
}
