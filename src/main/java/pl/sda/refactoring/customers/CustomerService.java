package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;
import static pl.sda.refactoring.util.PatternValidator.emailMatches;
import static pl.sda.refactoring.util.PatternValidator.nameMatches;
import static pl.sda.refactoring.util.PatternValidator.peselMatches;
import static pl.sda.refactoring.util.PatternValidator.vatMatches;

import java.time.LocalDateTime;
import java.util.UUID;
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;
import pl.sda.refactoring.util.EmailSender;

public class CustomerService {

    private final CustomerDao dao;
    private final EmailSender emailSender;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerDao dao, EmailSender emailSender,
        CustomerMapper customerMapper) {
        requireNonNull(dao);
        requireNonNull(emailSender);
        requireNonNull(customerMapper);
        this.dao = dao;
        this.emailSender = emailSender;
        this.customerMapper = customerMapper;
    }

    /**
     * Register new person type customer
     *
     * @param personDto@return
     */
    public boolean registerPerson(RegisterPersonDto personDto) {
        if (!isValidPerson(personDto) || personExists(personDto)) {
            return false;
        }

        final var customer = customerMapper.newPerson(personDto);
        String subj;
        String body;
        if (personDto.isVerified()) {
            customer.setVerf(true);
            customer.setVerfTime(LocalDateTime.now());
            customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
            subj = "Your are now verified customer!";
            body = "<b>Hi " + personDto.getFirstName() + "</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            customer.setVerf(false);
            subj = "Waiting for verification";
            body = "<b>Hi " + personDto.getFirstName() + "</b><br/>" +
                "We registered you in our service. Please wait for verification!";
        }
        customer.setId(UUID.randomUUID());
        dao.save(customer);
        // send email to customer
        emailSender.sendEmail(personDto.getEmail(), subj, body);
        return true;
    }

    private boolean isValidPerson(RegisterPersonDto personDto) {
        return emailMatches(personDto.getEmail()) &&
            nameMatches(personDto.getFirstName()) &&
            nameMatches(personDto.getLastName()) &&
            peselMatches(personDto.getPesel());
    }

    private boolean personExists(RegisterPersonDto personDto) {
        return dao.emailExists(personDto.getEmail()) || dao.peselExists(personDto.getPesel());
    }

    /**
     * Register new company type customer
     *
     * @param companyDto@return
     */
    public boolean registerCompany(RegisterCompanyDto companyDto) {
        if (!isValidCompany(companyDto) || companyExists(companyDto)) {
            return false;
        }

        final var customer = customerMapper.newCompany(companyDto);

        String subj;
        String body;
        if (companyDto.isVerified()) {
            customer.setVerf(companyDto.isVerified());
            customer.setVerfTime(LocalDateTime.now());
            customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
            subj = "Your are now verified customer!";
            body = "<b>Your company: " + companyDto.getName() + " is ready to make na order.</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            customer.setVerf(false);
            subj = "Waiting for verification";
            body = "<b>Hello</b><br/>" +
                "We registered your company: " + companyDto.getName() + " in our service. Please wait for verification!";
        }
        customer.setId(UUID.randomUUID());
        dao.save(customer);
        // send email to customer
        emailSender.sendEmail(companyDto.getEmail(), subj, body);
        return true;
    }

    private boolean companyExists(RegisterCompanyDto companyDto) {
        return dao.emailExists(companyDto.getEmail()) || dao.vatExists(companyDto.getVat());
    }

    /**
     * Set new address for customer
     * @param cid
     * @param str
     * @param zipcode
     * @param city
     * @param country
     * @return
     */
    public boolean updateAddress(UUID cid, String str, String zipcode, String city, String country) {
        var result = false;
        var customer = dao.findById(cid);
        if (customer.isPresent()) {
           var object = customer.get();
           object.setAddrStreet(str);
           object.setAddrZipCode(zipcode);
           object.setAddrCity(city);
           object.setAddrCountryCode(country);
           dao.save(object);
           result = true;
        }
        return result;
    }

    private boolean isValidCompany(RegisterCompanyDto companyDto) {
        return companyDto.getEmail() != null && companyDto.getName() != null && companyDto.getVat() != null;
    }
}
