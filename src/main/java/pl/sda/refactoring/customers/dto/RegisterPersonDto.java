package pl.sda.refactoring.customers.dto;

import static java.util.Objects.requireNonNull;

public final class RegisterPersonDto {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final boolean verified;

    /**
     * @param email
     * @param firstName
     * @param lastName
     * @param pesel
     * @param verified
     */
    public RegisterPersonDto(String email, String firstName, String lastName, String pesel, boolean verified) {
        requireNonNull(email);
        requireNonNull(firstName);
        requireNonNull(lastName);
        requireNonNull(pesel);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.verified = verified;
    }

    public String getEmail() {
        return email;
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

    public boolean isVerified() {
        return verified;
    }
}
