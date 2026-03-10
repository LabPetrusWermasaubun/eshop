package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private List<Order> orders = new ArrayList<>();

    public Order save(Order order){

        Order existing = findById(order.getId());

        if(existing == null){
            orders.add(order);
        }else{
            orders.remove(existing);
            orders.add(order);
        }

        return order;

    }

    public Order findById(String id){

        for(Order order : orders){
            if(order.getId().equals(id)){
                return order;
            }
        }

        return null;

    }

    public List<Order> findAllByAuthor(String author){

        List<Order> result = new ArrayList<>();

        for(Order order : orders){
            if(order.getAuthor().equals(author)){
                result.add(order);
            }
        }

        return result;

    }

}