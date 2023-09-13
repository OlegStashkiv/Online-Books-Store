package com.olegstashkiv.booksstore.dto.shopingcart;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
