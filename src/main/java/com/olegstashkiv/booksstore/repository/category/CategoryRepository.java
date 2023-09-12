package com.olegstashkiv.booksstore.repository.category;

import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :id")
    List<Book> getBooksByCategoriesId(Long id);
}
