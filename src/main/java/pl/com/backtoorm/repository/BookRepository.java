package pl.com.backtoorm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.backtoorm.model.Book;

/**
 * Repository interface for managing Book entities.
 * It extends JpaRepository to provide CRUD operations.
 */
public interface BookRepository extends JpaRepository<Book, Long> {}
