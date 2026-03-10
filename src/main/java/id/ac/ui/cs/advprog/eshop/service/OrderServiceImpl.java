package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }

    @Override
    public Order createOrder(Order order) {

        if(repository.findById(order.getId()) != null){
            throw new IllegalArgumentException();
        }

        return repository.save(order);

    }

    @Override
    public Order updateStatus(String orderId, String status) {

        Order order = repository.findById(orderId);

        if(order == null){
            throw new NoSuchElementException();
        }

        order.setStatus(status);

        return repository.save(order);

    }

    @Override
    public Order findById(String orderId) {

        Order order = repository.findById(orderId);

        if(order == null){
            throw new NoSuchElementException();
        }

        return order;

    }

    @Override
    public List<Order> findAllByAuthor(String author) {

        return repository.findAllByAuthor(author);

    }

}