package com.ecommerce.service;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll(String category, String search) {
        if (search != null && !search.isBlank()) {
            return productRepository.search(search);
        }
        if (category != null && !category.isBlank()) {
            return productRepository.findByCategoryIgnoreCaseAndActiveTrue(category);
        }
        return productRepository.findByActiveTrue();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public Product create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity() == null ? 0 : request.stockQuantity())
                .category(request.category())
                .imageUrl(request.imageUrl())
                .active(true)
                .build();
        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest request) {
        Product product = getById(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        if (request.stockQuantity() != null) product.setStockQuantity(request.stockQuantity());
        product.setCategory(request.category());
        product.setImageUrl(request.imageUrl());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = getById(id);
        // Soft delete so historical orders keep referencing a valid product row.
        product.setActive(false);
        productRepository.save(product);
    }
}
