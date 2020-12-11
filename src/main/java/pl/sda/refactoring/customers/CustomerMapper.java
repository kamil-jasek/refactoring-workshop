package pl.sda.refactoring.customers;

import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;

final class CustomerMapper {

    Person newPerson(RegisterPersonDto personDto) {
        return new Person(personDto.getEmail(), personDto.getFirstName(), personDto.getLastName(), personDto.getPesel());
    }

    Customer newCompany(RegisterCompanyDto companyDto) {
        final var company = new Customer();
        return company.setUpCompany(companyDto);
    }

}
