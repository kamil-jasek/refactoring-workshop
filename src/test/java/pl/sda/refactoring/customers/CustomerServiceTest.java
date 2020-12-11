package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.sda.refactoring.customers.dto.RegisterPersonDto;
import pl.sda.refactoring.util.EmailSender;

final class CustomerServiceTest {

    private CustomerDao customerDao;
    private EmailSender emailSender;
    private CustomerService customerService;

    @BeforeEach
    void initService() {
        customerDao = mock(CustomerDao.class);
        emailSender = mock(EmailSender.class);
        customerService = new CustomerService(customerDao, emailSender, new CustomerMapper());
    }

    @Test
    void shouldRegisterNotVerifiedPerson() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        final var result = customerService
            .registerPerson(new RegisterPersonDto("kamil@com.pl", "Kamil", "Jasek", "03039403944", false));

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

    @Test
    void shouldRegisterVerifiedPerson() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        final var result = customerService
            .registerPerson(new RegisterPersonDto("kamil@com.pl", "Kamil", "Jasek", "03039403944", true));

        // then
        assertTrue(result);
        verify(customerDao, times(1)).save(customerCaptor.capture());
        final var capturedCustomer = customerCaptor.getValue();
        assertNotNull(capturedCustomer.getId());
        assertNotNull(capturedCustomer.getCtime());
        assertEquals("kamil@com.pl", capturedCustomer.getEmail());
        assertEquals("Kamil", capturedCustomer.getfName());
        assertEquals("Jasek", capturedCustomer.getlName());
        assertTrue(capturedCustomer.isVerf());
        assertNotNull(capturedCustomer.getVerfTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, capturedCustomer.getVerifBy());
        assertNull(capturedCustomer.getCompName());
        assertNull(capturedCustomer.getCompVat());
    }
}
