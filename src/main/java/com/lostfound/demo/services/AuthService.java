package com.lostfound.demo.services;

import com.lostfound.demo.models.User;

import java.util.HashMap;
import java.util.UUID;

/**
 * AuthService - Manages user authentication and registration.
 * Uses a HashMap to simulate a database, storing users by email for O(1) retrieval.
 * Pre-loads one Admin account upon construction.
 * Demonstrates Encapsulation: data is securely packed before being exposed
 * outside the class boundary.
 *
 * @author Phan Minh Duc
 */
public class AuthService {

    private HashMap<String, User> userMap;

    public AuthService() {
        userMap = new HashMap<>();
        // Pre-load an Admin account
        User admin = new User(
                UUID.randomUUID().toString(),
                "Admin",
                "admin@vinuni.edu.vn",
                "admin"
        );
        userMap.put(admin.getEmail(), admin);
    }

    /**
     * Login or auto-register a user by email.
     * If the email already exists in the userMap, return the existing User.
     * Otherwise, create a new User with role "user", add to userMap, and return it.
     *
     * @param email the user's email address
     * @return the User object
     */
    public User login(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        // Check if user already exists
        if (userMap.containsKey(email)) {
            return userMap.get(email);
        }

        // Create new user with "user" role
        User newUser = new User(
                UUID.randomUUID().toString(),
                email.split("@")[0], // Use part before @ as name
                email,
                "user"
        );

        userMap.put(email, newUser);
        return newUser;
    }

    /**
     * Get a user by email.
     *
     * @param email the email to look up
     * @return the User if found, null otherwise
     */
    public User getUserByEmail(String email) {
        return userMap.get(email);
    }

    /**
     * Get all registered users.
     *
     * @return the HashMap containing all users
     */
    public HashMap<String, User> getAllUsers() {
        return userMap;
    }
}
