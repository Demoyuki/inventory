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
 * This controller exposes Product data through REST endpoints for JSON.
 * This is different from ProductController because that returns HTML pages.
 * 
 * Secured via HTTP Basic Authentication (see SecurityConfig apiFilterChain).
 */
@RestController
@RequestMapping("/api/products")
public class ProductRestController {
	
	// Uses service layer
	private final ProductService productService;
	
	// Constructor injection
	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * REST API 1 – Return all products as JSON.
	 * GET /api/products
	 * 
	 * @return 200 OK with List<Product>, or 500 on exception
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
	 * REST API 2 – Return a single product by ID as JSON.
	 * GET /api/products/{id}
	 * 
	 * @param id the product ID
	 * @return 200 OK with Product if found, 404 NOT_FOUND if not found, or 500 on exception
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