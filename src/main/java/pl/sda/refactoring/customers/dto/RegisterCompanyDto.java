package pl.sda.refactoring.customers.dto;

public class RegisterCompanyDto {

    private final String email;
    private final String name;
    private final String vat;
    private final boolean verified;

    /**
     * @param email
     * @param name
     * @param vat
     * @param verified
     */
    public RegisterCompanyDto(String email, String name, String vat, boolean verified) {
        this.email = email;
        this.name = name;
        this.vat = vat;
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    public boolean isVerified() {
        return verified;
    }
}
