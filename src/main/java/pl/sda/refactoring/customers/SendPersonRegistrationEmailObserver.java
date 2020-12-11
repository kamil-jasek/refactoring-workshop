package pl.sda.refactoring.customers;

import pl.sda.refactoring.customers.event.Event;
import pl.sda.refactoring.customers.event.PersonRegisteredEvent;

final class SendPersonRegistrationEmailObserver implements NotificationObserver<PersonRegisteredEvent> {

    @Override
    public boolean isSupported(Event event) {
        return event instanceof PersonRegisteredEvent;
    }

    @Override
    public void handle(PersonRegisteredEvent event) {

    }
}
