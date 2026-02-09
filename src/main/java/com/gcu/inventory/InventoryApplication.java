package com.gcu.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InventoryApplication
 * 
 * This is the main entry point for the Inventory Spring Boot application.
 * The @SpringBootApplication annotation enables component scanning,
 * auto-configuration, and configuration support.
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 1.0
 * @since Milestone 1
 */
@SpringBootApplication
public class InventoryApplication {

    /**
     * Main method that launches the Spring Boot application.
     * 
     * SpringApplication.run() bootstraps the application,
     * starts the embedded web server, and initializes the Spring context.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {

        // Start the Spring Boot application
        SpringApplication.run(InventoryApplication.class, args);
    }
}
