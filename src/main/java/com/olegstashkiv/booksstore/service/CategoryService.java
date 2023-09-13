package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.olegstashkiv.booksstore.dto.category.CategoryDto;
import com.olegstashkiv.booksstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Set<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
