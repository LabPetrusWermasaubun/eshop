package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    // Mengcover branch: if (product.getProductId() == null) { ... }
    @Test
    void testCreateProductWithNoId() {
        Product product = new Product();
        product.setProductName("Kecap ABC");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertNotNull(product.getProductId()); // UUID otomatis tergenerate
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product found = productRepository.findById("123");
        assertNotNull(found);
        assertEquals("123", found.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product found = productRepository.findById("non-existent");
        assertNull(found);
    }

    @Test
    void testUpdateSuccess() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Lama");
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId("123");
        updatedData.setProductName("Baru");

        Product result = productRepository.update(updatedData);
        assertNotNull(result);
        assertEquals("Baru", productRepository.findById("123").getProductName());
    }

    @Test
    void testUpdateNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId("456"); // ID Berbeda
        updatedData.setProductName("Baru");

        Product result = productRepository.update(updatedData);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        productRepository.delete("123");
        Product found = productRepository.findById("123");
        assertNull(found);
    }
}