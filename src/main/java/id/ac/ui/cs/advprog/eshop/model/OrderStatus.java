package id.ac.ui.cs.advprog.eshop.model;

public enum OrderStatus {

    WAITING_PAYMENT,
    FAILED,
    CANCELLED,
    SUCCESS;

    public static boolean contains(String value) {

        for(OrderStatus status : OrderStatus.values()){
            if(status.name().equals(value)){
                return true;
            }
        }

        return false;
    }

}