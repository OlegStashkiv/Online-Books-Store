package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.book.BookDto;
import com.olegstashkiv.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.olegstashkiv.booksstore.dto.book.CreateBookRequestDto;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categories", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default Set<Long> mapToLongSet(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }


    @AfterMapping
    default Set<Category> mapToCategorySet(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
    }
}
