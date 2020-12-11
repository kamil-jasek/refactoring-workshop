package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.UUID;
import pl.sda.refactoring.customers.dto.CustomerDto;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;

public final class CustomerFacade {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = requireNonNull(customerService);
    }

    public boolean registerPerson(RegisterPersonDto personDto) {
        return this.customerService.registerPerson(personDto);
    }

    public CustomerDto findById(UUID cid) {
        return null;
    }
}
