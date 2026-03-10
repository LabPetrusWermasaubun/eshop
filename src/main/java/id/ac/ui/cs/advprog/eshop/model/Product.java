package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
    }
    public Product(String productId, String productName, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getName() {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public int getQuantity() {
        return productQuantity;
    }

    public void setQuantity(int quantity) {
        this.productQuantity = quantity;
    }
}