package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.BookDto;
import com.olegstashkiv.booksstore.dto.BookSearchParameters;
import com.olegstashkiv.booksstore.dto.CreateBookRequestDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.BookMapper;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.repository.book.BookRepository;
import com.olegstashkiv.booksstore.repository.book.BookSpecificationBuilder;
import com.olegstashkiv.booksstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
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

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = specificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
