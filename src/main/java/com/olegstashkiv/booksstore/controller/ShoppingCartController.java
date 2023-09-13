package com.olegstashkiv.booksstore.controller;

import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.CreateCartItemRequestDto;
import com.olegstashkiv.booksstore.dto.cartitem.UpdateCartItemDto;
import com.olegstashkiv.booksstore.dto.shopingcart.ShoppingCartDto;
import com.olegstashkiv.booksstore.service.CartItemService;
import com.olegstashkiv.booksstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "Create a new shopping cart")
    public CartItemDto addCartItem(@RequestBody @Valid CreateCartItemRequestDto requestDto) {
        return shoppingCartService.save(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get a shopping cart",
            description = "Pagination and sorting are also included")
    public ShoppingCartDto getShoppingCart(Pageable pageable) {

        return shoppingCartService.getShoppingCart(pageable);
    }

    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update quantity of cartItem")
    public CartItemDto update(@RequestBody @Valid UpdateCartItemDto updateCartItemDto,
                              @PathVariable Long id) {
        return cartItemService.update(updateCartItemDto, id);
    }

    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item by id")
    public void deleteCartItem(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
