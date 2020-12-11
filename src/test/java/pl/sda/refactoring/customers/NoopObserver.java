package pl.sda.refactoring.customers;

import pl.sda.refactoring.application.events.NotificationObserver;
import pl.sda.refactoring.customers.event.EmptyEvent;
import pl.sda.refactoring.customers.event.Event;

final class NoopObserver implements NotificationObserver<EmptyEvent> {

    private boolean received;

    @Override
    public boolean isSupported(Event event) {
        return event instanceof EmptyEvent;
    }

    @Override
    public void handle(EmptyEvent event) {
        received = true;
    }

    public boolean isReceived() {
        return received;
    }
}
