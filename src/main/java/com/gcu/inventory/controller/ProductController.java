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

@Controller
@RequestMapping("/products")                                                           
public class ProductController {

	// Addition for milestone 4
	private final ProductService productService;
	
	// Addition for milestone 4
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	
    // Products list page
	// Milestone 4: Updated products list handler to pull from database
    @GetMapping("")
    public String products(Model model) {
    	model.addAttribute("products", productService.getAll());
        return "products/list";
    }

    // Create product page
    @GetMapping("/create")
    public String createProduct(Model model) {
    	model.addAttribute("product", new Product());
        return "products/create";
    }
    
    //Milestone 4: added PostMapping to handle form submission
    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") Product product) {
    	productService.create(product);
    	return "redirect:/products";
    }
    
    // For Milestone 5:
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
    	Product product = productService.getById(id);
    	model.addAttribute("product", product);
    	return "products/edit";
    }
    
    @PostMapping("/update")
    public String updateProduct(Product product) {
    	productService.update(product);
    	return "redirect:/products";
    }
    
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id) {
    	System.out.println("Delete request for id=" + id);
    	productService.delete(id);
    	return "redirect:/products";
    	
    }
}
