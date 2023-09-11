package com.olegstashkiv.booksstore.controller;

import com.olegstashkiv.booksstore.dto.book.BookDto;
import com.olegstashkiv.booksstore.dto.book.BookSearchParameters;
import com.olegstashkiv.booksstore.dto.book.CreateBookRequestDto;
import com.olegstashkiv.booksstore.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of books")
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Get book by id")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto create(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete book by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update book by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto update(@PathVariable Long id,
                          @RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.update(id, bookRequestDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Search for books by parameter",
            description = "Search for books by parameter")
    public List<BookDto> searchBooks(BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }
}
