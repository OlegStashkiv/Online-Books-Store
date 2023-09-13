package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.book.BookDto;
import com.olegstashkiv.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.olegstashkiv.booksstore.dto.book.CreateBookRequestDto;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categoryIds", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    default Set<Long> mapToLongSet(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

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
