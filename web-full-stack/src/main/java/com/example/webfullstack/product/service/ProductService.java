package com.example.webfullstack.product.service;

import com.example.webfullstack.product.domain.Product;
import com.example.webfullstack.product.repository.ProductRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product loadProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Long createProduct(int stock) {
        Product product = productRepository.save(new Product(stock));
        return product.getId();
    }

    @Transactional
    public void removeProductStock(Long productId, int amount) {
        Product product = loadProduct(productId);

        // 재고 확인
        if (!isUpdatableProduct(product, amount)) {
            throw new RuntimeException("out of stock");
        }

        product.setStock(product.getStock() - amount);
    }

    @Transactional
    public synchronized void removeProductStockSynchronized(Long productId, int amount) {
        Product product = loadProduct(productId);

        // 재고 확인
        if (!isUpdatableProduct(product, amount)) {
            throw new RuntimeException("out of stock");
        }

        product.setStock(product.getStock() - amount);
    }

    public boolean isUpdatableProduct(Product product, int amount) {
        return product.getStock() >= amount;
    }
}
