package outboxsupport;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class ProcessedEvent {

    @Column(name = "event_key", nullable = false, unique = true, updatable = false)
    private String eventKey;

    @Column(name = "processed_at", nullable = false)
    private Instant processedAt;
}
