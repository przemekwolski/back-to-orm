package pl.com.backtoorm.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class JpaConfig {

  @PersistenceContext private final EntityManager entityManager;
}
