package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.CreateCartItemRequestDto;
import com.olegstashkiv.booksstore.dto.shopingcart.ShoppingCartDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.model.ShoppingCart;
import com.olegstashkiv.booksstore.model.User;
import com.olegstashkiv.booksstore.repository.shopingcart.ShoppingCartRepository;
import com.olegstashkiv.booksstore.service.CartItemService;
import com.olegstashkiv.booksstore.service.ShoppingCartService;
import com.olegstashkiv.booksstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final UserService userService;

    @Override
    public CartItemDto save(CreateCartItemRequestDto cartDto) {
        return cartItemService.save(cartDto);
    }

    @Override
    @Transactional
    public ShoppingCartDto getShoppingCart(Pageable pageable) {
        User authenticatedUser = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find a shopping cart with id"
                        + authenticatedUser.getId()));
        Long id = shoppingCart.getId();
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(id);
        shoppingCartDto.setUserId(authenticatedUser.getId());
        shoppingCartDto.setCartItems(cartItemService.findCartItemsByShoppingCartId(id));
        return shoppingCartDto;
    }
}
