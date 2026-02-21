package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void testCreate() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);
        Product result = service.create(product);
        assertEquals(product, result);
    }

    @Test
    void testFindAll() {
        Product product = new Product();
        Iterator<Product> iterator = List.of(product).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = service.findAll();
        assertEquals(1, result.size());
        assertEquals(product, result.getFirst());
    }

    @Test
    void testDelete() {
        service.delete("123");
        verify(productRepository, times(1)).delete("123");
    }

    @Test
    void testFindById() {
        Product product = new Product();
        when(productRepository.findById("123")).thenReturn(product);
        Product result = service.findById("123");
        assertEquals(product, result);
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        when(productRepository.update(product)).thenReturn(product);
        Product result = service.update(product);
        assertEquals(product, result);
    }
}