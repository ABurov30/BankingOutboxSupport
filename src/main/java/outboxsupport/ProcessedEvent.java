package outboxsupport;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class ProcessedEvent {

  @Column(name = "event_key", nullable = false, unique = true, updatable = false)
  private String eventKey;

  @Column(name = "processed_at", nullable = false)
  private Instant processedAt;
}
