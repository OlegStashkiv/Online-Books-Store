package com.olegstashkiv.booksstore.dto.order;

import lombok.Data;

@Data
public class CreateOrderRequestDto {
    private String shippingAddress;
}
