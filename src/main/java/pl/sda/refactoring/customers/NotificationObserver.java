package pl.sda.refactoring.customers;

import pl.sda.refactoring.customers.event.Event;

public interface NotificationObserver<T extends Event> {

    boolean isSupported(Event event);
    void handle(T event);
}
