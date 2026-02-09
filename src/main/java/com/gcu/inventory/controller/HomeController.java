package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring MVC controller for the application home page.
 * Handles the display of the landing page and root URL redirects.
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 3
 */
@Controller
public class HomeController {
    /**
     * Displays the home/landing page of the application.
     * Sets the page title in the model for the template.
     * 
     * @return The name of the Thymeleaf template to render (index.html)
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}