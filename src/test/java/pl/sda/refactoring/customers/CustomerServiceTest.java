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
import pl.sda.refactoring.customers.dto.RegisterCompanyDto;
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
        customerService = new CustomerService(customerDao, setUpNotificationPublisher(), new CustomerMapper());
    }

    private NotificationPublisher setUpNotificationPublisher() {
        final var notificationPublisher = new NotificationPublisher();
        final var sendCompanyRegistrationEmailObserver = new SendCompanyRegistrationEmailObserver(emailSender);
        final var sendPersonRegistrationEmailObserver = new SendPersonRegistrationEmailObserver(emailSender);
        notificationPublisher.registerObserver(sendCompanyRegistrationEmailObserver);
        notificationPublisher.registerObserver(sendPersonRegistrationEmailObserver);
        return notificationPublisher;
    }

    @Test
    void shouldRegisterNotVerifiedPerson() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Person.class);

        // when
        final var result = customerService
            .registerPerson(new RegisterPersonDto("kamil@com.pl", "Kamil", "Jasek", "03039403944", false));

        // then
        assertTrue(result);
        verify(customerDao, times(1)).save(customerCaptor.capture());
        final var capturedCustomer = customerCaptor.getValue();
        assertNotNull(capturedCustomer.getId());
        assertNotNull(capturedCustomer.getCreateTime());
        assertEquals("kamil@com.pl", capturedCustomer.getEmail());
        assertEquals("Kamil", capturedCustomer.getFirstName());
        assertEquals("Jasek", capturedCustomer.getLastName());
        assertFalse(capturedCustomer.isVerified());
        assertNull(capturedCustomer.getVerificationTime());
        assertNull(capturedCustomer.getVerifiedBy());
        assertNull(capturedCustomer.getCompanyName());
        assertNull(capturedCustomer.getCompanyVat());
    }

    @Test
    void shouldRegisterVerifiedPerson() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Person.class);

        // when
        final var result = customerService
            .registerPerson(new RegisterPersonDto("kamil@com.pl", "Kamil", "Jasek", "03039403944", true));

        // then
        assertTrue(result);
        verify(customerDao, times(1)).save(customerCaptor.capture());
        final var capturedCustomer = customerCaptor.getValue();
        assertNotNull(capturedCustomer.getId());
        assertNotNull(capturedCustomer.getCreateTime());
        assertEquals("kamil@com.pl", capturedCustomer.getEmail());
        assertEquals("Kamil", capturedCustomer.getFirstName());
        assertEquals("Jasek", capturedCustomer.getLastName());
        assertTrue(capturedCustomer.isVerified());
        assertNotNull(capturedCustomer.getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, capturedCustomer.getVerifiedBy());
        assertNull(capturedCustomer.getCompanyName());
        assertNull(capturedCustomer.getCompanyVat());
    }

    @Test
    void shouldRegisterVerifiedCompany() {
        // given
        given(customerDao.emailExists(anyString())).willReturn(false);
        given(customerDao.peselExists(anyString())).willReturn(false);
        var customerCaptor = ArgumentCaptor.forClass(Customer.class);

        // when
        final var result = customerService
            .registerCompany(new RegisterCompanyDto("kamil@com.pl", "TEST S.A.", "8382773833", true));

        // then
        assertTrue(result);
        verify(customerDao, times(1)).save(customerCaptor.capture());
        final var capturedCustomer = customerCaptor.getValue();
        assertNotNull(capturedCustomer.getId());
        assertNotNull(capturedCustomer.getCreateTime());
        assertEquals("kamil@com.pl", capturedCustomer.getEmail());
        assertEquals("TEST S.A.", capturedCustomer.getCompanyName());
        assertEquals("8382773833", capturedCustomer.getCompanyVat());
        assertTrue(capturedCustomer.isVerified());
        assertNotNull(capturedCustomer.getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, capturedCustomer.getVerifiedBy());
    }
}
