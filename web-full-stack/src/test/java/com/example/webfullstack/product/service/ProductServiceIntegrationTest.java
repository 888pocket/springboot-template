package com.example.webfullstack.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.webfullstack.product.domain.Product;
import com.example.webfullstack.product.repository.ProductRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void createProduct_IntegrationTest() {
        // Given, When
        Long productId = productService.createProduct(20);

        // Then
        assertNotNull(productId);
    }

    @Test
    public void readProduct_IntegrationTest() {
        // Given
        int stock = 20;
        Long productId = productService.createProduct(stock);

        // When
        Product product = productService.loadProduct(productId);

        // Then
        assertNotNull(product);
        assertEquals(product.getId(), productId);
        assertEquals(product.getStock(), stock);
    }

    @Test
    public void readProduct_NotFound_IntegrationTest() {
        // Given
        Long nonExistingProductId = -1L; // 존재하지 않는 Product ID

        // When, Then
        assertThrows(NoSuchElementException.class, () -> productService.loadProduct(nonExistingProductId));
    }

    @Test
    public void removeProductStock_IntegrationTest() {
        // Given
        int stock = 20;
        Long productId = productService.createProduct(stock);

        // When
        productService.removeProductStockSynchronized(productId, 1);

        // Then
        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent());
        assertEquals(foundProduct.get().getId(), productId);
        assertEquals(foundProduct.get().getStock(), stock - 1);
    }
}