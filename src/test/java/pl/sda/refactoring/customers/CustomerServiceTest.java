package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import pl.sda.refactoring.util.EmailSender;

@TestInstance(Lifecycle.PER_CLASS)
final class CustomerServiceTest {

    private final CustomerDao customerDao = mock(CustomerDao.class);
    private final EmailSender emailSender = mock(EmailSender.class);
    private final CustomerService customerService = new CustomerService();

    @BeforeAll
    void initService() {
        customerService.setDao(customerDao);
        customerService.setEmailSender(emailSender);
    }

    @Test
    void shouldNotRegisterPerson() {
        // when
        final var result = customerService.registerPerson(null, null, null, null, false);

        // then
        assertFalse(result);
    }
}
