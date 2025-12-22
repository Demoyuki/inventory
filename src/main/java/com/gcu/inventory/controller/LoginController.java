package com.gcu.inventory.controller;

import com.gcu.inventory.model.LoginModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String display(Model model) {
        model.addAttribute("loginModel", new LoginModel());
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@Valid LoginModel loginModel,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        // Simulated authentication (NO BCrypt, NO Spring Security)
        if ("admin".equals(loginModel.getUsername())
                && "admin".equals(loginModel.getPassword())) {

            return "redirect:/products";
        }

        model.addAttribute("loginError", "Invalid username or password");
        return "login";
    }
}
