package pl.sda.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.sda.refactoring.customers.Customer;

final class EqualsHashcodeTest {

    @Test
    void testEquals() {
        // given
        final var customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setEmail("kamil@test.com");

        final var customers = new HashSet<>();

        // when
        customers.add(customer);
        customer.setCtime(LocalDateTime.now());

        // then
        assertTrue(customers.contains(customer));
    }
}
