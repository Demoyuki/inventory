package com.gcu.inventory.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Handles database access related to users.
 * Uses Spring JDBC via JdbcTemplate.
 * 
 * Milestone 4: Added createUser, userExists, validateLogin
 * Spring Security upgrade: Added findPasswordByUsername for UserDetailsService
 */
@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks if a username already exists in the database.
     * Used by RegistrationService to prevent duplicate usernames.
     */
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    /**
     * Creates a new user record in the database.
     * Password should already be BCrypt-encoded before calling this method.
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
     * Loads the BCrypt-hashed password for a given username.
     * Returns null if the username does not exist.
     * Used by UserDetailsServiceImpl for Spring Security authentication.
     */
    public String findPasswordByUsername(String username) {
        String sql = "SELECT password FROM userinfo WHERE username = ?";
        var results = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("password"), username);
        return results.isEmpty() ? null : results.get(0);
    }
}