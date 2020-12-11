package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.sda.refactoring.customers.event.PersonRegisteredEvent;
import pl.sda.refactoring.util.EmailSender;

final class SendPersonRegistrationEmailObserverTest {

    public EmailSender emailSender;
    public SendPersonRegistrationEmailObserver observer;

    @BeforeEach
    void init() {
        emailSender = mock(EmailSender.class);
        observer = new SendPersonRegistrationEmailObserver(emailSender);
    }

    @Test
    void shouldSendVerifiedEmail() {
        // given
        final var event = new PersonRegisteredEvent(UUID.randomUUID(), "Kmail", "jasek", "k@test.com", true);
        final var emailCapture = ArgumentCaptor.forClass(String.class);
        final var subjectCapture = ArgumentCaptor.forClass(String.class);
        final var bodyCapture = ArgumentCaptor.forClass(String.class);

        // when
        observer.handle(event);

        // then
        verify(emailSender).sendEmail(emailCapture.capture(), subjectCapture.capture(), bodyCapture.capture());
        assertEquals("k@test.com", emailCapture.getValue());
        assertEquals(
            "Your are now verified customer!",
            subjectCapture.getValue());
        assertEquals(
            "<b>Hi Kmail</b><br/>Thank you for registering in our service. Now you are verified customer!",
            bodyCapture.getValue());
    }
}