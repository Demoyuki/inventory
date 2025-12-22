package com.yourpackage.controller;

import com.yourpackage.model.UserRegistrationModel;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

   
    @GetMapping("/")
    public String home() {
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserRegistrationModel());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("user") UserRegistrationModel user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

       
        if (!user.passwordsMatch()) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match.");
            return "register";
        }

        
        String hashed = encoder.encode(user.getPassword());

       
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("passwordHash", hashed);

        return "registerSuccess";
    }
}
