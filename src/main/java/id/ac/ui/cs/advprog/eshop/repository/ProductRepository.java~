package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();
    private Map<String, Product> productMap = new HashMap<>();

    public Product create(Product product) {
        productData.add(product);
        productMap.put(product.getProductId(), product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findByProductId(String productId) {
        Product product = productMap.get(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        return product;
    }

    public Product update(String productId, Product product) {
        Product existingProduct = productMap.get(productId);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        return existingProduct;
    }
}
