package com.gcu.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gcu.inventory.data.ProductsDAO;
import com.gcu.inventory.model.Product;

@Service
public class ProductService {
	
	private final ProductsDAO productDAO;
	
	public ProductService(ProductsDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	// Saves a product to the database using the DAO layer
	public boolean create(Product product) {
		return productDAO.createProduct(product) > 0;
	}
	
	// Gets all products for displaying on the products list page
	public List<Product> getAll() {
		return productDAO.findAll();
	}
	
}
