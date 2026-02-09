package com.gcu.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gcu.inventory.data.ProductsDAO;
import com.gcu.inventory.model.Product;

/**
 * Service layer for product business logic and operations.
 * Acts as an intermediary between the controller layer and data access layer,
 * implementing business rules and coordinating product-related operations.
 * 
 * <p>This service provides methods for all CRUD operations on products
 * and abstracts the data access implementation from the controllers.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 4
 */
@Service
public class ProductService {

    // Data access object used for product persistence
    private final ProductsDAO productDAO;

    // Constructor-based dependency injection
    public ProductService(ProductsDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Creates a new product.
     *
     * @param product Product to be saved
     * @return True if the product was successfully created
     */
    public boolean create(Product product) {
        return productDAO.createProduct(product) > 0;
    }

    /**
     * Retrieves all products.
     *
     * @return List of all products
     */
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * <p>This method expects the product to exist and may
     * throw an exception if it does not.</p>
     *
     * @param id Unique identifier of the product
     * @return Product matching the given ID
     */
    public Product getById(int id) {
        return productDAO.findById(id);
    }

    /**
     * Updates an existing product.
     *
     * @param product Product containing updated values
     * @return True if the product was successfully updated
     */
    public boolean update(Product product) {
        return productDAO.updateProduct(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id Unique identifier of the product
     * @return True if the product was successfully deleted
     */
    public boolean delete(int id) {
        return productDAO.deleteById(id);
    }

    /**
     * Retrieves a product by ID as an Optional.
     *
     * <p>This method is used by REST controllers to
     * handle missing products without throwing exceptions.</p>
     *
     * @param id Unique identifier of the product
     * @return Optional containing the product if found
     */
    public Optional<Product> getByIdOptional(int id) {
        return productDAO.findByIdOptional(id);
    }
}
