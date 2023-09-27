package com.olegstashkiv.booksstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.olegstashkiv.booksstore.dto.book.BookDto;
import com.olegstashkiv.booksstore.dto.book.CreateBookRequestDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.BookMapper;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.model.Category;
import com.olegstashkiv.booksstore.repository.book.BookRepository;
import com.olegstashkiv.booksstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static Book book;
    private static CreateBookRequestDto createBookRequest;
    private static BookDto bookDto;
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(10L);
        category.setName("Test Category");
        category.setDescription("Test Category Description");

        book = new Book();
        book.setId(1L);
        book.setAuthor("Test Author 1");
        book.setTitle("Test Title 1");
        book.setIsbn("978-0262033848");
        book.setDescription("Test Description for Book 1");
        book.setPrice(BigDecimal.valueOf(500));
        book.setCoverImage("TestImage1.png");
        book.setCategories(Set.of(category));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("Test Author 1");
        bookDto.setTitle("Test Title 1");
        bookDto.setIsbn("978-0262033848");
        bookDto.setPrice(BigDecimal.valueOf(500));
        bookDto.setDescription("Test Description for Book 1");
        bookDto.setCoverImage("TestImage1.png");
        bookDto.setCategories(Set.of(10L));

        createBookRequest = new CreateBookRequestDto();
        createBookRequest.setAuthor("Test Author 1");
        createBookRequest.setTitle("Test Title 1");
        createBookRequest.setIsbn("978-0262033848");
        createBookRequest.setPrice(BigDecimal.valueOf(500));
        createBookRequest.setDescription("Test Description for Book 1");
        createBookRequest.setCoverImage("TestImage1.png");
        createBookRequest.setCategoryIds(Set.of(10L));

    }

    @Test
    @DisplayName("Test save new book method")
    public void saveBook_ValidData_ReturnSavedBook() {
        when(bookMapper.toModel(createBookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.save(createBookRequest);
        BookDto expected = bookDto;

        assertEquals(expected, actual);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Test find all books method")
    public void findAll_IncludePagination_ReturnListBook() {
        Pageable pageable = Pageable.ofSize(10);
        when(bookRepository.findAllWithCategories(pageable)).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> actual = bookService.findAll(pageable);
        List<BookDto> expected = List.of(bookDto);

        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findAllWithCategories(pageable);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Test get book by id with valid request")
    public void findById_WithValidId_ReturnsBook() {
        when(bookRepository.findByIdWithCategories(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto expected = bookDto;
        BookDto actual = bookService.findById(1L);

        assertEquals(expected, actual);
        verify(bookMapper).toDto(any(Book.class));
        verify(bookRepository).findByIdWithCategories(anyLong());
    }

    @Test
    @DisplayName("Test get book by id with invalid request")
    public void findById_WitValidId_ThrowException() {
        Long invalidUserId = 200L;
        when(bookRepository.findByIdWithCategories(invalidUserId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(200L)
        );

        String expected = "Can`t find book by id: " + invalidUserId;
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test update book method")
    public void update_ValidRequest_ReturnUpdatedBook() {
        when(bookMapper.toModel(createBookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.update(1L, createBookRequest);
        BookDto expected = bookDto;

        assertEquals(expected, actual);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

}
