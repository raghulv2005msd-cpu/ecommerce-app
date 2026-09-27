package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.*;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order checkout(User user, OrderRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Your cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.shippingAddress())
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cartItems) {
            Product product = ci.getProduct();
            if (ci.getQuantity() > product.getStockQuantity()) {
                throw new BadRequestException("Not enough stock for \"" + product.getName() + "\"");
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(ci.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();
            order.getItems().add(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));

            product.setStockQuantity(product.getStockQuantity() - ci.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PAID); // simplified: assume payment succeeds immediately
        Order saved = orderRepository.save(order);

        cartItemRepository.deleteByUser(user);
        return saved;
    }

    public List<Order> getOrdersForUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getById(Long id, User user) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("This order does not belong to you");
        }
        return order;
    }
}
