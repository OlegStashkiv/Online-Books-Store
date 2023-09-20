package com.olegstashkiv.booksstore.dto.order;

import com.olegstashkiv.booksstore.model.enums.Status;
import lombok.Data;

@Data
public class UpdateStatusOrderDto {
    private Status status;
}
