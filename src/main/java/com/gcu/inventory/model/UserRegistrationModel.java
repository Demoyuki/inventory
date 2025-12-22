package com.gcu.inventory.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationModel {

    @NotBlank(message = "First Name is required.")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last Name is required.")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "Email Address is required.")
    @Email
    private String email;

    @NotBlank(message = "Phone Number is required.")
    @Pattern(
        regexp = "^(\\d{10}|\\d{3}-\\d{3}-\\d{4})$",
        message = "Invalid phone format."
    )
    private String phoneNumber;

    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 30)
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8)
    private String password;

    @NotBlank(message = "Confirm Password is required.")
    private String confirmPassword;

    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
