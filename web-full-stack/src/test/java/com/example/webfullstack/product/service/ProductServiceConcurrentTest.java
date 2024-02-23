package com.example.webfullstack.product.service;

import com.example.webfullstack.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceConcurrentTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void removeProduct_unSynchronized_ConcurrentTest() throws InterruptedException {
        // Given
        Long productId = productService.createProduct(20);
        int numberOfThreads = 100;

        // When
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                try {
                    productService.removeProductStock(productId, 1);
                } catch (RuntimeException e) {

                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Then
        Assertions.assertTrue(productService.loadProduct(productId).getStock() != 0);
    }

    @Test
    public void removeProduct_synchronized_ConcurrentTest() throws InterruptedException {
        // given
        Long productId = productService.createProduct(20);
        int numberOfThreads = 100;

        // when
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                try {
                    productService.removeProductStockSynchronized(productId, 1);
                } catch (RuntimeException e) {

                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Then
        Assertions.assertTrue(productService.loadProduct(productId).getStock() == 0);
    }
}