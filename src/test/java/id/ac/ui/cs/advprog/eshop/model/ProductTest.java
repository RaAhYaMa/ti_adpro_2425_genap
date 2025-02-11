package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testSetProductId() {
        this.product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertEquals("a0f9de46-90b1-437d-a0bf-d0821dde9096", this.product.getProductId());
    }

    @Test
    void testSetProductName() {
        this.product.setProductName("Sampo Cap Usep");
        assertEquals("Sampo Cap Usep", this.product.getProductName());
    }

    @Test
    void testSetProductQuantity() {
        this.product.setProductQuantity(50);
        assertEquals(50, this.product.getProductQuantity());
    }
}
