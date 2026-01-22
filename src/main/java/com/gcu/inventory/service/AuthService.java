package com.gcu.inventory.service;
import com.gcu.inventory.data.UserDAO;
import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserRepository;

/**
 * Purpose: Moves login/authentication rules out of the controller and into a dedicated business layer
 * AuthService receives UserRepository instead of creating it with new
 * 
 * Milestone 4 updates
 * Refactored to validate user credentials using the database
 * Uses userDAO.java instead of in memory validation
 * 
 */
@Service
public class AuthService {
	// Provides database access for authentication operations
	private final UserDAO userDAO;

	// Constructor based dependency injection of UserDAO
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Authenticates user credentials by validating against database records
    public boolean authenticate(String username, String password) {
        return userDAO.validateLogin(username, password);
    }
}