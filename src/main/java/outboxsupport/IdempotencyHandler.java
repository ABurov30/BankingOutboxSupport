package outboxsupport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdempotencyHandler {

    default <T extends ProcessedEvent> boolean isAlreadyProcessed(
            String eventKey,
            BaseProcessedEventRepository<T> repository
    ) {
        return repository.existsByEventKey(eventKey);
    }

    default <T extends ProcessedEvent> void markAsProcessed(
            T processedEvent,
            BaseProcessedEventRepository<T> repository
    ) {
        repository.save(processedEvent);
    }
}
