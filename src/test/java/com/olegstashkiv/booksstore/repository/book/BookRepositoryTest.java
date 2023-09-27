package com.olegstashkiv.booksstore.repository.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.repository.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Book expectedBook1;
    private Book expectedBook2;

    @BeforeEach
    void setUp() {
        expectedBook1 = new Book();
        expectedBook1.setId(1L);
        expectedBook1.setAuthor("Test Author 1");
        expectedBook1.setTitle("Test Title 1");
        expectedBook1.setIsbn("978-0262033848");
        expectedBook1.setDescription("Test Description for Book 1");
        expectedBook1.setPrice(BigDecimal.valueOf(500));
        expectedBook1.setCoverImage("TestImage1.png");
        expectedBook1.setCategories(Set.of(categoryRepository.findById(1L).get()));

        expectedBook2 = new Book();
        expectedBook2.setId(1L);
        expectedBook2.setAuthor("Test Author 2");
        expectedBook2.setTitle("Test Title 2");
        expectedBook2.setIsbn("978-0-9767736-6-5");
        expectedBook2.setDescription("Test Description for Book 2");
        expectedBook2.setCoverImage("TestImage2.png");
        expectedBook2.setPrice(BigDecimal.valueOf(300));
        expectedBook2.setCategories(Set.of(categoryRepository.findById(2L).get()));
    }

    @Test
    @Sql(scripts = {
            "classpath:database/book/add-books-categories-to-table.sql"
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/book/remove-books-categories-from-table.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find a book by id with categories with a valid id")
    void findBooksById_ExistingId_ReturnOneBook() {
        Book actual = bookRepository.findByIdWithCategories(1L).get();

        assertEquals(expectedBook1, actual);
    }

    @Test
    @Sql(scripts = {
            "classpath:database/book/add-books-categories-to-table.sql"
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/book/remove-books-categories-from-table.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find a book by id with categories with an invalid id")
    void findBooksById_NotValidId_ReturnException() {
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class, () ->
                bookRepository.findByIdWithCategories(10L).orElseThrow(
                    () -> new EntityNotFoundException("Can`t find book by id: 10")));

        String expected = "Can`t find book by id: 10";
        assertEquals(expected, actual.getMessage());
    }

    @Test
    @Sql(scripts = {
            "classpath:database/book/add-books-categories-to-table.sql"
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/book/remove-books-categories-from-table.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find a book by id with categories with an invalid id")
    void findAllBooks_ValidRequest_ListOfBooks() {
        Pageable pageable = Pageable.ofSize(10);
        List<Book> actual = bookRepository.findAllWithCategories(pageable);

        List<Book> expected = List.of(expectedBook1, expectedBook2);

        assertEquals(expected, actual);
    }
}
