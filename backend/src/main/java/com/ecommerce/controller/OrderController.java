package com.ecommerce.controller;

import com.ecommerce.config.CurrentUserService;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CurrentUserService currentUserService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            Authentication authentication,
            @Valid @RequestBody OrderRequest request) {

        Order order = orderService.checkout(
                currentUserService.resolve(authentication),
                request);

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public List<Order> getMyOrders(Authentication authentication) {
        return orderService.getOrdersForUser(currentUserService.resolve(authentication));
    }

    @GetMapping("/{id}")
    public Order getOrder(Authentication authentication, @PathVariable Long id) {
        return orderService.getById(id, currentUserService.resolve(authentication));
    }
}
