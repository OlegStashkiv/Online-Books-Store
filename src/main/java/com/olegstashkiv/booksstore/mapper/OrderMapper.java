package com.olegstashkiv.booksstore.mapper;

import com.olegstashkiv.booksstore.config.MapperConfig;
import com.olegstashkiv.booksstore.dto.order.OrderDto;
import com.olegstashkiv.booksstore.model.Order;
import com.olegstashkiv.booksstore.model.OrderItem;
import com.olegstashkiv.booksstore.model.ShoppingCart;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapperConfig.class)
public abstract class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status",
            expression = "java(com.olegstashkiv.booksstore.model.enums.Status.PENDING)")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order toOrderFromCart(ShoppingCart shoppingCart);

    @Mapping(ignore = true, target = "orderItems")
    public abstract OrderDto toDto(Order order);

    @AfterMapping
    public void setUserId(@MappingTarget OrderDto orderDto, Order order) {
        orderDto.setUserId(order.getUser().getId());
    }

    @AfterMapping
    public void setOrderItems(@MappingTarget Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
    }
}
