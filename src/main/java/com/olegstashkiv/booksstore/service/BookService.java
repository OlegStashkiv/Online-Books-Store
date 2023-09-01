package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
