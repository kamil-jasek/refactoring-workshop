package pl.sda.refactoring.customers;

import java.util.Optional;
import java.util.UUID;

class CustomerDao {

    public void save(Customer customer) {
        throw new UnsupportedOperationException();
    }

    public boolean emailExists(String email) {
        throw new UnsupportedOperationException();
    }

    public boolean peselExists(String pesel) {
        throw new UnsupportedOperationException();
    }

    public boolean vatExists(String vat) {
        throw new UnsupportedOperationException();
    }

    public Optional<Customer> findById(UUID cid) {
        throw new UnsupportedOperationException();
    }
}
