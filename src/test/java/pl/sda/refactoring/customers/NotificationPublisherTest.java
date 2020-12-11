package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import pl.sda.refactoring.customers.event.EmptyEvent;

final class NotificationPublisherTest {

    @Test
    void shouldReceiveEvent() {
        // given
        final var notificationPublisher = new NotificationPublisher();
        final var noopObserver = new NoopObserver();
        notificationPublisher.registerObserver(noopObserver);

        // when
        notificationPublisher.publishEvent(new EmptyEvent());

        // then
        assertTrue(noopObserver.isReceived());
    }
}
