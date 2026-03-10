package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    private Payment samplePayment;

    @BeforeEach
    void initialize() {
        List<Product> productList = new ArrayList<>();

        Product item = new Product();
        item.setProductId("prd-01");
        item.setProductName("Sabun Cuci");
        item.setProductQuantity(2);

        productList.add(item);

        Order orderData = new Order("ord-77", productList, 456L, "Budi");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP9999ZZZ1111");

        samplePayment = new Payment("payment-77", "VOUCHER_CODE", paymentData, orderData, "SUCCESS");
    }

    @Test
    void shouldDisplayAdminPaymentList() throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(samplePayment);

        when(paymentService.getAllPayments()).thenReturn(paymentList);

        mvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/listPayment"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void shouldOpenDetailFormPage() throws Exception {
        mvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/detailForm"));
    }

    @Test
    void shouldShowAdminPaymentDetail() throws Exception {
        when(paymentService.getPayment("payment-77")).thenReturn(samplePayment);

        mvc.perform(get("/payment/admin/detail/payment-77"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/adminDetail"));
    }

    @Test
    void shouldUpdatePaymentStatus() throws Exception {
        when(paymentService.getPayment("payment-77")).thenReturn(samplePayment);

        mvc.perform(post("/payment/admin/set-status/payment-77")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }

    @Test
    void shouldShowUserPaymentDetail() throws Exception {
        when(paymentService.getPayment("payment-77")).thenReturn(samplePayment);

        mvc.perform(get("/payment/detail/payment-77"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/userDetail"));
    }
}