package com.gcu.inventory.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gcu.inventory.model.Product;

@Repository
public class ProductsDAO {

		private final JdbcTemplate jdbcTemplate;
		
		public ProductsDAO(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
		
		// Inserts a new product record into the database
		public int createProduct(Product product) {
			String sql = """
					INSERT INTO products (products_Name, products_Description, products_Price, products_Quantity) 
					VALUES (?, ?, ?, ?)
		""";
		
			
			return jdbcTemplate.update(
		            sql,
		            product.getName(),
		            product.getDescription(),
		            product.getPrice(),
		            product.getQuantity()
		        );
		}
		
		// Retrieves all products from the database for the list page
		public List<Product> findAll() {
			String sql = """
					SELECT products_Name, products_Description, products_Price, products_Quantity
					FROM products
					""";
			
			return jdbcTemplate.query(sql, new ProductRowMapper());
		}
		
		// Maps a databse row into a Product object
		private static class ProductRowMapper implements RowMapper<Product> {
			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product p = new Product();
				p.setName(rs.getString("products_Name"));
				p.setDescription(rs.getString("products_Description"));
				p.setPrice(rs.getDouble("products_Price"));
				p.setQuantity(rs.getInt("products_Quantity"));
				return p;
			}
		}
}
