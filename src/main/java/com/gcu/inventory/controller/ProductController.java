package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Req                                                           
public class ProductController {

    // Products list page
    @GetMapping("")
    public String products() {
        return "products/list";
    }

    // Create product page
    @GetMapping("/create")
    public String createProduct(Model model) {
        return "products/create";
    }
}
