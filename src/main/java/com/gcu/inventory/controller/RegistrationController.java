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
 * Milestone 4 updates
 * Updated to call RegistrationService.register
 * Added handling for duplicate username via Boolean return
 */
@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserRegistrationModel());
        return "register/register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("user") UserRegistrationModel user,
            BindingResult bindingResult
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
        
        // Milestone 4 addition
        // Delegates registration logic to the registration service layer for database persistence
        boolean success = registrationService.register(user);

        // Handles duplicate username scenarios returned from the database
        if (!success) {
            bindingResult.rejectValue(
                    "username",
                    "username.exists",
                    "Username already exists."
            );
            return "register/register";
        }

        return "register/success";
    }
}


   

