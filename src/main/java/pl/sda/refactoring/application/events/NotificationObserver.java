package pl.sda.refactoring.application.events;

import pl.sda.refactoring.customers.event.Event;

public interface NotificationObserver<T extends Event> {

    boolean isSupported(Event event);
    void handle(T event);
}
