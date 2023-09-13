package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.user.UserRegistrationRequestDto;
import com.olegstashkiv.booksstore.dto.user.UserRegistrationResponseDto;
import com.olegstashkiv.booksstore.exception.RegistrationException;
import com.olegstashkiv.booksstore.mapper.UserMapper;
import com.olegstashkiv.booksstore.model.Role;
import com.olegstashkiv.booksstore.model.ShoppingCart;
import com.olegstashkiv.booksstore.model.User;
import com.olegstashkiv.booksstore.model.enums.RoleName;
import com.olegstashkiv.booksstore.repository.role.RoleRepository;
import com.olegstashkiv.booksstore.repository.shopingcart.ShoppingCartRepository;
import com.olegstashkiv.booksstore.repository.user.UserRepository;
import com.olegstashkiv.booksstore.service.UserService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserRegistrationResponseDto register(
            UserRegistrationRequestDto request
    ) throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setShippingAddress(request.getShippingAddress());

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(
                () -> new RegistrationException("Can't find such a role "
                        + RoleName.ROLE_USER));
        roles.add(defaultRole);
        user.setRoles(roles);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("Can't find a user with email "
                        + authentication.getName())
        );
    }
}
