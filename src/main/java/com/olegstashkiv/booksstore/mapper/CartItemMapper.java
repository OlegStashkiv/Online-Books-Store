package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.cartitem.CartItemDto;
import com.olegstashkiv.booksstore.dto.cartitem.UpdateCartItemDto;
import com.olegstashkiv.booksstore.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    UpdateCartItemDto toUpdateDto(CartItem cartItem);

    CartItemDto toDto(CartItem cartItem);

    @AfterMapping
    default void setBookId(@MappingTarget CartItemDto cartDto, CartItem cartItem) {
        cartDto.setBookId(cartItem.getBook().getId());
    }

    @AfterMapping
    default void setBookTitle(@MappingTarget CartItemDto cartDto, CartItem cartItem) {
        cartDto.setTitle(cartItem.getBook().getTitle());
    }
}
