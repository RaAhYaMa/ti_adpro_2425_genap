package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        productService.create(product1);
        productService.create(product2);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductPageSuccess() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        mockMvc.perform(get("/product/edit/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"));

        verify(productService, times(1)).findByProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testEditProductPageFailure() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        when(productService.findByProductId("fb659eaf-2c49-561e-9870-81bf7a064be6")).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/product/edit/fb659eaf-2c49-561e-9870-81bf7a064be6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).findByProductId("fb659eaf-2c49-561e-9870-81bf7a064be6");
    }

    @Test
    void testEditProductPostSuccess() throws Exception {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        mockMvc.perform(post("/product/edit/eb558e9f-1c39-460e-8860-71af6af63bd6")
                        .flashAttr("product", product2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).update("eb558e9f-1c39-460e-8860-71af6af63bd6", product2);
    }

    @Test
    void testEditProductPostFailure() throws Exception {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        when(productService.update("fb659eaf-2c49-561e-9870-81bf7a064be6", product2)).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/product/edit/fb659eaf-2c49-561e-9870-81bf7a064be6")
                        .flashAttr("product", product2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).update("fb659eaf-2c49-561e-9870-81bf7a064be6", product2);
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        mockMvc.perform(post("/product/delete/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testDeleteProductFailure() throws Exception {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        when(productService.delete("fb659eaf-2c49-561e-9870-81bf7a064be6")).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/product/delete/fb659eaf-2c49-561e-9870-81bf7a064be6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("fb659eaf-2c49-561e-9870-81bf7a064be6");
    }
}
