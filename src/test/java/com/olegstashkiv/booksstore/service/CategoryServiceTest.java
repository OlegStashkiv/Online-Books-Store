package com.olegstashkiv.booksstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.olegstashkiv.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.olegstashkiv.booksstore.dto.category.CategoryDto;
import com.olegstashkiv.booksstore.dto.category.CreateCategoryRequestDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.BookMapper;
import com.olegstashkiv.booksstore.mapper.CategoryMapper;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.model.Category;
import com.olegstashkiv.booksstore.repository.CategoryRepository;
import com.olegstashkiv.booksstore.service.impl.CategoryServiceImpl;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private Category category;
    private CategoryDto categoryDto;
    private CreateCategoryRequestDto categoryRequestDto;
    private Book book;
    private BookDtoWithoutCategoryIds bookDto;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(10L);
        category.setName("Test Category");
        category.setDescription("Test Category Description");

        categoryDto = new CategoryDto();
        categoryDto.setId(10L);
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Category Description");

        categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName("Test Category");
        categoryRequestDto.setDescription("Test Category Description");

        book = new Book();
        book.setId(1L);
        book.setAuthor("Test Author 1");
        book.setTitle("Test Title 1");
        book.setIsbn("978-0262033848");
        book.setDescription("Test Description for Book 1");
        book.setPrice(BigDecimal.valueOf(500));
        book.setCoverImage("TestImage1.png");
        book.setCategories(Set.of(category));

        bookDto = new BookDtoWithoutCategoryIds();
        bookDto.setId(1L);
        bookDto.setAuthor("Test Author 1");
        bookDto.setTitle("Test Title 1");
        bookDto.setIsbn("978-0262033848");
        bookDto.setPrice(BigDecimal.valueOf(500));
        bookDto.setDescription("Test Description for Book 1");
        bookDto.setCoverImage("TestImage1.png");
    }

    @Test
    @DisplayName("Test find all category method")
    public void findAll_ValidRequest_ListCategories() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Category> categoryPage = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        Set<CategoryDto> actual = categoryService.findAll(pageable);
        Set<CategoryDto> expected = Set.of(categoryDto);

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Test get category by id method with valid id")
    public void getById_ValidId_ReturnCategory() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getById(category.getId());
        CategoryDto expected = categoryDto;

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(category.getId());
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Test get category by id method with invalid id")
    public void getById_InvalidId_ThrowException() {
        Long invalidCategoryId = -100L;
        when(categoryRepository.findById(invalidCategoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(invalidCategoryId)
        );

        String expected = "Can`t find category by id: " + invalidCategoryId;
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test save new category method")
    public void save_ValidRequest_ReturnSavedCategory() {
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.save(categoryRequestDto);

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Test update category method")
    public void update_ValidRequest_ReturnUpdatedCategory() {
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto expected = categoryDto;
        CategoryDto actual = categoryService.update(category.getId(), categoryRequestDto);

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Test get books by category id method")
    public void getBooksByCategoryId_ValidId_ReturnListBooks() {
        when(categoryRepository.getBooksByCategoriesId(category.getId())).thenReturn(List.of(book));
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(bookDto);

        List<BookDtoWithoutCategoryIds> expected = List.of(bookDto);
        List<BookDtoWithoutCategoryIds> actual = categoryService
                .getBooksByCategoryId(category.getId());

        assertEquals(expected, actual);
        verify(categoryRepository, times(1))
                .getBooksByCategoriesId(category.getId());
        verifyNoMoreInteractions(categoryRepository, bookMapper);
    }
}
