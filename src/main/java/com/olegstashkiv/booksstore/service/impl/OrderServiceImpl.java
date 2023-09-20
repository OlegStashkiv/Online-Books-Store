package com.olegstashkiv.booksstore.service.impl;

import com.olegstashkiv.booksstore.dto.order.CreateOrderRequestDto;
import com.olegstashkiv.booksstore.dto.order.OrderDto;
import com.olegstashkiv.booksstore.dto.order.UpdateStatusOrderDto;
import com.olegstashkiv.booksstore.dto.orderitem.OrderItemDto;
import com.olegstashkiv.booksstore.exception.EntityNotFoundException;
import com.olegstashkiv.booksstore.mapper.OrderItemMapper;
import com.olegstashkiv.booksstore.mapper.OrderMapper;
import com.olegstashkiv.booksstore.model.CartItem;
import com.olegstashkiv.booksstore.model.Order;
import com.olegstashkiv.booksstore.model.OrderItem;
import com.olegstashkiv.booksstore.model.ShoppingCart;
import com.olegstashkiv.booksstore.repository.CartItemRepository;
import com.olegstashkiv.booksstore.repository.OrderItemRepository;
import com.olegstashkiv.booksstore.repository.OrderRepository;
import com.olegstashkiv.booksstore.repository.ShoppingCartRepository;
import com.olegstashkiv.booksstore.service.OrderService;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public Set<OrderItemDto> getByOrderId(Long id) {
        return orderItemRepository.getOrderItemsByOrderId(id).stream()
                .map(orderItemMapper::orderItemToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemDto getByOrderIdAndOrderItemId(Long orderId, Long orderItemId) {
        OrderItem orderItem = orderItemRepository.getOrderItemByOrderIdAndId(orderId, orderItemId);
        return orderItemMapper.orderItemToDto(orderItem);
    }

    @Override
    public Set<OrderDto> findAll(Pageable pageable, Long id) {
        return orderRepository.findAllByUserId(id, pageable).stream()
                .map(order -> {
                    OrderDto orderDto = orderMapper.toDto(order);
                    orderDto.setOrderItems(getOrderItems(orderDto));
                    return orderDto;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public OrderDto addOrder(CreateOrderRequestDto requestDto, Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(
                () -> new EntityNotFoundException("Cant find shopping Cart by id: " + shoppingCartId)
        );
        Set<CartItem> cartItems = new HashSet<>(
                cartItemRepository.findCartItemsByShoppingCartId(shoppingCartId));
        shoppingCart.setCartItems(cartItems);
        Order order = orderMapper.toOrderFromCart(shoppingCart);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(getTotalPrice(order.getOrderItems()));
        Order savedOrder = saveOrderAndOrderItemsToDb(cartItems, order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto changeStatus(Long id, UpdateStatusOrderDto updateStatusDto) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find order by id: " + id));
        order.setStatus(updateStatusDto.getStatus());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    private BigDecimal getTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order saveOrderAndOrderItemsToDb(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> orderItems = cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        orderItems.forEach(orderItem -> orderItem.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);
        return savedOrder;
    }

    private Set<OrderItemDto> getOrderItems(OrderDto orderDto) {
        return orderItemRepository
                .getOrderItemsByOrderId(orderDto.getId()).stream()
                .map(orderItemMapper::orderItemToDto)
                .collect(Collectors.toSet());
    }
}
