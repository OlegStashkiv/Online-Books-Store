package com.olegstashkiv.booksstore.service;

import com.olegstashkiv.booksstore.dto.user.UserRegistrationRequestDto;
import com.olegstashkiv.booksstore.dto.user.UserRegistrationResponseDto;
import com.olegstashkiv.booksstore.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(
            UserRegistrationRequestDto request
    ) throws RegistrationException;
}
