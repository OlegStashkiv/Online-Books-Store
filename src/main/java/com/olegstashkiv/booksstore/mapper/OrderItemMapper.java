package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.orderitem.OrderItemDto;
import com.olegstashkiv.booksstore.model.CartItem;
import com.olegstashkiv.booksstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "orderItem.book.id")
    OrderItemDto orderItemToDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "cartItem.book.price")
    OrderItem cartItemToOrderItem(CartItem cartItem);
}
