package outboxsupport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseProcessedEventRepository<T extends ProcessedEvent>
        extends JpaRepository<T, UUID> {

    boolean existsByEventKey(String eventKey);
}

