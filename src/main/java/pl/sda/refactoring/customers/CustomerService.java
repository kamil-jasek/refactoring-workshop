package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;
import static pl.sda.refactoring.util.PatternValidator.emailMatches;
import static pl.sda.refactoring.util.PatternValidator.nameMatches;
import static pl.sda.refactoring.util.PatternValidator.peselMatches;

import java.time.LocalDateTime;
import java.util.UUID;
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;
import pl.sda.refactoring.customers.event.CompanyRegisteredEvent;
import pl.sda.refactoring.customers.event.PersonRegisteredEvent;

public class CustomerService {

    private final CustomerDao dao;
    private final NotificationPublisher notificationPublisher;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerDao dao, NotificationPublisher notificationPublisher,
        CustomerMapper customerMapper) {
        this.dao = requireNonNull(dao);
        this.notificationPublisher = requireNonNull(notificationPublisher);
        this.customerMapper = requireNonNull(customerMapper);
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
        if (personDto.isVerified()) {
            customer.setVerf(true);
            customer.setVerfTime(LocalDateTime.now());
            customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
        }
        dao.save(customer);
        notificationPublisher.publishEvent(new PersonRegisteredEvent(
            customer.getId(),
            customer.getfName(),
            customer.getlName(),
            customer.getEmail(),
            customer.isVerf()));
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
        if (companyDto.isVerified()) {
            customer.setVerf(companyDto.isVerified());
            customer.setVerfTime(LocalDateTime.now());
            customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
        }
        dao.save(customer);
        notificationPublisher.publishEvent(new CompanyRegisteredEvent(
            customer.getId(),
            customer.getEmail(),
            customer.getCompName(),
            customer.getCompVat(),
            customer.isVerf()));
        return true;
    }

    private boolean companyExists(RegisterCompanyDto companyDto) {
        return dao.emailExists(companyDto.getEmail()) || dao.vatExists(companyDto.getVat());
    }

    /**
     * Set new address for customer
     *
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
