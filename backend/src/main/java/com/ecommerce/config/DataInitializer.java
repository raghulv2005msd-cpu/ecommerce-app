
package com.ecommerce.config;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {

        // Insert products only when database is empty
        if (productRepository.count() > 0) {
            return;
        }

        // 1. Wireless Headphones
        productRepository.save(Product.builder()
                .name("Wireless Headphones")
                .description("High quality wireless headphones")
                .price(new BigDecimal("1999.00"))
                .stockQuantity(20)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e")
                .active(true)
                .build());

        // 2. Smart Watch
        productRepository.save(Product.builder()
                .name("Smart Watch")
                .description("Modern smart watch with fitness tracking")
                .price(new BigDecimal("2999.00"))
                .stockQuantity(15)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1523275335684-37898b6baf30")
                .active(true)
                .build());

        // 3. Running Shoes
        productRepository.save(Product.builder()
                .name("Running Shoes")
                .description("Comfortable shoes for running and walking")
                .price(new BigDecimal("2499.00"))
                .stockQuantity(25)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1542291026-7eec264c27ff")
                .active(true)
                .build());

        // 4. Backpack
        productRepository.save(Product.builder()
                .name("Backpack")
                .description("Stylish backpack for daily use")
                .price(new BigDecimal("1299.00"))
                .stockQuantity(30)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1553062407-98eeb64c6a62")
                .active(true)
                .build());

        // 5. Laptop
        productRepository.save(Product.builder()
                .name("Laptop")
                .description("Powerful laptop for work, study and entertainment")
                .price(new BigDecimal("54999.00"))
                .stockQuantity(10)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1496181133206-80ce9b88a853")
                .active(true)
                .build());

        // 6. Smartphone
        productRepository.save(Product.builder()
                .name("Smartphone")
                .description("Modern smartphone with high performance camera")
                .price(new BigDecimal("24999.00"))
                .stockQuantity(15)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9")
                .active(true)
                .build());

        // 7. Bluetooth Speaker
        productRepository.save(Product.builder()
                .name("Bluetooth Speaker")
                .description("Portable wireless speaker with clear sound")
                .price(new BigDecimal("1999.00"))
                .stockQuantity(20)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1608043152269-423dbba4e7e1")
                .active(true)
                .build());

        // 8. Wireless Keyboard
        productRepository.save(Product.builder()
                .name("Wireless Keyboard")
                .description("Slim wireless keyboard for comfortable typing")
                .price(new BigDecimal("1499.00"))
                .stockQuantity(25)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1587829741301-dc798b83add3")
                .active(true)
                .build());

        // 9. Gaming Mouse
        productRepository.save(Product.builder()
                .name("Gaming Mouse")
                .description("High precision gaming mouse with ergonomic design")
                .price(new BigDecimal("999.00"))
                .stockQuantity(30)
                .category("Electronics")
                .imageUrl("https://images.unsplash.com/photo-1527814050087-3793815479db")
                .active(true)
                .build());

        // 10. Cotton T-Shirt
        productRepository.save(Product.builder()
                .name("Cotton T-Shirt")
                .description("Comfortable cotton t-shirt for everyday wear")
                .price(new BigDecimal("799.00"))
                .stockQuantity(40)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab")
                .active(true)
                .build());

        // 11. Jeans
        productRepository.save(Product.builder()
                .name("Jeans")
                .description("Classic comfortable jeans for casual wear")
                .price(new BigDecimal("1499.00"))
                .stockQuantity(25)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1542272604-787c3835535d")
                .active(true)
                .build());

        // 12. Sports Shoes
        productRepository.save(Product.builder()
                .name("Sports Shoes")
                .description("Lightweight sports shoes for daily activities")
                .price(new BigDecimal("2999.00"))
                .stockQuantity(20)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1542291026-7eec264c27ff")
                .active(true)
                .build());

        // 13. Sunglasses
        productRepository.save(Product.builder()
                .name("Sunglasses")
                .description("Stylish sunglasses with UV protection")
                .price(new BigDecimal("699.00"))
                .stockQuantity(35)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1511499767150-a48a237f0083")
                .active(true)
                .build());

        // 14. Travel Bag
        productRepository.save(Product.builder()
                .name("Travel Bag")
                .description("Spacious travel bag for trips and vacations")
                .price(new BigDecimal("1799.00"))
                .stockQuantity(18)
                .category("Fashion")
                .imageUrl("https://images.unsplash.com/photo-1553062407-98eeb64c6a62")
                .active(true)
                .build());

        System.out.println("14 sample products inserted successfully!");
    }
}
