package com.olegstashkiv.booksstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @Length(min = 3, max = 255)
    private String name;

    @NotNull
    private String description;
}
