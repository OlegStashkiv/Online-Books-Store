package com.olegstashkiv.booksstore.dto.order;

import com.olegstashkiv.booksstore.dto.orderitem.OrderItemDto;
import com.olegstashkiv.booksstore.model.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
