package com.gcu.inventory.model;

/**
 * Model class representing a product in the inventory system.
 * Contains all product attributes including identification, descriptive information,
 * pricing, and stock quantity. Includes validation constraints for data integrity.
 * 
 * <p>This class is used throughout the application layers for transferring
 * product data between the view, controller, service, and data access layers.</p>
 * 
 * @author Victor Marrufo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 3
 */
public class Product {

    // Unique identifier for the product
    private int id;

    // Product name
    private String name;

    // Product description
    private String description;

    // Product price
    private double price;

    // Quantity available in inventory
    private int quantity;

    // Default constructor
    public Product() {
    }

    /**
     * Returns the product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the quantity in stock.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity in stock.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product ID.
     */
    public void setId(int id) {
        this.id = id;
    }
}
