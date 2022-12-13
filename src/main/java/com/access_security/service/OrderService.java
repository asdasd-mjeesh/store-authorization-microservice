package com.access_security.service;

import com.access_security.model.common.OrderStatus;
import com.access_security.model.request.filter.OrderFilter;
import com.access_security.model.response.order.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponse> getById(Long id);

    List<OrderResponse> getByFilter(OrderFilter filter);

    Optional<OrderResponse> createOrder(Long accountId);

    Optional<OrderResponse> changeStatus(Long orderId, OrderStatus status);
}
