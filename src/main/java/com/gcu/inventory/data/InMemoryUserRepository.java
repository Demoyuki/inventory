package com.gcu.inventory.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


/**
 * Purpose: In memory implementation of the repository using a Map of username and password to emulate authentication
 * without a database.
 * Keeps user data in one place
 * 
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
	
	private final Map<String, String> users = new HashMap<>();
	
	public InMemoryUserRepository() {
		// Hardwired Users
		users.put("test", "test");
		users.put("admin", "admin");
	}

	@Override
	/**
	 * Validates user credentials against the in memory user store
	 * This method emulates authentication without using a database.
	 */
	public boolean isValidUser(String username, String password) {
		if (username == null || password == null) return false;
		
		return password.equals(users.get(username));
	}

}
