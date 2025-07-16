package pl.com.backtoorm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.com.backtoorm.config.SecurityConfig;
import pl.com.backtoorm.model.Book;
import pl.com.backtoorm.repository.BookRepository;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
@MockitoBean(types = {BookRepository.class})
class BookControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private BookRepository repository;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @WithMockUser
  void getBooks_authenticated_returnsOk() throws Exception {
    Book book1 = new Book(null, "Test Book", "Author", BigDecimal.TEN);
    Book book2 = new Book(1L, "Test Book", "Author", BigDecimal.TEN);
    when(repository.findAll()).thenReturn(List.of(book1, book2));

    mockMvc
        .perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(List.of(book1, book2))));
  }

  @Test
  void getBooks_unauthenticated_returnsUnauthorized() throws Exception {
    mockMvc.perform(get("/books")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void postBook_authenticated_returnsCreated() throws Exception {
    Book book = new Book(null, "Test Book", "Author", BigDecimal.TEN);
    Book savedBook = new Book(1L, "Test Book", "Author", BigDecimal.TEN);
    when(repository.save(any(Book.class))).thenReturn(savedBook);

    mockMvc
        .perform(
            post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Test Book"));
  }

  @Test
  @WithMockUser
  void getBookById_authenticated_returnsBook() throws Exception {
    Book book = new Book(1L, "Test Book", "Author", BigDecimal.TEN);
    when(repository.findById(1L)).thenReturn(Optional.of(book));

    mockMvc
        .perform(get("/books/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Test Book"));
  }

  @Test
  @WithMockUser
  void putBook_authenticated_updatesBook() throws Exception {
    Book existing = new Book(1L, "Old Name", "Old Author", BigDecimal.valueOf(5));
    Book updated = new Book(1L, "New Name", "New Author", BigDecimal.valueOf(15));
    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(repository.save(any(Book.class))).thenReturn(updated);

    mockMvc
        .perform(
            put("/books/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updated)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("New Name"))
        .andExpect(jsonPath("$.author").value("New Author"))
        .andExpect(jsonPath("$.price").value(15.0));
  }

  @Test
  @WithMockUser
  void patchBook_authenticated_updatesAuthor() throws Exception {
    Book existing = new Book(1L, "Book Name", "Old Author", BigDecimal.TEN);
    Book updated = new Book(1L, "Book Name", "New Author", BigDecimal.TEN);
    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(repository.save(any(Book.class))).thenReturn(updated);

    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/books/1")
                .contentType("application/json")
                .content("{\"author\":\"New Author\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.author").value("New Author"));
  }

  @Test
  @WithMockUser
  void deleteBook_authenticated_deletesBook() throws Exception {
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/books/1"))
        .andExpect(status().isOk());
  }
}
