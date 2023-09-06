package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.BookDto;
import com.olegstashkiv.booksstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
