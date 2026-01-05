package com.gcu.inventory.data;


/**
 * Defines contract for user authentication logic
 * Repository interface represents the data access layer
 */
public interface UserRepository {
	boolean isValidUser(String username, String password);

}
