package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.CreateCartItemRequestDto;
import com.olegstashkiv.booksstore.dto.cartitem.UpdateCartItemDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.CartItemMapper;
import com.olegstashkiv.booksstore.model.CartItem;
import com.olegstashkiv.booksstore.model.ShoppingCart;
import com.olegstashkiv.booksstore.repository.book.BookRepository;
import com.olegstashkiv.booksstore.repository.cartitem.CartItemRepository;
import com.olegstashkiv.booksstore.repository.shopingcart.ShoppingCartRepository;
import com.olegstashkiv.booksstore.service.CartItemService;
import com.olegstashkiv.booksstore.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CartItemDto save(CreateCartItemRequestDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookRepository.findById(requestDto.getBookId()).get());
        cartItem.setQuantity(requestDto.getQuantity());
        Long userId = userService.getAuthenticatedUser().getId();
        Long shoppingCardId = shoppingCartRepository.findByUserId(userId).orElseThrow().getId();
        setShoppingCartAndCartItems(shoppingCardId, cartItem);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public List<CartItemDto> findCartItemsByShoppingCartId(Long id) {
        return cartItemRepository.findCartItemsByShoppingCartId(id).stream()
                .map(cartItemMapper::toDto)
                .toList();
    }

    @Override
    public CartItemDto update(UpdateCartItemDto updateCartItemDto, Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a cart item with id " + id)
        );
        cartItem.setQuantity(updateCartItemDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

    private void setShoppingCartAndCartItems(Long id, CartItem cartItem) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find shopping cart by id: " + id));
        cartItem.setShoppingCart(shoppingCart);
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(cartItem);
        if (shoppingCart.getCartItems().isEmpty()) {
            shoppingCart.setCartItems(cartItems);
        } else {
            shoppingCart.getCartItems().add(cartItem);
        }
    }
}
