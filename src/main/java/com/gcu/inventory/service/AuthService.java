package com.gcu.inventory.service;

import org.springframework.stereotype.Service;

import com.gcu.inventory.data.UserRepository;

/**
 * Purpose: Moves login/authentication rules out of the controller and into a dedicated business layer
 * AuthService receives UserRepository instead of creating it with new
 * 
 */
@Service
public class AuthService {
	
	private final UserRepository userRepository;
	
	// DI happens here
	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean login(String username, String password) {
		return userRepository.isValidUser(username, password);
	}
}
