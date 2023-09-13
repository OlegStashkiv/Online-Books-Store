package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.category.CategoryDto;
import com.olegstashkiv.booksstore.dto.category.CreateCategoryRequestDto;
import com.olegstashkiv.booksstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category toModel(CreateCategoryRequestDto requestDto);

    CategoryDto toDto(Category category);
}
