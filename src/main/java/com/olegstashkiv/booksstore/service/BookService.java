package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.BookDto;
import com.olegstashkiv.booksstore.dto.BookSearchParameters;
import com.olegstashkiv.booksstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParameters searchParameters);
}
