package pl.sda.refactoring.customers;

import static pl.sda.refactoring.util.PatternValidator.emailMatches;
import static pl.sda.refactoring.util.PatternValidator.nameMatches;
import static pl.sda.refactoring.util.PatternValidator.peselMatches;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;
import pl.sda.refactoring.util.EmailSender;

public class CustomerService {

    private CustomerDao dao;
    private EmailSender emailSender;

    /**
     * Register new person type customer
     *
     * @param personDto@return
     */
    public boolean registerPerson(RegisterPersonDto personDto) {
        var result = false;
        var customer = newPerson();
        if (isValidPerson(personDto) && !personExists(personDto)) {
            customer.setEmail(personDto.getEmail());
            customer.setfName(personDto.getFirstName());
            customer.setlName(personDto.getLastName());
            customer.setPesel(personDto.getPesel());
            result = true;
        }

        if (result) {
            customer.setCtime(LocalDateTime.now());
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
        }

        return result;
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

    private Customer newPerson() {
        var customer = new Customer();
        customer.setType(Customer.PERSON);
        return customer;
    }

    /**
     * Register new company type customer
     * @param email
     * @param name
     * @param vat
     * @param verified
     * @return
     */
    public boolean registerCompany(String email, String name, String vat, boolean verified) {
        var result = false;
        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        var isInDb = dao.emailExists(email) || dao.vatExists(vat);
        if (!isInDb) {
            if (email != null && name != null && vat != null) {
                var emailP = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                var emailM = emailP.matcher(email);
                if (emailM.matches()) {
                    customer.setEmail(email);
                }
                if (name.length() > 0 && nameMatches(name)) {
                    customer.setCompName(name);
                }
                if (vat.length() == 10 && vat.matches("/\\d{10}/")) {
                    customer.setCompVat(vat);
                }

                if (isValidPerson(customer)) {
                    result = true;
                }
            }
        }

        if (result == true) {
            customer.setCtime(LocalDateTime.now());
            String subj;
            String body;
            if (verified) {
                customer.setVerf(verified);
                customer.setVerfTime(LocalDateTime.now());
                customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
                subj = "Your are now verified customer!";
                body = "<b>Your company: " + name + " is ready to make na order.</b><br/>" +
                    "Thank you for registering in our service. Now you are verified customer!";
            } else {
                customer.setVerf(false);
                subj = "Waiting for verification";
                body = "<b>Hello</b><br/>" +
                    "We registered your company: " + name + " in our service. Please wait for verification!";
            }
            customer.setId(UUID.randomUUID());
            dao.save(customer);
            // send email to customer
            emailSender.sendEmail(email, subj, body);
        }

        return result;
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

    private boolean isValidPerson(Customer customer) {
        return customer.getEmail() != null && customer.getfName() != null && customer.getlName() != null && customer.getPesel() != null;
    }

    private boolean isValid(boolean flag) {
        return flag == true;
    }

    public void setDao(CustomerDao dao) {
        this.dao = dao;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
}
