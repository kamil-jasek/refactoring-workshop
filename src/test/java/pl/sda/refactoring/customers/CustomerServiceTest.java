package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

    @Test
    void shouldRegisterPerson() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        final var result = customerService
            .registerPerson("kamil@com.pl", "Kamil", "Jasek", "03039403944", false);

        // then
        assertTrue(result);

        // then
        assertTrue(result);
        verify(customerDao, times(1)).save(customerCaptor.capture());
        final var capturedCustomer = customerCaptor.getValue();
        assertNotNull(capturedCustomer.getId());
        assertNotNull(capturedCustomer.getCtime());
        assertEquals("kamil@com.pl", capturedCustomer.getEmail());
        assertEquals("Kamil", capturedCustomer.getfName());
        assertEquals("Jasek", capturedCustomer.getlName());
        assertFalse(capturedCustomer.isVerf());
        assertNull(capturedCustomer.getVerfTime());
        assertNull(capturedCustomer.getVerifBy());
        assertNull(capturedCustomer.getCompName());
        assertNull(capturedCustomer.getCompVat());
    }
}
