package com.olegstashkiv.booksstore.dto.cartitem;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String title;
    private Integer quantity;
}
