package pl.sda.refactoring.customers.event;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

public final class CompanyRegisteredEvent implements Event {

    private final UUID customerId;
    private final String email;
    private final String name;
    private final String vatNumber;
    private final boolean verified;

    public CompanyRegisteredEvent(UUID customerId, String email, String name, String vatNumber, boolean verified) {
        requireNonNull(customerId);
        requireNonNull(email);
        requireNonNull(name);
        requireNonNull(vatNumber);
        this.email = email;
        this.customerId = customerId;
        this.name = name;
        this.vatNumber = vatNumber;
        this.verified = verified;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public boolean isVerified() {
        return verified;
    }
}
