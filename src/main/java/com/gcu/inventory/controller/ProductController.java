package com.gcu.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("")
    public String products() {
        return "products";
    }

    @GetMapping("/create")
    public String createProduct() {
        return "product-create";
    }
}
