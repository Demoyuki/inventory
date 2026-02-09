package com.gcu.inventory.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gcu.inventory.model.Product;

/**
 * Data Access Object for Product entity.
 * Handles all database operations for products using Spring's JdbcTemplate.
 * Implements CRUD operations and provides SQL query execution for the products table.
 * 
 * <p>This DAO uses JdbcTemplate to prevent SQL injection attacks and provides
 * a clean abstraction layer for database operations.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 4
 */
@Repository
public class ProductsDAO {

    // JdbcTemplate used to execute SQL queries
    private final JdbcTemplate jdbcTemplate;

    // Constructor-based dependency injection
    public ProductsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Inserts a new product record into the database.
     *
     * @param product Product data to be saved
     * @return Number of rows affected
     */
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

    /**
     * Retrieves all product records from the database.
     *
     * @return List of all products
     */
    public List<Product> findAll() {
        String sql = """
                SELECT idProducts, products_Name, products_Description, products_Price, products_Quantity
                FROM products
                """;

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    /**
     * Maps a database row to a Product object.
     */
    private static class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product p = new Product();
            p.setId(rs.getInt("idProducts"));
            p.setName(rs.getString("products_Name"));
            p.setDescription(rs.getString("products_Description"));
            p.setPrice(rs.getDouble("products_Price"));
            p.setQuantity(rs.getInt("products_Quantity"));
            return p;
        }
    }

    /**
     * Retrieves a single product by its ID.
     *
     * <p>This method expects the product to exist and will throw
     * an exception if no record is found.</p>
     *
     * @param id Unique identifier of the product
     * @return Product matching the given ID
     */
    public Product findById(int id) {
        String sql = "SELECT idProducts, products_Name, products_Description, products_Price, products_Quantity " +
                     "FROM products WHERE idProducts = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getInt("idProducts"));
            p.setName(rs.getString("products_Name"));
            p.setDescription(rs.getString("products_Description"));
            p.setPrice(rs.getDouble("products_Price"));
            p.setQuantity(rs.getInt("products_Quantity"));
            return p;
        }, id);
    }

    /**
     * Updates an existing product record in the database.
     *
     * @param product Product containing updated values
     * @return True if at least one row was updated
     */
    public boolean updateProduct(Product product) {
        String sql = """
                UPDATE products
                SET products_Name = ?, products_Description = ?, products_Price = ?, products_Quantity = ?
                WHERE idProducts = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getId()
        );

        return rows > 0;
    }

    /**
     * Deletes a product record by its ID.
     *
     * @param id Unique identifier of the product
     * @return True if a record was deleted
     */
    public boolean deleteById(int id) {
        String sql = "DELETE FROM products WHERE idProducts = ?";
        int rows = jdbcTemplate.update(sql, id);
        return rows > 0;
    }

    /**
     * Retrieves a product by ID and returns an Optional result.
     *
     * <p>This method returns Optional.empty() when no product
     * is found instead of throwing an exception. This is used
     * by REST endpoints to return HTTP 404 responses.</p>
     *
     * @param id Unique identifier of the product
     * @return Optional containing the product if found
     */
    public Optional<Product> findByIdOptional(int id) {
        String sql = "SELECT idProducts, products_Name, products_Description, products_Price, products_Quantity " +
                     "FROM products WHERE idProducts = ?";

        try {
            Product product = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Product p = new Product();
                p.setId(rs.getInt("idProducts"));
                p.setName(rs.getString("products_Name"));
                p.setDescription(rs.getString("products_Description"));
                p.setPrice(rs.getFloat("products_Price"));
                p.setQuantity(rs.getInt("products_Quantity"));
                return p;
            }, id);

            return Optional.of(product);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
