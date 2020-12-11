package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;
import static pl.sda.refactoring.util.PatternValidator.emailMatches;
import static pl.sda.refactoring.util.PatternValidator.nameMatches;
import static pl.sda.refactoring.util.PatternValidator.peselMatches;
import static pl.sda.refactoring.util.PatternValidator.vatMatches;

import pl.sda.refactoring.application.events.NotificationPublisher;
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;
import pl.sda.refactoring.customers.dto.UpdateAddressDto;
import pl.sda.refactoring.customers.event.CompanyRegisteredEvent;
import pl.sda.refactoring.customers.event.PersonRegisteredEvent;

class CustomerService {

    private final CustomerDao dao;
    private final NotificationPublisher notificationPublisher;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerDao dao, NotificationPublisher notificationPublisher,
        CustomerMapper customerMapper) {
        this.dao = requireNonNull(dao);
        this.notificationPublisher = requireNonNull(notificationPublisher);
        this.customerMapper = requireNonNull(customerMapper);
    }

    public boolean registerPerson(RegisterPersonDto personDto) {
        if (!isValidPerson(personDto) || personExists(personDto)) {
            return false;
        }
        final var customer = customerMapper.newPerson(personDto);
        if (personDto.isVerified()) {
            customer.markVerified();
        }
        dao.save(customer);
        notificationPublisher.publishEvent(new PersonRegisteredEvent(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.isVerified()));
        return true;
    }

    public boolean registerCompany(RegisterCompanyDto companyDto) {
        if (!isValidCompany(companyDto) || companyExists(companyDto)) {
            return false;
        }
        final var customer = customerMapper.newCompany(companyDto);
        if (companyDto.isVerified()) {
            customer.markVerified();
        }
        dao.save(customer);
        notificationPublisher.publishEvent(new CompanyRegisteredEvent(
            customer.getId(),
            customer.getEmail(),
            customer.getCompanyName(),
            customer.getCompanyVat(),
            customer.isVerified()));
        return true;
    }

    public boolean updateAddress(UpdateAddressDto updateAddressDto) {
        final var customer = dao.findById(updateAddressDto.getCustomerId()).orElse(null);
        if (customer == null) {
            return false;
        }
        customer.updateAddress(updateAddressDto.getStreet(),
            updateAddressDto.getZipCode(),
            updateAddressDto.getCity(),
            updateAddressDto.getCountry());
        dao.save(customer);
        return true;
    }

    private boolean isValidPerson(RegisterPersonDto personDto) {
        return emailMatches(personDto.getEmail()) &&
            nameMatches(personDto.getFirstName()) &&
            nameMatches(personDto.getLastName()) &&
            peselMatches(personDto.getPesel());
    }

    private boolean isValidCompany(RegisterCompanyDto companyDto) {
        return emailMatches(companyDto.getEmail()) &&
            nameMatches(companyDto.getName()) &&
            vatMatches(companyDto.getVat());
    }

    private boolean personExists(RegisterPersonDto personDto) {
        return dao.emailExists(personDto.getEmail()) || dao.peselExists(personDto.getPesel());
    }

    private boolean companyExists(RegisterCompanyDto companyDto) {
        return dao.emailExists(companyDto.getEmail()) || dao.vatExists(companyDto.getVat());
    }
}
