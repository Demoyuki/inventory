package com.gcu.inventory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility to generate BCrypt hashes for existing database passwords.
 * 
 * HOW TO USE:
 *   1. Run this class as a Java application (right-click -> Run As -> Java Application)
 *   2. Copy the hashes printed to the console
 *   3. Paste them into the UPDATE statements in inventory_upgrade.sql
 *   4. Run the SQL in MySQL Workbench
 * 
 * You only need to run this once to upgrade existing passwords.
 * New user registrations will be encoded automatically by RegistrationService.
 */
public class PasswordHashHelper {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // These match the two existing users in the userinfo table
        String[][] users = {
            { "admin",  "admin" },
            { "admin1", "12345678" }
        };

        System.out.println("========================================");
        System.out.println("  BCrypt Hashes for existing DB users");
        System.out.println("========================================");

        for (String[] user : users) {
            String username = user[0];
            String plainPassword = user[1];
            String encoded = encoder.encode(plainPassword);
            System.out.println();
            System.out.println("Username : " + username);
            System.out.println("Password : " + plainPassword);
            System.out.println("Hash     : " + encoded);
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("  Copy the hashes above into");
        System.out.println("  inventory_upgrade.sql UPDATE statements");
        System.out.println("========================================");
    }
}