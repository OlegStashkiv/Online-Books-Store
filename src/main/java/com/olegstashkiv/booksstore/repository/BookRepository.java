package com.olegstashkiv.booksstore.repository;

import com.olegstashkiv.booksstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
