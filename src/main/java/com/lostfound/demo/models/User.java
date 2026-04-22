package com.lostfound.demo.models;

/**
 * User - Data class representing a user in the Lost & Found system.
 * Provides only getter methods and no setters, making instances effectively
 * immutable after creation. This design ensures controlled access to internal
 * state and reflects the principle of Encapsulation.
 *
 * @author Nguyen The Minh Duc
 */
public class User {

    private String id;
    private String name;
    private String email;
    private String role;

    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "', role='" + role + "'}";
    }
}
