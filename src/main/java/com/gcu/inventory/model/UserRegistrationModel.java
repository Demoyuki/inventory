package com.gcu.inventory.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Model class representing user registration and account information.
 * Contains user credentials and personal information required for registration.
 * Includes validation constraints to ensure data integrity.
 * 
 * <p>This model is used for:</p>
 * <ul>
 *   <li>User registration form binding</li>
 *   <li>Spring Security authentication (UserDetailsService)</li>
 *   <li>Database storage in the userinfo table</li>
 * </ul>
 * 
 * <p><strong>Security Note:</strong> Passwords stored in this model should be
 * BCrypt-hashed before database persistence. Plain-text passwords should only
 * exist temporarily during the registration form submission.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 6
 */
public class UserRegistrationModel {

    // User first name
    @NotBlank(message = "First Name is required.")
    @Size(min = 2, max = 50)
    private String firstName;

    // User last name
    @NotBlank(message = "Last Name is required.")
    @Size(min = 2, max = 50)
    private String lastName;

    // User email address
    @NotBlank(message = "Email Address is required.")
    @Email
    private String email;

    // User phone number
    @NotBlank(message = "Phone Number is required.")
    @Pattern(
        regexp = "^(\\d{10}|\\d{3}-\\d{3}-\\d{4})$",
        message = "Invalid phone format."
    )
    private String phoneNumber;

    // Username chosen by the user
    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 30)
    private String username;

    // Password chosen by the user
    @NotBlank(message = "Password is required.")
    @Size(min = 8)
    private String password;

    // Password confirmation entered by the user
    @NotBlank(message = "Confirm Password is required.")
    private String confirmPassword;

    /**
     * Checks whether the password and confirmation match.
     *
     * @return True if both password fields are equal
     */
    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
