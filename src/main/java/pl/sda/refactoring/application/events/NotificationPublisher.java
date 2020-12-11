package pl.sda.refactoring.application.events;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import pl.sda.refactoring.customers.event.Event;

public final class NotificationPublisher {

    private final List<NotificationObserver<Event>> observers;

    public NotificationPublisher() {
        this.observers = new ArrayList<>();
    }

    public <T extends Event> void registerObserver(NotificationObserver<T> observer) {
        //noinspection unchecked
        observers.add((NotificationObserver<Event>) observer);
    }

    public void publishEvent(Event event) {
        requireNonNull(event);
        observers.stream()
            .filter(observer -> observer.isSupported(event))
            .forEach(observer -> observer.handle(event));
    }
}
