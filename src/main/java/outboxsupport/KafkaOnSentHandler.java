package outboxsupport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public interface KafkaOnSentHandler {
    default <E extends OutboxEventEntity> void onPublish(UUID eventId, JpaRepository<E, UUID> repository) {
        E event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setOutboxEventStatus(OutboxEventStatus.PUBLISHED);
        event.setSentAt(LocalDateTime.now());
        event.setRetryCount(event.getRetryCount() + 1);
        repository.save(event);
    }

    default <E extends OutboxEventEntity> void onFailed(UUID eventId, Throwable e, JpaRepository<E, UUID> repository) {
        E event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        int retryCount = event.getRetryCount() + 1;
        event.setErrorMessage(e.getMessage());
        event.setNextRetryAt(LocalDateTime.now().plus(Duration.ofMillis(5000)));
        event.setRetryCount(retryCount);
        if (retryCount >= 5) {
            event.setOutboxEventStatus(OutboxEventStatus.FAILED);
        } else {
            event.setOutboxEventStatus(OutboxEventStatus.PENDING);
        }

        repository.save(event);
    }
}
