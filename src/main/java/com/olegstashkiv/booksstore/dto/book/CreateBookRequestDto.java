package com.olegstashkiv.booksstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Length(min = 1, max = 255)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Only Latin letters are allowed")
    private String title;
    @NotBlank
    @Length(min = 1, max = 255)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Only Latin letters are allowed")
    private String author;
    @NotNull
    @ISBN
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private String description;
    @URL
    private String coverImage;
}
