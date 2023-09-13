package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.CreateCartItemRequestDto;
import com.olegstashkiv.booksstore.dto.shopingcart.ShoppingCartDto;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    CartItemDto save(CreateCartItemRequestDto requestDto);

    ShoppingCartDto getShoppingCart(Pageable pageable);
}
