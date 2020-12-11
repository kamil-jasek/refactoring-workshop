package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import pl.sda.refactoring.customers.event.Event;

final class NotificationPublisher {

    private final List<NotificationObserver<Event>> observers;

    NotificationPublisher() {
        this.observers = new ArrayList<>();
    }

    <T extends Event> void registerObserver(NotificationObserver<T> observer) {
        //noinspection unchecked
        observers.add((NotificationObserver<Event>) observer);
    }

    void publishEvent(Event event) {
        requireNonNull(event);
        observers.stream()
            .filter(observer -> observer.isSupported(event))
            .forEach(observer -> observer.handle(event));
    }
}
