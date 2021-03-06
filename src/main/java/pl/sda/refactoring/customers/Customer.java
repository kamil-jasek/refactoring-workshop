package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;

/**
 * The customer, can be person or company
 */
class Customer {

    // customer types
    public static final int COMPANY = 1;
    public static final int PERSON = 2;

    private UUID id;
    private int type;
    private LocalDateTime createTime;
    private String email;
    private LocalDateTime verificationTime;
    private boolean verified;
    private CustomerVerifier verifiedBy;
    private Address address;

    // company data
    private String companyName;
    private String companyVat;

    public Customer() {
    }

    public Customer(int type, LocalDateTime createTime, String email) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.createTime = createTime;
        this.email = email;
    }

    Customer setUpCompany(RegisterCompanyDto companyDto) {
        this.type = COMPANY;
        this.id = UUID.randomUUID();
        this.email = companyDto.getEmail();
        this.companyName = companyDto.getName();
        this.companyVat = companyDto.getVat();
        this.createTime = LocalDateTime.now();
        return this;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyVat() {
        return companyVat;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getVerificationTime() {
        return verificationTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public CustomerVerifier getVerifiedBy() {
        return verifiedBy;
    }

    void markVerified() {
        this.verified = true;
        this.verificationTime = LocalDateTime.now();
        this.verifiedBy = CustomerVerifier.AUTO_EMAIL;
    }

    void updateAddress(String str, String zipcode, String city, String country) {
        this.address = new Address(str, zipcode, city, country);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return type == customer.type && verified == customer.verified && Objects.equals(id, customer.id)
            && Objects.equals(createTime, customer.createTime) && Objects.equals(email, customer.email)
            && Objects.equals(verificationTime, customer.verificationTime) && verifiedBy == customer.verifiedBy
            && Objects.equals(address, customer.address) && Objects
            .equals(companyName, customer.companyName) && Objects.equals(companyVat, customer.companyVat);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(id, type, createTime, email, verificationTime, verified, verifiedBy, address, companyName,
                companyVat);
    }
}
