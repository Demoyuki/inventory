package com.gcu.inventory.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Updates For Milestone 4
 * Handles database access related to users
 * Uses Spring JDBC via JdbcTemplate
 * Implements SQL operations for:
 * 	 Creating User
 * 	 Checking if username exist
 * 	 Validating Login Credentials
 */
@Repository
public class UserDAO {

	// Provides access to the database using Spring JDBC
	private final JdbcTemplate jdbcTemplate;

	// Constructor based injection of JdbcTemplate
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks if username already exist
     * @param username
     * @return
     */
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    /**
     * To create new user and add information to database
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param username
     * @param password
     * @return
     */
    public int createUser(String firstName, String lastName, String email, String phoneNumber,
            String username, String password) {

	String sql = """
	INSERT INTO userinfo (first_name, last_name, email, phone_number, username, password)
	VALUES (?, ?, ?, ?, ?, ?)
	""";
	
	return jdbcTemplate.update(sql, firstName, lastName, email, phoneNumber, username, password);
	}

    /**
     * Checks login credentials
     * @param username
     * @param password
     * @return
     */
    public boolean validateLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
}
