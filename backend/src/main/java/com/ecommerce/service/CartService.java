package com.ecommerce.service;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public List<CartItem> getCart(User user) {
        return cartItemRepository.findByUser(user);
    }

    public CartItem addOrUpdateItem(User user, CartItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + request.productId()));

        if (request.quantity() > product.getStockQuantity()) {
            throw new BadRequestException("Only " + product.getStockQuantity() + " units of \"" + product.getName() + "\" left in stock");
        }

        CartItem item = cartItemRepository.findByUserAndProductId(user, request.productId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());

        item.setQuantity(request.quantity());
        return cartItemRepository.save(item);
    }

    public void removeItem(User user, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("This cart item does not belong to you");
        }
        cartItemRepository.delete(item);
    }

    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
