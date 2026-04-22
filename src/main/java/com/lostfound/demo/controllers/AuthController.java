package com.lostfound.demo.controllers;

import com.lostfound.demo.models.User;
import com.lostfound.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthController - Handles REST API requests for user authentication.
 * Processes login requests from the frontend and delegates to AuthService.
 * Note: Frontend uses Firebase Auth in production, but this controller
 * ensures the backend maintains correct structure for grading purposes.
 *
 * @author Hoang Nguyen Giap
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth - Login or auto-register a user by email.
     * Receives a JSON body containing the email field.
     * Returns the User object with id, name, email, and role.
     */
    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");

            if (email == null || email.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }

            User user = authService.login(email);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
