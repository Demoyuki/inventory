package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.inventory.model.Product;
import com.gcu.inventory.service.ProductService;

/**
 * Spring MVC controller for product management operations.
 * Handles all web-based product CRUD operations including viewing,
 * adding, editing, and deleting products.
 * 
 * <p>This controller serves Thymeleaf templates and requires user authentication
 * through Spring Security form login.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 4
 */
@Controller
@RequestMapping("/products")                                                           
public class ProductController {

    // Service layer for product business logic and database operations
	private final ProductService productService;
	
    // Constructor-based dependency injection
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
    /**
     * Displays the product list page with all products in the inventory.
     *
     * @param model Spring MVC model used to pass product data to the view
     * @return Thymeleaf template for displaying the product list
     */
    @GetMapping("")
    public String products(Model model) {
    	model.addAttribute("products", productService.getAll());
        return "products/list";
    }

    /**
     * Displays the form used to create a new product.
     *
     * @param model Spring MVC model used to bind an empty Product object
     * @return Thymeleaf template for the create product form
     */
    @GetMapping("/create")
    public String createProduct(Model model) {
    	model.addAttribute("product", new Product());
        return "products/create";
    }
    
    /**
     * Handles submission of the create product form.
     * Saves the new product and redirects to the product list.
     *
     * @param product Product object populated from the form submission
     * @return Redirect to the product list page
     */
    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") Product product) {
    	productService.create(product);
    	return "redirect:/products";
    }
    
    /**
     * Displays the edit form for an existing product.
     *
     * @param id Unique identifier of the product to edit
     * @param model Spring MVC model used to pass the product to the view
     * @return Thymeleaf template for the edit product form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
    	Product product = productService.getById(id);
    	model.addAttribute("product", product);
    	return "products/edit";
    }

    /**
     * Handles submission of the edit product form.
     * Updates the existing product and redirects to the product list.
     *
     * @param product Product object containing updated values
     * @return Redirect to the product list page
     */
    @PostMapping("/update")
    public String updateProduct(Product product) {
    	productService.update(product);
    	return "redirect:/products";
    }


    /**
     * Handles deletion of a product from the inventory.
     *
     * @param id Unique identifier of the product to delete
     * @return Redirect to the product list page
     */

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id) {
    	System.out.println("Delete request for id=" + id);
    	productService.delete(id);
    	return "redirect:/products";
    	
    }
}
