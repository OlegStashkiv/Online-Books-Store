package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.BookDto;
import com.olegstashkiv.booksstore.dto.CreateBookRequestDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.BookMapper;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.repository.BookRepository;
import com.olegstashkiv.booksstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }
}
