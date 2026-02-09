package com.gcu.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.inventory.model.Product;
import com.gcu.inventory.service.ProductService;

/**
 * REST controller that exposes product data as JSON.
 *
 * <p>This controller provides API endpoints and returns data rather than
 * HTML views. It is separate from ProductController, which serves
 * Thymeleaf pages.</p>
 *
 * <p>Security for these endpoints is handled by Spring Security configuration.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 7
 */
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    // Service layer used to access product data
    private final ProductService productService;

    // Constructor-based dependency injection
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products as JSON.
     *
     * @return HTTP 200 with a list of products, or HTTP 500 if an error occurs
     */
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Returns a single product by its ID as JSON.
     *
     * @param id Unique identifier of the product
     * @return HTTP 200 with the product if found,
     *         HTTP 404 if the product does not exist,
     *         or HTTP 500 if an error occurs
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        try {
            Optional<Product> productOpt = productService.getByIdOptional(id);

            return productOpt
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
