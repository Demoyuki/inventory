package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring MVC controller for user login functionality.
 * Handles the display of the login page and logout confirmation.
 * 
 * <p>Actual authentication is handled by Spring Security, not this controller.
 * This controller only provides the login view to the user.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 6
 * @see com.gcu.inventory.SecurityConfig
 */
@Controller
public class LoginController {
    /**
     * Displays the login page view.
     *
     * @param model Spring MVC model used to pass attributes to the view
     * @return Thymeleaf template name for the login page
     */
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("title", "Login Form");
        return "login";
    }
}