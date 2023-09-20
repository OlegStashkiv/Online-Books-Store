package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.order.CreateOrderRequestDto;
import com.olegstashkiv.booksstore.dto.order.OrderDto;
import com.olegstashkiv.booksstore.dto.order.UpdateStatusOrderDto;
import com.olegstashkiv.booksstore.dto.orderitem.OrderItemDto;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Set<OrderDto> findAll(Pageable pageable, Long id);

    OrderDto addOrder(CreateOrderRequestDto requestDto, Long id);

    OrderDto changeStatus(Long id, UpdateStatusOrderDto updateStatusDto);

    Set<OrderItemDto> getByOrderId(Long id);

    OrderItemDto getByOrderIdAndOrderItemId(Long orderId, Long orderItemId);
}
