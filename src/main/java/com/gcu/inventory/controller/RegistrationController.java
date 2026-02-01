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
 * Milestone 4: Added database persistence via RegistrationService
 * Spring Security upgrade: Password is now BCrypt-encoded by RegistrationService
 *                          before being saved to the database.
 *                          Added model attributes for the success page.
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

        // Pass attributes so success.html can display them
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("username", user.getUsername());

        return "register/success";
    }
}