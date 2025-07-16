package pl.com.backtoorm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.backtoorm.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
