package pl.sda.refactoring.customers.dto;

import java.util.UUID;

public class UpdateAddressDto {

    private final UUID customerId;
    private final String street;
    private final String zipCode;
    private final String city;
    private final String country;

    public UpdateAddressDto(UUID customerId, String street, String zipCode, String city, String country) {
        this.customerId = customerId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
