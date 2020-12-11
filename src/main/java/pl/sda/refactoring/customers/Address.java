package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Address {
    private String street;
    private String city;
    private String zipCode;
    private String countryCode;

    // for framework
    private Address() {
    }

    public Address(String street, String zipCode, String city, String country) {
        this.street = requireNonNull(street);
        this.zipCode = requireNonNull(zipCode);
        this.city = requireNonNull(city);
        this.countryCode = requireNonNull(country);
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects
            .equals(city, address.city) && Objects.equals(zipCode, address.zipCode)
            && Objects.equals(countryCode, address.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, zipCode, countryCode);
    }
}