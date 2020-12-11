package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.customers.event.CompanyRegisteredEvent;
import pl.sda.refactoring.customers.event.Event;
import pl.sda.refactoring.util.EmailSender;

final class SendCompanyRegistrationEmailObserver implements NotificationObserver<CompanyRegisteredEvent> {

    private final EmailSender emailSender;

    SendCompanyRegistrationEmailObserver(EmailSender emailSender) {
        requireNonNull(emailSender);
        this.emailSender = emailSender;
    }

    @Override
    public boolean isSupported(Event event) {
        return event instanceof CompanyRegisteredEvent;
    }

    @Override
    public void handle(CompanyRegisteredEvent event) {
        if (event.isVerified()) {
            sendVerifiedEmail(event);
        } else {
            sendNotVerifiedEmail(event);
        }
    }

    private void sendVerifiedEmail(CompanyRegisteredEvent event) {
        final var subject = "Your are now verified customer!";
        final var body = "<b>Your company: " + event.getName() + " is ready to make na order.</b><br/>" +
            "Thank you for registering in our service. Now you are verified customer!";
        emailSender.sendEmail(event.getEmail(), subject, body);
    }

    private void sendNotVerifiedEmail(CompanyRegisteredEvent event) {
        final var subject = "Waiting for verification";
        final var body = "<b>Hello</b><br/>" +
            "We registered your company: " + event.getName() + " in our service. Please wait for verification!";
        emailSender.sendEmail(event.getEmail(), subject, body);
    }
}
