package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;

final class CustomerMapper {

    Customer newPerson(RegisterPersonDto personDto) {
        var customer = new Customer();
        customer.setType(Customer.PERSON);
        customer.setEmail(personDto.getEmail());
        customer.setfName(personDto.getFirstName());
        customer.setlName(personDto.getLastName());
        customer.setPesel(personDto.getPesel());
        customer.setCtime(LocalDateTime.now());
        return customer;
    }

    Customer newCompany(RegisterCompanyDto companyDto) {
        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        customer.setEmail(companyDto.getEmail());
        customer.setCompName(companyDto.getName());
        customer.setCompVat(companyDto.getVat());
        customer.setCtime(LocalDateTime.now());
        return customer;
    }
}
