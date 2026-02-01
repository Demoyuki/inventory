package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring Security upgrade:
 * - Removed POST handler - Spring Security handles POST /login automatically
 * - Removed AuthService dependency - no longer needed
 * - Removed session management - Spring Security manages the auth session
 * - Only responsibility: serve the login form on GET /login
 * 
 * Spring Security automatically:
 *   - Processes POST /login with username & password fields
 *   - Redirects to /login?error on failure
 *   - Redirects to /products on success
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("title", "Login Form");
        return "login";
    }
}