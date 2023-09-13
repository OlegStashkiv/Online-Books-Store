package com.olegstashkiv.booksstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull
    @Min(1)
    private int quantity;
    @NotNull
    private Long bookId;
}
