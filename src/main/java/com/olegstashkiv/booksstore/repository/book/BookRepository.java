package com.olegstashkiv.booksstore.repository.book;

import com.olegstashkiv.booksstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book b INNER JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findByIdWithCategories(Long id);

    @Query("SELECT DISTINCT b FROM Book b INNER JOIN FETCH b.categories")
    List<Book> findAllWithCategories(Pageable pageable);
}
