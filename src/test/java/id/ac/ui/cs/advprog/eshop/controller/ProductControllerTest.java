package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Kecap")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Product()));
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        when(service.findById("123")).thenReturn(product);
        mockMvc.perform(get("/product/edit/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"));
    }

    @Test
    void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", "Kecap Baru"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("../list"));
    }
}