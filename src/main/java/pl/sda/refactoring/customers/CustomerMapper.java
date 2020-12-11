package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
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
}
