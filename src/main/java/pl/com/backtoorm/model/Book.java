package pl.com.backtoorm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
public class Book {

  @Id @GeneratedValue private Long id;

  @NotEmpty(message = "Please provide a name")
  private String name;

  @NotEmpty(message = "Please provide a author")
  private String author;

  @NotNull(message = "Please provide a price")
  @DecimalMin("1.00")
  private BigDecimal price;
}
