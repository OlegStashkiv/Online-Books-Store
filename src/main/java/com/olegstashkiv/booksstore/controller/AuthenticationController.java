package com.olegstashkiv.booksstore.controller;

import com.olegstashkiv.booksstore.dto.user.UserLoginRequestDto;
import com.olegstashkiv.booksstore.dto.user.UserLoginResponseDto;
import com.olegstashkiv.booksstore.dto.user.UserRegistrationRequestDto;
import com.olegstashkiv.booksstore.dto.user.UserRegistrationResponseDto;
import com.olegstashkiv.booksstore.security.AuthenticationService;
import com.olegstashkiv.booksstore.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request
    ) {
        return userService.register(request);
    }
}
