package pl.com.backtoorm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

/**
 * Represents a book entity with fields for id, name, author, and price. Includes validation
 * constraints for the fields.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SoftDelete
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
  @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq")
  private Long id;

  @NotEmpty(message = "Please provide a name")
  private String name;

  @NotEmpty(message = "Please provide a author")
  private String author;

  @NotNull(message = "Please provide a price")
  @DecimalMin("1.00")
  private BigDecimal price;
}
