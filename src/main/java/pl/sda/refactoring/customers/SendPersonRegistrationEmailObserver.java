package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.customers.event.Event;
import pl.sda.refactoring.customers.event.PersonRegisteredEvent;
import pl.sda.refactoring.util.EmailSender;

final class SendPersonRegistrationEmailObserver implements NotificationObserver<PersonRegisteredEvent> {

    private final EmailSender emailSender;

    SendPersonRegistrationEmailObserver(EmailSender emailSender) {
        requireNonNull(emailSender);
        this.emailSender = emailSender;
    }

    @Override
    public boolean isSupported(Event event) {
        return event instanceof PersonRegisteredEvent;
    }

    @Override
    public void handle(PersonRegisteredEvent event) {
        if (event.isVerified()) {
            sendVerifiedPersonEmail(event);
        } else {
            sendNotVerifiedPersonEmail(event);
        }
    }

    private void sendVerifiedPersonEmail(PersonRegisteredEvent event) {
        final var subject = "Your are now verified customer!";
        final var body = "<b>Hi " + event.getFirstName() + "</b><br/>" +
            "Thank you for registering in our service. Now you are verified customer!";
        emailSender.sendEmail(event.getEmail(), subject, body);
    }

    private void sendNotVerifiedPersonEmail(PersonRegisteredEvent event) {
        final var subject = "Waiting for verification";
        final var body = "<b>Hi " + event.getFirstName() + "</b><br/>" +
            "We registered you in our service. Please wait for verification!";
        emailSender.sendEmail(event.getEmail(), subject, body);
    }
}
