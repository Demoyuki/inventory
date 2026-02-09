package com.gcu.inventory.controller;

import com.gcu.inventory.model.UserRegistrationModel;
import com.gcu.inventory.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Spring MVC controller for user registration functionality.
 * Handles the display of the registration form and processing of new user registrations.
 * 
 * <p>This controller validates user input, checks for duplicate usernames,
 * and delegates user creation to the RegistrationService which handles
 * BCrypt password encryption before database storage.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 6
 * @see RegistrationService
 * @see UserRegistrationModel
 */
@Controller
public class RegistrationController {

    // Service layer used for registration and persistence logic
    private final RegistrationService registrationService;

    // Constructor-based dependency injection
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Displays the user registration form.
     *
     * @param model Spring MVC model used to bind a new registration object
     * @return Thymeleaf template for the registration page
     */
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserRegistrationModel());
        return "register/register";
    }

    /**
     * Handles submission of the registration form.
     *
     * <p>This method performs validation, verifies that the password
     * and confirmation match, attempts to register the user, and
     * returns either the registration form or a success page.</p>
     *
     * @param user User registration data submitted from the form
     * @param bindingResult Holds validation and registration errors
     * @param model Spring MVC model used to pass data to the success view
     * @return Registration form on error, or success page on completion
     */
    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("user") UserRegistrationModel user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register/register";
        }

        if (!registrationService.passwordsMatch(user)) {
            bindingResult.rejectValue(
                    "confirmPassword",
                    "password.mismatch",
                    "Passwords do not match."
            );
            return "register/register";
        }

        boolean success = registrationService.register(user);

        if (!success) {
            bindingResult.rejectValue(
                    "username",
                    "username.exists",
                    "Username already exists."
            );
            return "register/register";
        }

        // Add attributes used by the success view
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("username", user.getUsername());

        return "register/success";
    }
}
