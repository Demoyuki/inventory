package com.gcu.inventory.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Model class used to bind login form data.
 *
 * <p>This class holds username and password values
 * submitted by the user and applies validation
 * constraints for form submission.</p>
 *
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 2
 */
public class LoginModel {

    // Username entered on the login form
    @NotBlank(message = "Username is required")
    private String username;

    // Password entered on the login form
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    /**
     * Returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
