package com.gcu.inventory.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserDAO;
import com.gcu.inventory.model.UserRegistrationModel;

/**
 * Service layer for user registration business logic.
 * Handles user registration operations including password encryption,
 * username validation, and coordination with the data access layer.
 * 
 * <p>This service ensures that all passwords are properly encrypted using
 * BCrypt before being stored in the database, providing secure password storage.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 6
 * @see UserDAO
 */
@Service
public class RegistrationService {

    // Data access object for user persistence
    private final UserDAO userDAO;

    // Password encoder used to hash passwords before storage
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the RegistrationService with required dependencies.
     *
     * @param userDAO Data access object for user persistence
     * @param passwordEncoder Encoder used to hash passwords securely
     */
    public RegistrationService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * <p>This method checks for an existing username,
     * encodes the user's password, and saves the user
     * to the database.</p>
     *
     * @param model Registration data submitted by the user
     * @return True if registration succeeds, false if the username already exists
     */
    public boolean register(UserRegistrationModel model) {
        if (userDAO.userExists(model.getUsername())) {
            return false;
        }

        // Encode the plain-text password before saving
        String encodedPassword = passwordEncoder.encode(model.getPassword());

        userDAO.createUser(
                model.getFirstName(),
                model.getLastName(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getUsername(),
                encodedPassword
        );

        return true;
    }

    /**
     * Checks whether the password and confirmation fields match.
     *
     * @param user Registration model containing password fields
     * @return True if both passwords are equal
     */
    public boolean passwordsMatch(UserRegistrationModel user) {
        return user.getPassword() != null
                && user.getConfirmPassword() != null
                && user.getPassword().equals(user.getConfirmPassword());
    }
}
