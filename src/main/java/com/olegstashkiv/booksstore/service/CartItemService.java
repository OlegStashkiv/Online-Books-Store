package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.CreateCartItemRequestDto;
import com.olegstashkiv.booksstore.dto.cartitem.UpdateCartItemDto;
import java.util.List;

public interface CartItemService {
    CartItemDto save(CreateCartItemRequestDto requestDto);

    List<CartItemDto> findCartItemsByShoppingCartId(Long id);

    CartItemDto update(UpdateCartItemDto updateCartItemDto, Long id);

    public void delete(Long id);
}
