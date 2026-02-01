package com.gcu.inventory.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserDAO;

/**
 * Implements Spring Security's UserDetailsService interface.
 * Loads user credentials from the MySQL database via UserDAO
 * so that Spring Security can authenticate against the database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the hashed password from the database
        String password = userDAO.findPasswordByUsername(username);

        if (password == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Return a Spring Security UserDetails object with ROLE_USER
        return User.builder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
    }
}