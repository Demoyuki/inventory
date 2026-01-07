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

        return "register/success";
    }
}


   

