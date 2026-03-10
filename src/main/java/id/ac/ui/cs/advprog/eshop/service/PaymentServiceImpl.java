package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository){
        this.repository = repository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment(UUID.randomUUID().toString(),order,method,paymentData);

        if(method.equals("VOUCHER")){

            String code = paymentData.get("voucherCode");

            if(code != null &&
                    code.length() == 16 &&
                    code.startsWith("ESHOP") &&
                    code.replaceAll("[^0-9]","").length() == 8){

                payment.setStatus("SUCCESS");

            }else{

                payment.setStatus("REJECTED");

            }

        }

        repository.save(payment);

        return payment;

    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        if(status.equals("SUCCESS")){
            payment.getOrder().setStatus("SUCCESS");
        }

        if(status.equals("REJECTED")){
            payment.getOrder().setStatus("FAILED");
        }

        return payment;

    }

    @Override
    public Payment getPayment(String paymentId) {

        return repository.findById(paymentId);

    }

}