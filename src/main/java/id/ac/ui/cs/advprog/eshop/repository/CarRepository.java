package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;

public interface CarRepository {
    public Car create(Car car);
    public List<Car> findAll();
    public Car findById (String id);
    public Car update(String id, Car updateCar);
    public void delete(String id);
}
