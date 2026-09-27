package com.ecommerce.controller;

import com.ecommerce.config.CurrentUserService;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.model.CartItem;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CurrentUserService currentUserService;

    @GetMapping
    public List<CartItem> getCart(Authentication authentication) {
        return cartService.getCart(currentUserService.resolve(authentication));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItem(Authentication authentication,
                                             @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addOrUpdateItem(currentUserService.resolve(authentication), request));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItem(Authentication authentication, @PathVariable Long cartItemId) {
        cartService.removeItem(currentUserService.resolve(authentication), cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(currentUserService.resolve(authentication));
        return ResponseEntity.noContent().build();
    }
}
