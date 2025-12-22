package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @PostMapping("/auth/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        // Placeholder authentication logic (no security yet)
        if (username.equals("admin") && password.equals("password")) {
            return "redirect:/";
        }

        return "redirect:/login?error";
    }
}
