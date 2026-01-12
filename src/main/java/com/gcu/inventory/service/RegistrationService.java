package com.gcu.inventory.service;

import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserDAO;
import com.gcu.inventory.model.UserRegistrationModel;

/**
 * Updates For Milestone 4
 * Removed in memory storage
 * Logic moved into DAO.java
 * Accepts UserRegistrationModel and extracts required fields
 */
@Service
public class RegistrationService {

	// Used to inejct UserDAO for database backed user operations
	private final UserDAO userDAO;
	
	// Constructor based dependency injection for the UserDAO
    public RegistrationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Registers a new user by validating uniqueness and persisting data to the database
    public boolean register(UserRegistrationModel model) {

    	// Prevents duplicate usernames from being added
        if (userDAO.userExists(model.getUsername())) return false;
        // Validation registration data to the database
        userDAO.createUser(
            model.getFirstName(),
            model.getLastName(),
            model.getEmail(),
            model.getPhoneNumber(),
            model.getUsername(),
            model.getPassword()
        );
        return true;
    }
	
    /**
     * Verifies the the password and confirmation password match
     * @param user
     * @return
     */
	public boolean passwordsMatch(UserRegistrationModel user) {
        return user.getPassword() != null
                && user.getConfirmPassword() != null
                && user.getPassword().equals(user.getConfirmPassword());
    }
}
