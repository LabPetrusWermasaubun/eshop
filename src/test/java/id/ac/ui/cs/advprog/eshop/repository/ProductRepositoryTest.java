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

    private static final String PRODUCT_ID = "123";
    private static final String SECOND_PRODUCT_ID = "222";
    private static final String FIRST_PRODUCT_ID = "111";
    private static final String NON_EXISTENT_ID = "non-existent";
    private static final String DOES_NOT_EXIST = "does-not-exist";

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

    @Test
    void testCreateProductWithNoId() {
        Product product = new Product();
        product.setProductName("Kecap ABC");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertNotNull(product.getProductId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        productRepository.create(product);

        Product found = productRepository.findById(PRODUCT_ID);

        assertNotNull(found);
        assertEquals(PRODUCT_ID, found.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product found = productRepository.findById(NON_EXISTENT_ID);
        assertNull(found);
    }

    @Test
    void testUpdateSuccess() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductName("Lama");
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId(PRODUCT_ID);
        updatedData.setProductName("Baru");

        Product result = productRepository.update(updatedData);

        assertNotNull(result);
        assertEquals("Baru", result.getProductName());
    }

    @Test
    void testUpdateNotFound() {
        Product updatedData = new Product();
        updatedData.setProductId("456");
        updatedData.setProductName("Baru");

        Product result = productRepository.update(updatedData);

        assertNull(result);
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        productRepository.create(product);

        productRepository.delete(PRODUCT_ID);

        Product found = productRepository.findById(PRODUCT_ID);
        assertNull(found);
    }

    @Test
    void testDeleteNonExistentProduct() {
        productRepository.delete(DOES_NOT_EXIST);

        Product result = productRepository.findById(DOES_NOT_EXIST);
        assertNull(result);
    }

    @Test
    void testUpdateSecondProductInList() {
        Product first = new Product();
        first.setProductId(FIRST_PRODUCT_ID);
        first.setProductName("First");
        productRepository.create(first);

        Product second = new Product();
        second.setProductId(SECOND_PRODUCT_ID);
        second.setProductName("Second");
        productRepository.create(second);

        Product updatedSecond = new Product();
        updatedSecond.setProductId(SECOND_PRODUCT_ID);
        updatedSecond.setProductName("Updated Second");
        updatedSecond.setProductQuantity(50);

        Product result = productRepository.update(updatedSecond);

        assertNotNull(result);
        assertEquals("Updated Second", result.getProductName());
    }

    @Test
    void testDeleteSecondProduct() {
        Product first = new Product();
        first.setProductId(FIRST_PRODUCT_ID);
        productRepository.create(first);

        Product second = new Product();
        second.setProductId(SECOND_PRODUCT_ID);
        productRepository.create(second);

        productRepository.delete(SECOND_PRODUCT_ID);

        assertNull(productRepository.findById(SECOND_PRODUCT_ID));
        assertNotNull(productRepository.findById(FIRST_PRODUCT_ID));
    }
}