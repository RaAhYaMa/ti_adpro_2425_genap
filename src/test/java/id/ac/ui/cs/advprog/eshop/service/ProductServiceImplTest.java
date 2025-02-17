package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    public void testCreate() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        Product createdProduct = productService.create(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertEquals(product.getProductQuantity(), createdProduct.getProductQuantity());
    }

    @Test
    public void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());
        List<Product> allProducts = productService.findAll();
        assertNotNull(allProducts);
        assertEquals(1, allProducts.size());
        assertEquals(product.getProductId(), allProducts.get(0).getProductId());
        assertEquals(product.getProductName(), allProducts.get(0).getProductName());
        assertEquals(product.getProductQuantity(), allProducts.get(0).getProductQuantity());
    }

    @Test
    public void testFindByProductId() {
        when(productRepository.findByProductId(any(String.class))).thenReturn(product);
        Product foundProduct = productService.findByProductId(product.getProductId());
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
        assertEquals(product.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    public void testUpdate() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        when(productRepository.update(any(String.class), any(Product.class))).thenReturn(updatedProduct);
        Product updatedProductResult = productService.update(product.getProductId(), updatedProduct);
        assertEquals(updatedProduct.getProductId(), updatedProductResult.getProductId());
        assertEquals(updatedProduct.getProductName(), updatedProductResult.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), updatedProductResult.getProductQuantity());
    }

    @Test
    public void testDelete() {
        when(productRepository.delete(any(String.class))).thenReturn(product);
        Product deletedProduct = productService.delete(product.getProductId());
        assertEquals(product.getProductId(), deletedProduct.getProductId());
        assertEquals(product.getProductName(), deletedProduct.getProductName());
        assertEquals(product.getProductQuantity(), deletedProduct.getProductQuantity());
    }
}
