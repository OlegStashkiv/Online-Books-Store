package com.olegstashkiv.booksstore;

import com.olegstashkiv.booksstore.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksStoreApplication {
    private final BookService bookService;

    @Autowired
    public BooksStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BooksStoreApplication.class, args);
    }
}
