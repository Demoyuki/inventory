package com.gcu.inventory.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object for User entity.
 * Handles all database operations for user accounts using Spring's JdbcTemplate.
 * Provides methods for user creation, retrieval, and username validation.
 * 
 * <p>This DAO interacts with the 'userinfo' table in the MySQL database
 * and supports Spring Security authentication by retrieving BCrypt-hashed passwords.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 6
 */
@Repository
public class UserDAO {

    // JdbcTemplate used to execute SQL statements
    private final JdbcTemplate jdbcTemplate;

    // Constructor-based dependency injection
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks whether a username already exists in the database.
     *
     * @param username Username to check
     * @return True if the username exists
     */
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    /**
     * Creates a new user record in the database.
     *
     * <p>This method assumes the password has already been
     * encoded before being passed in.</p>
     *
     * @param firstName User first name
     * @param lastName User last name
     * @param email User email address
     * @param phoneNumber User phone number
     * @param username Username for login
     * @param password Encoded password
     * @return Number of rows affected
     */
    public int createUser(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String username,
            String password
    ) {
        String sql = """
                INSERT INTO userinfo (first_name, last_name, email, phone_number, username, password)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                firstName,
                lastName,
                email,
                phoneNumber,
                username,
                password
        );
    }

    /**
     * Retrieves the stored password hash for a given username.
     *
     * <p>This method returns null when the username does not exist.</p>
     *
     * @param username Username to look up
     * @return Encoded password, or null if the user is not found
     */
    public String findPasswordByUsername(String username) {
        String sql = "SELECT password FROM userinfo WHERE username = ?";

        var results = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("password"),
                username
        );

        return results.isEmpty() ? null : results.get(0);
    }
}
