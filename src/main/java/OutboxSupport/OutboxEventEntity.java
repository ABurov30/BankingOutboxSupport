package OutboxSupport;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@MappedSuperclass
public abstract class OutboxEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "aggregate_type", nullable = false)
  private String aggregateType;

  @Column(name = "aggregate_id", nullable = false)
  private UUID aggregateId;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> payload;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OutboxEventStatus outboxEventStatus = OutboxEventStatus.PENDING;

  @Column(name = "retry_count", nullable = false)
  private int retryCount = 0;

  @Column(name = "error_message")
  private String errorMessage;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  @Column(name = "topic", nullable = false)
  private String topic;

  @Column(name = "event_key", nullable = false, unique = true)
  private String eventKey;

  @Column(name = "schema_version", nullable = false)
  private String schemaVersion;

  @Column(name = "next_retry_at")
  private LocalDateTime nextRetryAt;

  @Column(name = "locked_at")
  private LocalDateTime lockedAt;

  @Column(name = "locked_by")
  private String lockedBy;

  @Column(name = "correlation_id")
  private UUID correlationId;
}
