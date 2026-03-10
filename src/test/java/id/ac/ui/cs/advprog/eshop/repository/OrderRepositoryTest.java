package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    OrderRepository repository;
    Order order;

    @BeforeEach
    void setUp(){

        repository = new OrderRepository();

        List<Product> products = new ArrayList<>();
        products.add(new Product("1","Laptop",1000));

        order = new Order("1",products,System.currentTimeMillis(),"Petrus");

    }

    @Test
    void testSaveCreate(){

        repository.save(order);

        assertEquals(order, repository.findById("1"));

    }

    @Test
    void testSaveUpdate(){

        repository.save(order);

        order.setStatus("SUCCESS");

        repository.save(order);

        assertEquals("SUCCESS", repository.findById("1").getStatus());

    }

    @Test
    void testFindByIdIfIdFound(){

        repository.save(order);

        assertNotNull(repository.findById("1"));

    }

    @Test
    void testFindByIdIfIdNotFound(){

        assertNull(repository.findById("2"));

    }

    @Test
    void testFindAllByAuthorIfAuthorFound(){

        repository.save(order);

        List<Order> orders = repository.findAllByAuthor("Petrus");

        assertEquals(1, orders.size());

    }

    @Test
    void testFindAllByAuthorIfAllLowercase(){

        repository.save(order);

        List<Order> orders = repository.findAllByAuthor("petrus");

        assertEquals(0, orders.size());

    }

}