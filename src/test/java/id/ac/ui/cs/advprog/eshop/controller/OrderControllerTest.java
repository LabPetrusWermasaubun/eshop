package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    private Order sampleOrder;

    @BeforeEach
    void initialize() {
        List<Product> productList = new ArrayList<>();

        Product item = new Product();
        item.setProductId("a1b2c3d4-5678-9101-1121-314151617181");
        item.setProductName("Sabun Mandi Bersih");
        item.setProductQuantity(3);

        productList.add(item);

        sampleOrder = new Order("order-999", productList, 987654321L, "Budi");
    }

    @Test
    void shouldDisplayCreateOrderPage() throws Exception {
        mvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/createOrder"));
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(sampleOrder);

        mvc.perform(post("/order/create")
                        .param("author", "Budi"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }

    @Test
    void shouldShowOrderHistoryPage() throws Exception {
        mvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/historyOrder"));
    }

    @Test
    void shouldReturnOrderListByAuthor() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(sampleOrder);

        when(orderService.findAllByAuthor("Budi")).thenReturn(orders);

        mvc.perform(post("/order/history")
                        .param("author", "Budi"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/listOrder"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("author", "Budi"));
    }

    @Test
    void shouldOpenPaymentPage() throws Exception {
        when(orderService.findById("order-999")).thenReturn(sampleOrder);

        mvc.perform(get("/order/pay/order-999"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void shouldProcessPayment() throws Exception {
        when(orderService.findById("order-999")).thenReturn(sampleOrder);

        id.ac.ui.cs.advprog.eshop.model.Payment fakePayment =
                new id.ac.ui.cs.advprog.eshop.model.Payment(
                        "payment-001",
                        "VOUCHER_CODE",
                        new java.util.HashMap<>(),
                        sampleOrder
                );

        when(paymentService.addPayment(any(), any(), any())).thenReturn(fakePayment);

        mvc.perform(post("/order/pay/order-999")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP8888XYZ9999"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/paymentResult"))
                .andExpect(model().attributeExists("paymentId"));
    }
}