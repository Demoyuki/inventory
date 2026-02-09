package com.gcu.inventory.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserDAO;

/**
 * Custom implementation of Spring Security's UserDetailsService interface.
 * Loads user-specific data from the MySQL database for authentication purposes.
 * 
 * <p>This service is used by Spring Security to retrieve user credentials
 * and authorities during the authentication process. It queries the database
 * through UserDAO and converts the user data into Spring Security's UserDetails format.</p>
 * 
 * <p>Passwords stored in the database are BCrypt-hashed and are validated
 * by Spring Security's authentication manager.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 6
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see UserDAO
 * @see com.gcu.inventory.SecurityConfig
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // DAO used to retrieve user data from the database
    private final UserDAO userDAO;

    /**
     * Constructor-based dependency injection.
     * Injects the UserDAO so this service can access stored user credentials.
     */
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Loads a user's authentication details by username.
     * 
     * This method is automatically invoked by Spring Security during login.
     * It retrieves the stored (hashed) password from the database and
     * returns a UserDetails object used for authentication.
     *
     * @param username the username entered during login
     * @return UserDetails object containing username, password, and roles
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Retrieve the hashed password associated with the username
        String password = userDAO.findPasswordByUsername(username);

        // If no user is found, Spring Security requires this exception
        if (password == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Build and return a UserDetails object with ROLE_USER
        // Spring Security will compare the stored hash with the login password
        return User.builder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
    }
}
