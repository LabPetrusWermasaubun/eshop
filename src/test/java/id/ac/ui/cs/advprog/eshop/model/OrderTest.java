package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        products.add(new Product("1","Laptop",1000));
    }

    @Test
    void testCreateOrderSuccess() {
        Order order = new Order("1", products, System.currentTimeMillis(), "Petrus", "SUCCESS");

        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("1", products, System.currentTimeMillis(), "Petrus");

        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderEmptyProducts() {
        List<Product> emptyProducts = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Order("1", emptyProducts, System.currentTimeMillis(), "Petrus");
        });
    }

    @Test
    void testCreateOrderInvalidStatus() {
        Order order = new Order("1", products, System.currentTimeMillis(), "Petrus", "INVALID");

        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("1", products, System.currentTimeMillis(), "Petrus");

        order.setStatus("CANCELLED");

        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("1", products, System.currentTimeMillis(), "Petrus");

        order.setStatus("INVALID");

        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

}