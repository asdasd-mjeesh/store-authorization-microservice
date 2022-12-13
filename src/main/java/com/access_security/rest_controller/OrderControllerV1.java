package com.access_security.rest_controller;

import com.access_security.model.common.OrderStatus;
import com.access_security.model.request.filter.OrderFilter;
import com.access_security.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderControllerV1 {
    private final OrderService orderService;

    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestParam(name = "accountId") Long accountId) {
        var createdOrder = orderService.createOrder(accountId);
        if (createdOrder.isPresent()) {
            return new ResponseEntity<>(createdOrder.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PatchMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam(name = "orderId") Long orderId,
                                          @RequestParam(name = "status") OrderStatus status) {
        var orderResponse = orderService.changeStatus(orderId, status);
        if (orderResponse.isPresent()) {
            return ResponseEntity.ok(orderResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        var orderResponse = orderService.getById(id);
        if (orderResponse.isPresent()) {
            return ResponseEntity.ok(orderResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PutMapping("/filter")
    public ResponseEntity<?> getByFilter(@RequestBody OrderFilter filter) {
        var ordersResponse = orderService.getByFilter(filter);
        return ResponseEntity.ok(ordersResponse);
    }
}
