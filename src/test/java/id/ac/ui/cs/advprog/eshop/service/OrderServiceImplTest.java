package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    OrderServiceImpl service;
    OrderRepository repository;

    List<Product> products;
    Order order;

    @BeforeEach
    void setUp(){

        repository = mock(OrderRepository.class);
        service = new OrderServiceImpl(repository);

        products = new ArrayList<>();
        products.add(new Product("1","Laptop",1000));

        order = new Order("1",products,System.currentTimeMillis(),"Petrus");

    }

    @Test
    void testCreateOrder(){

        when(repository.findById("1")).thenReturn(null);

        service.createOrder(order);

        verify(repository).save(order);

    }

    @Test
    void testCreateOrderIfAlreadyExist(){

        when(repository.findById("1")).thenReturn(order);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createOrder(order);
        });

    }

}