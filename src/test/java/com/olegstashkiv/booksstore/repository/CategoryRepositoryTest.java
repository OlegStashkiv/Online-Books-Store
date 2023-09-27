package com.olegstashkiv.booksstore.repository;

import com.olegstashkiv.booksstore.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Sql(scripts = {
            "classpath:database/book/add-books-categories-to-table.sql"
    },executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/book/remove-books-categories-from-table.sql"
    },executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get a book by valid categories id")
    void getBooksByCategoryID_ExistingId_ReturnOneBook() {
        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setAuthor("Test Author 1");
        expectedBook.setTitle("Test Title 1");
        expectedBook.setIsbn("978-0262033848");
        expectedBook.setDescription("Test Description for Book 1");
        expectedBook.setPrice(BigDecimal.valueOf(500));
        expectedBook.setCoverImage("TestImage1.png");
        expectedBook.setCategories(Set.of(categoryRepository.findById(1L).get()));

        List<Book> actual = categoryRepository.getBooksByCategoriesId(1L);

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(expectedBook, actual.get(0));
    }
}
