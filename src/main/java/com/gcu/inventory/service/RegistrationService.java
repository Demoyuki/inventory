package com.gcu.inventory.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserDAO;
import com.gcu.inventory.model.UserRegistrationModel;

/**
 * Milestone 4: Moved logic into DAO, removed in-memory storage
 * Spring Security upgrade: Encodes password with BCrypt before saving
 *                          so Spring Security can validate it on login.
 */
@Service
public class RegistrationService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     * Checks for duplicate username, encodes the password with BCrypt,
     * then persists to the database.
     * @return false if username already exists, true on success
     */
    public boolean register(UserRegistrationModel model) {
        if (userDAO.userExists(model.getUsername())) return false;

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
     * Verifies that password and confirmPassword fields match.
     */
    public boolean passwordsMatch(UserRegistrationModel user) {
        return user.getPassword() != null
                && user.getConfirmPassword() != null
                && user.getPassword().equals(user.getConfirmPassword());
    }
}