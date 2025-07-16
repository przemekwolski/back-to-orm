package pl.com.backtoorm.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.backtoorm.exception.BookNotFoundException;
import pl.com.backtoorm.exception.BookUnSupportedFieldPatchException;
import pl.com.backtoorm.model.Book;
import pl.com.backtoorm.repository.BookRepository;

@RestController
@Validated
@RequiredArgsConstructor
public class BookController {

  private final BookRepository repository;

  @GetMapping("/books")
  List<Book> findAll() {
    return repository.findAll();
  }

  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  Book newBook(@Valid @RequestBody Book newBook) {
    return repository.save(newBook);
  }

  @GetMapping("/books/{id}")
  Book findOne(@PathVariable @Min(1) Long id) {
    return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
  }

  @PutMapping("/books/{id}")
  Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

    return repository
        .findById(id)
        .map(
            x -> {
              x.setName(newBook.getName());
              x.setAuthor(newBook.getAuthor());
              x.setPrice(newBook.getPrice());
              return repository.save(x);
            })
        .orElseGet(
            () -> {
              newBook.setId(id);
              return repository.save(newBook);
            });
  }

  @PatchMapping("/books/{id}")
  Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

    return repository
        .findById(id)
        .map(
            x -> {
              String author = update.get("author");
              if (!ObjectUtils.isEmpty(author)) {
                x.setAuthor(author);

                // better create a custom method to update a value = :newValue where id = :id
                return repository.save(x);
              } else {
                throw new BookUnSupportedFieldPatchException(update.keySet());
              }
            })
        .orElseThrow(() -> new BookNotFoundException(id));
  }

  @DeleteMapping("/books/{id}")
  void deleteBook(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
